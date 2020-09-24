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

public class VirusCheckResultViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private SharedPreferenceProcess spp;

    private MutableLiveData<List<VirusModel>> virusList;

    public VirusCheckResultViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusList = new MutableLiveData<>();
    }

    public void initiateSharedPreferenceProcess(Context context) {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(context);
    }

    // for live data
    public void setVirusListLD(List<VirusModel> virusList){
        this.virusList.setValue(virusList);
    }
    public LiveData<List<VirusModel>> getVirusListLD(){
        return this.virusList;
    }

    // for find all virus by AsyncTask
    public void processFindingVirusList() {
        try {
            FindVirusListAsyncTask findVirusListAsyncTask = new FindVirusListAsyncTask();
            findVirusListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusListAsyncTask extends AsyncTask<Void, Void, List<VirusModel>> {
        @Override
        protected List<VirusModel> doInBackground(Void... voids) {
            List<VirusModel> virusModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                virusModelList = MyJsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // save virus list into spp
//            spp.saveVirusInfoList(virusModelList);
            return virusModelList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusList) {
            setVirusListLD(resultVirusList);
        }
    }
}
