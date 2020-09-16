package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.JsonParser;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

import java.util.ArrayList;
import java.util.List;

public class NutrientViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private SharedPreferenceProcess spp;

    private MutableLiveData<List<NutrientModel>> nutrientListLD;

    public NutrientViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.nutrientListLD = new MutableLiveData<>();
    }

    public void initiateSharedPreferenceProcess(Context context) {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(context);
    }

    // for live data
    public void setNutrientListLD(List<NutrientModel> nutrientModelList){
        this.nutrientListLD.setValue(nutrientModelList);
    }
    public LiveData<List<NutrientModel>> getNutrientListLD(){
        return this.nutrientListLD;
    }

    // for find all virus by AsyncTask
    public void processFindingNutrientList() {
        try {
            NutrientViewModel.FindNutrientListAsyncTask findNutrientListAsyncTask = new NutrientViewModel.FindNutrientListAsyncTask();
            findNutrientListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindNutrientListAsyncTask extends AsyncTask<Void, Void, List<NutrientModel>> {
        @Override
        protected List<NutrientModel> doInBackground(Void... voids) {
            List<NutrientModel> nutrientModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllNutrients();
                nutrientModelList = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // save virus list into spp
            spp.saveVirusInfoList(nutrientModelList);
            return nutrientModelList;
        }

        @Override
        protected void onPostExecute(List<NutrientModel> resultNutrientList) {
            setNutrientListLD(resultNutrientList);
        }
    }
