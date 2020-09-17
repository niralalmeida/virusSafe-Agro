package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizListViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private SharedPreferenceProcess spp;

    private MutableLiveData<List<VirusModel>> virusQuizListLD;

    public VirusQuizListViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusQuizListLD = new MutableLiveData<>();
    }

    public void initiateSharedPreferenceProcess(Context context) {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(context);
    }

    // for live data
    public void setVirusQuizListLD(List<VirusModel> virusModelQuizList){
        this.virusQuizListLD.setValue(virusModelQuizList);
    }
    public LiveData<List<VirusModel>> getVirusQuizListLD(){
        return this.virusQuizListLD;
    }

    // for find all virus by AsyncTask
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
                virusModelQuizList = MyJsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // save virus list into spp
            spp.saveVirusInfoList(virusModelQuizList);
            return virusModelQuizList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusModelQuizList) {
            setVirusQuizListLD(resultVirusModelQuizList);
        }
    }
}
