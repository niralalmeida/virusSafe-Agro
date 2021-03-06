package com.example.virussafeagro.viewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.models.NutrientModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class NutrientViewModel extends ViewModel {
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;
    private FindNutrientListAsyncTask currentFindNutrientListAsyncTask;

    private MutableLiveData<List<NutrientModel>> nutrientListLD;

    public NutrientViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.nutrientListLD = new MutableLiveData<>();
        this.currentFindNutrientListAsyncTask = new NutrientViewModel.FindNutrientListAsyncTask();
    }

    // for live data
    public void setNutrientListLD(List<NutrientModel> nutrientModelList) {
        this.nutrientListLD.setValue(nutrientModelList);
    }
    public LiveData<List<NutrientModel>> getNutrientListLD() {
        return this.nutrientListLD;
    }


    // for find all virus by AsyncTask
    public void processFindingNutrientList() {
        try {
            currentFindNutrientListAsyncTask = new NutrientViewModel.FindNutrientListAsyncTask();
            currentFindNutrientListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class FindNutrientListAsyncTask extends AsyncTask<Void, Void, List<NutrientModel>> {
        @Override
        protected List<NutrientModel> doInBackground(Void... voids) {
            if (isCancelled()){
                return null;
            }
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllNutrients();
                MainActivity.nutrientModelList = MyJsonParser.nutrientListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MainActivity.nutrientModelList;
        }

        @Override
        protected void onPostExecute(List<NutrientModel> resultNutrientList) {
            setNutrientListLD(resultNutrientList);
        }
    }

    public FindNutrientListAsyncTask getCurrentFindNutrientListAsyncTask() {
        return currentFindNutrientListAsyncTask;
    }
}
