package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.ChoiceQuestionCorrectAnswerModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizResultViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<ChoiceQuestionCorrectAnswerModel>> correctAnswerListLD;

    public VirusQuizResultViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.correctAnswerListLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setCorrectAnswerListLD(List<ChoiceQuestionCorrectAnswerModel> correctAnswerList){
        this.correctAnswerListLD.setValue(correctAnswerList);
    }
    public LiveData<List<ChoiceQuestionCorrectAnswerModel>> getCorrectAnswerListLD(){
        return this.correctAnswerListLD;
    }

    public void processFindingCorrectAnswers(int virusId) {
        try {
            VirusQuizResultViewModel.FindingCorrectAnswersAsyncTask findingCorrectAnswersAsyncTask = new VirusQuizResultViewModel.FindingCorrectAnswersAsyncTask();
            findingCorrectAnswersAsyncTask.execute(virusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindingCorrectAnswersAsyncTask extends AsyncTask<Integer, Void, List<ChoiceQuestionCorrectAnswerModel>> {
        @Override
        protected List<ChoiceQuestionCorrectAnswerModel> doInBackground(Integer... integers) {
            List<ChoiceQuestionCorrectAnswerModel> correctAnswersList = new ArrayList<>();
            int virusId = integers[0];
            try {
                String resultTextForAnswers = networkConnectionToTomatoVirusDB.getAllAnswers(virusId);
                correctAnswersList = JsonParser.choiceQuestionAnswerListJsonParser(resultTextForAnswers);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (AppResources.choiceQuestionCorrectAnswerModelListBackup.size() == 0){
//                correctAnswersList = AppResources.getChoiceQuestionCorrectAnswerModelListBackup(virusId - 1);
//            } else {
//                correctAnswersList = AppResources.choiceQuestionCorrectAnswerModelListBackup.get(virusId - 1);
//            }

            return correctAnswersList;
        }

        @Override
        protected void onPostExecute(List<ChoiceQuestionCorrectAnswerModel> resultCorrectAnswerList) {
            setCorrectAnswerListLD(resultCorrectAnswerList);
        }
    }
}
