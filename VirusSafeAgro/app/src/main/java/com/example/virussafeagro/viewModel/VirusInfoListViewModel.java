package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToAWSTomatoS3;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

import java.util.ArrayList;
import java.util.List;

public class VirusInfoListViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private NetworkConnectionToAWSTomatoS3 networkConnectionToAWSTomatoS3;
    private FindVirusInfoListAsyncTask currentFindVirusInfoListAsyncTask;

    private MutableLiveData<List<VirusModel>> virusInfoListLD;

    public VirusInfoListViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.networkConnectionToAWSTomatoS3 = new NetworkConnectionToAWSTomatoS3();
        this.virusInfoListLD = new MutableLiveData<>();
        this.currentFindVirusInfoListAsyncTask = new FindVirusInfoListAsyncTask();
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
            currentFindVirusInfoListAsyncTask = new FindVirusInfoListAsyncTask();
            currentFindVirusInfoListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class FindVirusInfoListAsyncTask extends AsyncTask<Void, Void, List<VirusModel>> {
        @Override
        protected List<VirusModel> doInBackground(Void... voids) {
            if (isCancelled()){
                return null;
            }
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                MainActivity.virusModelInfoList = MyJsonParser.virusInfoListJsonParser(resultText);
                // check network connection
                if (!MyJsonParser.isVirusInfoListTaskFailed) {
                    // get the virus image URLs
                    for (VirusModel virusModel : MainActivity.virusModelInfoList) {
                        String resultTextForURL = networkConnectionToAWSTomatoS3.getVirusImagesByVirusId(virusModel.getVirusId());
                        virusModel = MyJsonParser.virusImageURLIntoVirusModelJsonParser(resultTextForURL, virusModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MainActivity.virusModelInfoList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusModelInfoList) {
            setVirusInfoListLD(resultVirusModelInfoList);
        }
    }

    // get currentFindVirusInfoListAsyncTask
    public FindVirusInfoListAsyncTask getCurrentFindVirusInfoListAsyncTask() {
        return currentFindVirusInfoListAsyncTask;
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        virusInfoListLD = null;
    }
}
