package com.example.virussafeagro.viewModel;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class QuizActivityViewModel extends ViewModel {
    private FindVirusQuizQuestionsAsyncTask currentFindVirusQuizQuestionsAsyncTask;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<ChoiceQuestionModel>> quizQuestionModelListLD;

    public QuizActivityViewModel() {
            this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
            this.quizQuestionModelListLD = new MutableLiveData<>();
            this.currentFindVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
        }

    public void setQuizQuestionModelListLD(List<ChoiceQuestionModel> quizQuestionModelList){
        this.quizQuestionModelListLD.setValue(quizQuestionModelList);
    }
    public LiveData<List<ChoiceQuestionModel>> getQuizQuestionModelListLD(){
        return this.quizQuestionModelListLD;
    }

    public void processFindingVirusQuizQuestions(int virusId) {
        try {
            currentFindVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
            currentFindVirusQuizQuestionsAsyncTask.execute(virusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class FindVirusQuizQuestionsAsyncTask extends AsyncTask<Integer, Void, List<ChoiceQuestionModel>> {
        @Override
        protected List<ChoiceQuestionModel> doInBackground(Integer... integers) {
            if (isCancelled()){
                return null;
            }
            List<ChoiceQuestionModel> quizQuestionModelList = new ArrayList<>();
            int virusId = integers[0];
            try {
                // get all questions
                String resultTextForQuestions = networkConnectionToTomatoVirusDB.getAllQuestions(virusId);
                quizQuestionModelList = MyJsonParser.choiceQuestionModelListJsonParser(resultTextForQuestions);
                // get all options
                for (ChoiceQuestionModel choiceQuestionModel : quizQuestionModelList) {
                    String resultTextForOptions = networkConnectionToTomatoVirusDB.getAllOptions(choiceQuestionModel.getChoiceQuestionId());
                    List<ChoiceOptionModel> optionModelList = MyJsonParser.choiceOptionListJsonParser(resultTextForOptions);
                    // set the option list into question model
                    choiceQuestionModel.setChoiceQuestionOptionList(optionModelList);
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

    public QuizActivityViewModel.FindVirusQuizQuestionsAsyncTask getCurrentFindVirusQuizQuestionsAsyncTask() {
        return currentFindVirusQuizQuestionsAsyncTask;
    }
}
