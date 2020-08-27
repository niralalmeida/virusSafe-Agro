package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizListViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<VirusModel>> virusQuizListLD;

    public VirusQuizListViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusQuizListLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setVirusQuizListLD(List<VirusModel> virusModelQuizList){
        this.virusQuizListLD.setValue(virusModelQuizList);
    }
    public LiveData<List<VirusModel>> getVirusQuizListLD(){
        return this.virusQuizListLD;
    }

    public void processFindingVirusQuizList() {
        try {
            VirusQuizListViewModel.FindVirusQuizListAsyncTask findVirusQuizListAsyncTask = new VirusQuizListViewModel.FindVirusQuizListAsyncTask();
            findVirusQuizListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusQuizListAsyncTask extends AsyncTask<Void, Void, List<VirusModel>> {
        @Override
        protected List<VirusModel> doInBackground(Void... voids) {
            List<VirusModel> virusModelQuizList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                virusModelQuizList = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusModelQuizList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusModelQuizList) {
            setVirusQuizListLD(resultVirusModelQuizList);
        }
    }
}
