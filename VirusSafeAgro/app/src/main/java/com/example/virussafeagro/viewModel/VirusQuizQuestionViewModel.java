package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.List;

public class VirusQuizQuestionViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List[]> virusTwoTypeQuestionArrayLD;

    public VirusQuizQuestionViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusTwoTypeQuestionArrayLD = new MutableLiveData<>();
    }

    public void setVirusTwoTypeQuestionArrayLD(List[] virusTwoTypeQuestionArray){
        this.virusTwoTypeQuestionArrayLD.setValue(virusTwoTypeQuestionArray);
    }
    public LiveData<List[]> getVirusTwoTypeQuestionArrayLD(){
        return this.virusTwoTypeQuestionArrayLD;
    }

    public void processFindingVirusQuizQuestions(int virusId) {
        try {
            FindVirusQuizQuestionsAsyncTask findVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
            findVirusQuizQuestionsAsyncTask.execute(virusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusQuizQuestionsAsyncTask extends AsyncTask<Integer, Void, List[]> {
        @Override
        protected List[] doInBackground(Integer... integers) {
            List[] virusTwoTypeQuestionArray = new List[2];
            int virusId = integers[0];
            try {
                String resultTextForQuestions = networkConnectionToTomatoVirusDB.getAllQuestions(virusId);
                List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = JsonParser.singleChoiceQuestionModelListJsonParser(resultTextForQuestions);
                List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = JsonParser.multipleChoiceQuestionModelListJsonParser(resultTextForQuestions);
                // find options for single choice questions
                for (SingleChoiceQuestionModel singleChoiceQuestionModel : singleChoiceQuestionModelList) {
                    String resultTextForSingleOptions = networkConnectionToTomatoVirusDB.getAllOptions(singleChoiceQuestionModel.getChoiceQuestionId());
                    List<String> singleOptionList = JsonParser.singleChoiceOptionListJsonParser(resultTextForSingleOptions);
                    singleChoiceQuestionModel.setSingleChoiceQuestionOptionList(singleOptionList);
                }
                // find options for multiple choice questions
                for (MultipleChoiceQuestionModel multipleChoiceQuestionModel : multipleChoiceQuestionModelList) {
                    String resultTextForMultipleOptions = networkConnectionToTomatoVirusDB.getAllOptions(multipleChoiceQuestionModel.getChoiceQuestionId());
                    List<String> multipleOptionList = JsonParser.multipleChoiceOptionListJsonParser(resultTextForMultipleOptions);
                    multipleChoiceQuestionModel.setMultipleChoiceQuestionOptionList(multipleOptionList);
                }
                virusTwoTypeQuestionArray[0] = singleChoiceQuestionModelList;
                virusTwoTypeQuestionArray[1] = multipleChoiceQuestionModelList;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusTwoTypeQuestionArray;
        }

        @Override
        protected void onPostExecute(List[] resultVirusTwoTypeQuestionArray) {
            setVirusTwoTypeQuestionArrayLD(resultVirusTwoTypeQuestionArray);
        }
    }


}
