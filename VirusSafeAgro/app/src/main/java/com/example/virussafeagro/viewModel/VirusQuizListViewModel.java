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

public class VirusQuizListViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<Virus>> virusQuizListLD;

    public VirusQuizListViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusQuizListLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setVirusQuizListLD(List<Virus> virusQuizList){
        this.virusQuizListLD.setValue(virusQuizList);
    }
    public LiveData<List<Virus>> getVirusQuizListLD(){
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
    private class FindVirusQuizListAsyncTask extends AsyncTask<Void, Void, List<Virus>> {
        @Override
        protected List<Virus> doInBackground(Void... voids) {
            List<Virus> virusQuizList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                virusQuizList = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusQuizList;
        }

        @Override
        protected void onPostExecute(List<Virus> resultVirusQuizList) {
            setVirusQuizListLD(resultVirusQuizList);
        }
    }
}
