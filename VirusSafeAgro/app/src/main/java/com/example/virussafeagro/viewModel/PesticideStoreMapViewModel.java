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

    public void processFindingPesticideStoreList(int radius) {
        try{
            FindPesticideStoreListAsyncTask findPesticideStoreListAsyncTask = new FindPesticideStoreListAsyncTask();
            findPesticideStoreListAsyncTask.execute(radius);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FindPesticideStoreListAsyncTask extends AsyncTask<Integer, Void, List<PesticideStoreModel>> {

        @Override
        protected List<PesticideStoreModel> doInBackground(Integer... integers) {
            List<PesticideStoreModel> pesticideStoreList = new ArrayList<>();
            try {
                String pesticideStoreListResult = networkConnectionToGoogleSearchAPI.getPesticideStoreList(integers[0]);
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
