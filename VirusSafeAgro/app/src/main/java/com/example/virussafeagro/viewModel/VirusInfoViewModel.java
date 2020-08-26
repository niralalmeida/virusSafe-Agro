package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.model.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;

import java.util.ArrayList;
import java.util.List;

public class VirusInfoViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List<VirusModel>> virusInfoListLD;

    public VirusInfoViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusInfoListLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setVirusInfoListLD(List<VirusModel> virusInfoList){
        this.virusInfoListLD.setValue(virusInfoList);
    }
    public LiveData<List<VirusModel>> getVirusInfoListLD(){
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
    private class FindVirusInfoListAsyncTask extends AsyncTask<Void, Void, List<VirusModel>> {
        @Override
        protected List<VirusModel> doInBackground(Void... voids) {
//            List<VirusModel> virusInfoList = new ArrayList<>();
//            VirusModel vm1 = new VirusModel();
//            VirusModel vm2 = new VirusModel();
//            VirusModel vm3 = new VirusModel();
//            vm1.setVirusId(1);
//            vm1.setVirusFullName("virus no 1");
//            vm2.setVirusId(2);
//            vm2.setVirusFullName("virus no 2");
//            vm3.setVirusId(3);
//            vm3.setVirusFullName("virus no 3");
//            virusInfoList.add(vm1);
//            virusInfoList.add(vm2);
//            virusInfoList.add(vm3);
            List<VirusModel> virusInfoList = new ArrayList<>();
            try {
                String resultText = networkConnectionToTomatoVirusDB.getAllVirus();
                // test
                System.out.println(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusInfoList;
        }

        @Override
        protected void onPostExecute(List<VirusModel> resultVirusInfoList) {
            setVirusInfoListLD(resultVirusInfoList);
        }
    }
}
