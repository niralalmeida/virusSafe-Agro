package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.entities.Virus;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class VirusInfoViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<Virus>> virusInfoListLD;

    public VirusInfoViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusInfoListLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setVirusInfoListLD(List<Virus> virusInfoList){
        this.virusInfoListLD.setValue(virusInfoList);
    }
    public LiveData<List<Virus>> getVirusInfoListLD(){
        return this.virusInfoListLD;
    }

    public void processFindingVirusInfoList() {
        try {
            FindVirusInfoListAsyncTask findVirusInfoListAsyncTask = new FindVirusInfoListAsyncTask();
            findVirusInfoListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusInfoListAsyncTask extends AsyncTask<Void, Void, List<Virus>> {
        @Override
        protected List<Virus> doInBackground(Void... voids) {
            List<Virus> virusInfoList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                virusInfoList = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusInfoList;
        }

        @Override
        protected void onPostExecute(List<Virus> resultVirusInfoList) {
            setVirusInfoListLD(resultVirusInfoList);
        }
    }
}
