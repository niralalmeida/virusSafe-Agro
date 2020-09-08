package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.JsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

import java.util.ArrayList;
import java.util.List;

public class VirusInfoListViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private SharedPreferenceProcess spp;

    private MutableLiveData<List<VirusModel>> virusInfoListLD;

    public VirusInfoListViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusInfoListLD = new MutableLiveData<>();
    }

    public void initiateSharedPreferenceProcess(Context context) {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(context);
    }

    // for live data
    public void setVirusInfoListLD(List<VirusModel> virusModelInfoList){
        this.virusInfoListLD.setValue(virusModelInfoList);
    }
    public LiveData<List<VirusModel>> getVirusInfoListLD(){
        return this.virusInfoListLD;
    }

    // for find all virus by AsyncTask
    public void processFindingVirusInfoList() {
        try {
            FindVirusInfoListAsyncTask findVirusInfoListAsyncTask = new FindVirusInfoListAsyncTask();
            findVirusInfoListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusInfoListAsyncTask extends AsyncTask<Void, Void, List<VirusModel>> {
        @Override
        protected List<VirusModel> doInBackground(Void... voids) {
            List<VirusModel> virusModelInfoList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                virusModelInfoList = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // save virus list into spp
            spp.saveVirusInfoList(virusModelInfoList);
            return virusModelInfoList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusModelInfoList) {
            setVirusInfoListLD(resultVirusModelInfoList);
        }
    }
}
