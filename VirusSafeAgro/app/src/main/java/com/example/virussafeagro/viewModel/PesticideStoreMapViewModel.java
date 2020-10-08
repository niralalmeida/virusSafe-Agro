package com.example.virussafeagro.viewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.PesticideStoreModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToGoogleSearchAPI;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class PesticideStoreMapViewModel extends ViewModel {
    private NetworkConnectionToGoogleSearchAPI networkConnectionToGoogleSearchAPI;

    private MutableLiveData<List<PesticideStoreModel>> pesticideStoreListLD;

    public PesticideStoreMapViewModel() {
        this.networkConnectionToGoogleSearchAPI = new NetworkConnectionToGoogleSearchAPI();
        this.pesticideStoreListLD = new MutableLiveData<>();
    }

    public void setPesticideStoreListLD(List<PesticideStoreModel> pesticideStoreList) {
        this.pesticideStoreListLD.setValue(pesticideStoreList);
    }
    public LiveData<List<PesticideStoreModel>> getPesticideStoreListLD() {
        return this.pesticideStoreListLD;
    }

    public void processFindingPesticideStoreList(double latitude, double longitude, double radius) {
        try{
            FindPesticideStoreListAsyncTask findPesticideStoreListAsyncTask = new FindPesticideStoreListAsyncTask();
            findPesticideStoreListAsyncTask.execute(latitude, longitude, radius);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FindPesticideStoreListAsyncTask extends AsyncTask<Double, Void, List<PesticideStoreModel>> {

        @Override
        protected List<PesticideStoreModel> doInBackground(Double... doubles) {
            List<PesticideStoreModel> pesticideStoreList = new ArrayList<>();
            try {
                String pesticideStoreListResult = networkConnectionToGoogleSearchAPI.getPesticideStoreList(doubles[0], doubles[1], doubles[2]);
                pesticideStoreList = MyJsonParser.pesticideStoreListParser(pesticideStoreListResult);
            } catch (Exception e){
                e.printStackTrace();
            }
            return pesticideStoreList;
        }

        @Override
        protected void onPostExecute(List<PesticideStoreModel> resultPesticideStoreList) {
            setPesticideStoreListLD(resultPesticideStoreList);
        }
    }
}
