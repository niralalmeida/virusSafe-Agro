package com.example.virussafeagro.viewModel;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToAWSTomatoS3;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class QuizActivityViewModel extends ViewModel {
    private FindVirusQuizQuestionsAsyncTask currentFindVirusQuizQuestionsAsyncTask;
    private FindVirusQuizQuestionsImageURLAsyncTask currentFindVirusQuizQuestionsImageURLAsyncTask;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private NetworkConnectionToAWSTomatoS3 networkConnectionToAWSTomatoS3;

    private MutableLiveData<List<ChoiceQuestionModel>> quizQuestionModelListLD;
    private MutableLiveData<List<ChoiceQuestionModel>> quizQuestionModelListWithImageURLLD;

    public QuizActivityViewModel() {
            this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
            this.networkConnectionToAWSTomatoS3 = new NetworkConnectionToAWSTomatoS3();
            this.quizQuestionModelListLD = new MutableLiveData<>();
            this.quizQuestionModelListWithImageURLLD = new MutableLiveData<>();
            this.currentFindVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
        }

    public void setQuizQuestionModelListLD(List<ChoiceQuestionModel> quizQuestionModelList){
        this.quizQuestionModelListLD.setValue(quizQuestionModelList);
    }
    public LiveData<List<ChoiceQuestionModel>> getQuizQuestionModelListLD(){
        return this.quizQuestionModelListLD;
    }

    public void setQuizQuestionModelWithImageURLListLD(List<ChoiceQuestionModel> quizQuestionModelListWithImageURL){
        this.quizQuestionModelListWithImageURLLD.setValue(quizQuestionModelListWithImageURL);
    }
    public LiveData<List<ChoiceQuestionModel>> getQuizQuestionModelListWithImageURLLD(){
        return this.quizQuestionModelListWithImageURLLD;
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


    public void processFindingVirusQuizQuestionsImageURL(int virusId) {
        try {
            currentFindVirusQuizQuestionsImageURLAsyncTask = new FindVirusQuizQuestionsImageURLAsyncTask();
            currentFindVirusQuizQuestionsImageURLAsyncTask.execute(virusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class FindVirusQuizQuestionsImageURLAsyncTask extends AsyncTask<Integer, Void, List<ChoiceQuestionModel>> {
        @Override
        protected List<ChoiceQuestionModel> doInBackground(Integer... integers) {
            if (isCancelled()){
                return null;
            }
            List<ChoiceQuestionModel> quizQuestionModelListWithImageURL = QuizActivity.choiceQuestionModelList;
            int virusId = integers[0];
            try {
                // get all questions image URL
                String virusIdForS3API = AppResources.getVirusIdForS3API(virusId);
                String resultTextForQuestions = networkConnectionToAWSTomatoS3.getAllQuestionImages(virusIdForS3API);
                quizQuestionModelListWithImageURL = MyJsonParser.choiceQuestionModelListForImageJsonParser(resultTextForQuestions, quizQuestionModelListWithImageURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return quizQuestionModelListWithImageURL;
        }

        @Override
        protected void onPostExecute(List<ChoiceQuestionModel> resultQuizQuestionWithImageURLModelList) {
            setQuizQuestionModelWithImageURLListLD(resultQuizQuestionWithImageURLModelList);
        }
    }

    public QuizActivityViewModel.FindVirusQuizQuestionsImageURLAsyncTask getCurrentFindVirusQuizQuestionsImageURLAsyncTask() {
        return currentFindVirusQuizQuestionsImageURLAsyncTask;
    }
}
