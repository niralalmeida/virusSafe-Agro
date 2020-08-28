package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizQuestionViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List[]> virusTwoTypeQuestionArrayLD;

    public VirusQuizQuestionViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusTwoTypeQuestionArrayLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
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
                virusTwoTypeQuestionArray = JsonParser.virusTwoTypeQuestionArrayJsonParser(resultTextForQuestions);
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
