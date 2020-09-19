package com.example.virussafeagro.viewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizQuestionViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<ChoiceQuestionModel>> quizQuestionModelListLD;

    public VirusQuizQuestionViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.quizQuestionModelListLD = new MutableLiveData<>();
    }

    public void setQuizQuestionModelListLD(List<ChoiceQuestionModel> quizQuestionModelList){
        this.quizQuestionModelListLD.setValue(quizQuestionModelList);
    }
    public LiveData<List<ChoiceQuestionModel>> getQuizQuestionModelListLD(){
        return this.quizQuestionModelListLD;
    }

    public void processFindingVirusQuizQuestions(int virusId) {
        try {
            FindVirusQuizQuestionsAsyncTask findVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
            findVirusQuizQuestionsAsyncTask.execute(virusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusQuizQuestionsAsyncTask extends AsyncTask<Integer, Void, List<ChoiceQuestionModel>> {
        @Override
        protected List<ChoiceQuestionModel> doInBackground(Integer... integers) {
            List<ChoiceQuestionModel> quizQuestionModelList = new ArrayList<>();
            int virusId = integers[0];
            try {
                String resultTextForQuestions = networkConnectionToTomatoVirusDB.getAllQuestions(virusId);
                quizQuestionModelList = MyJsonParser.choiceQuestionModelListJsonParser(resultTextForQuestions);
                // check network connection
                if (!quizQuestionModelList.get(0).getChoiceQuestionType().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    // find options for choice questions
                    for (ChoiceQuestionModel choiceQuestionModel : quizQuestionModelList) {
                        String resultTextForOptions = networkConnectionToTomatoVirusDB.getAllOptions(choiceQuestionModel.getChoiceQuestionId());
                        List<ChoiceOptionModel> optionModelList = MyJsonParser.choiceOptionListJsonParser(resultTextForOptions);
                        choiceQuestionModel.setChoiceQuestionOptionList(optionModelList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return quizQuestionModelList;
        }

        @Override
        protected void onPostExecute(List<ChoiceQuestionModel> resultQuizQuestionModelList) {
            setQuizQuestionModelListLD(resultQuizQuestionModelList);
        }
    }


}
