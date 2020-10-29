package com.example.virussafeagro.viewModel;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToAWSTomatoS3;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizQuestionViewModel extends ViewModel {
    private ProgressBar progressBar;

    private FindVirusQuizQuestionsAsyncTask currentFindVirusQuizQuestionsAsyncTask;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private NetworkConnectionToAWSTomatoS3 networkConnectionToAWSTomatoS3;

    private MutableLiveData<List<ChoiceQuestionModel>> quizQuestionModelListLD;

    public VirusQuizQuestionViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.networkConnectionToAWSTomatoS3 = new NetworkConnectionToAWSTomatoS3();
        this.quizQuestionModelListLD = new MutableLiveData<>();
        this.currentFindVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
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
//            try {
//                String resultTextForQuestions = networkConnectionToTomatoVirusDB.getAllQuestions(virusId);
//                quizQuestionModelList = MyJsonParser.choiceQuestionModelListJsonParser(resultTextForQuestions);
//                // check network connection
//                if (!quizQuestionModelList.get(0).getChoiceQuestionType().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
//                    // find options for choice questions
//                    for (ChoiceQuestionModel choiceQuestionModel : quizQuestionModelList) {
//                        // [ RDS ] find images for questions
//                        String questionImageJsonResult = networkConnectionToTomatoVirusDB.getQuestionsImage(choiceQuestionModel.getChoiceQuestionId());
//                        String questionImageString = MyJsonParser.questionImageJsonParser(questionImageJsonResult);
//                        Bitmap questionImageBitmap = DataConverter.stringToBitmapConverter(questionImageString);
//                        List<Bitmap> questionImageList = new ArrayList<>();
//                        questionImageList.add(questionImageBitmap);
//                        choiceQuestionModel.setChoiceQuestionImageList(questionImageList);
//
//                        // find options
//                        String resultTextForOptions = networkConnectionToTomatoVirusDB.getAllOptions(choiceQuestionModel.getChoiceQuestionId());
//                        List<ChoiceOptionModel> optionModelList = MyJsonParser.choiceOptionListJsonParser(resultTextForOptions);
//
//                        // [ RDS ] find images for options
//                        for (ChoiceOptionModel choiceOptionModel : optionModelList) {
//                            String optionImageJsonResult = networkConnectionToTomatoVirusDB.getOptionsImage(choiceOptionModel.getChoiceOptionId());
//                            String optionImageString = MyJsonParser.optionImageJsonParser(optionImageJsonResult);
//                            Bitmap optionImageBitmap = DataConverter.stringToBitmapConverter(optionImageString);
//                            choiceOptionModel.setChoiceOptionImage(optionImageBitmap);
//                        }
//                        // get progress no
//                        int questionId =  choiceQuestionModel.getChoiceQuestionId();
//                        int progressNo =  questionId > 5 ? questionId % 5 : questionId;
//                        if (progressNo == 0){
//                            progressNo = 5;
//                        }
//
//                        // set the option list into question model
//                        choiceQuestionModel.setChoiceQuestionOptionList(optionModelList);
//
//                        // set progress bar
//                        progressBar.setProgress(progressNo);
//                    }
//                }

//                {
//                    // get images URLs by S3 API
//                    String virusIdForS3API = AppResources.getVirusIdForS3API(virusId);
//                    String resultTextForQuestionImages = networkConnectionToAWSTomatoS3.getAllQuestionImages(virusIdForS3API);
//                    quizQuestionModelList = MyJsonParser.choiceQuestionModelListForImageJsonParser(resultTextForQuestionImages, quizQuestionModelList);
//
//                    // check network connection for question image
//                    if (!quizQuestionModelList.get(quizQuestionModelList.size() - 1).getChoiceQuestionType().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
//
//                        // get images Bitmaps by the URLs
//                        for (ChoiceQuestionModel choiceQuestionModel : quizQuestionModelList) {
//                            // set question images by question image URLs
//                            List<String> questionImageURLList = choiceQuestionModel.getImageURLList();
//                            List<Bitmap> questionImageList = new ArrayList<>();
//                            for (String questionImageURL : questionImageURLList) {
//
//                                // get a question image by networkConnectionToAWSTomatoS3
//                                Bitmap questionImageBitmap = networkConnectionToAWSTomatoS3.getImageFromURL(questionImageURL);
//
//                                // add the question image into the new list
//                                questionImageList.add(questionImageBitmap);
//                            }
//                            // store the question image list into the question list
//                            choiceQuestionModel.setChoiceQuestionImageList(questionImageList);
//
//                            // set option images by option image URLs
//                            List<ChoiceOptionModel> choiceOptionModelList = choiceQuestionModel.getChoiceQuestionOptionList();
//                            for (ChoiceOptionModel choiceOptionModel : choiceOptionModelList) {
//                                // get option image URL
//                                String optionImageURL = choiceOptionModel.getChoiceOptionImageURL();
//                                if (optionImageURL != null && (!optionImageURL.isEmpty())) {
//                                    // get a option image by networkConnectionToAWSTomatoS3
//                                    Bitmap optionImageBitmap = networkConnectionToAWSTomatoS3.getImageFromURL(optionImageURL);
//                                    // store the image bitmap into the option model
//                                    choiceOptionModel.setChoiceOptionImage(optionImageBitmap);
//                                }
//                            }
//                        }
//                    }
//                }

//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return quizQuestionModelList;
        }

        @Override
        protected void onPostExecute(List<ChoiceQuestionModel> resultQuizQuestionModelList) {
            setQuizQuestionModelListLD(resultQuizQuestionModelList);
        }
    }

    public FindVirusQuizQuestionsAsyncTask getCurrentFindVirusQuizQuestionsAsyncTask() {
        return currentFindVirusQuizQuestionsAsyncTask;
    }
}
