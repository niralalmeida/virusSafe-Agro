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
//    private SharedPreferenceProcess spp;

    private MutableLiveData<List<NutrientModel>> nutrientListLD;

    public NutrientViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.nutrientListLD = new MutableLiveData<>();
    }

//    public void initiateSharedPreferenceProcess(Context context) {
//        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(context);
//    }

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
//            try {
//                String resultText = networkConnectionToTomatoVirusDB.getAllNutrients();
//                nutrientModelList = JsonParser.nutrientListJsonParser(resultText);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            nutrientModelList = testAddNewNutrient();
            // save virus list into spp
//            spp.saveVirusInfoList(nutrientModelList);
            return nutrientModelList;
        }

        @Override
        protected void onPostExecute(List<NutrientModel> resultNutrientList) {
            setNutrientListLD(resultNutrientList);
        }
    }

    // test
    private List<NutrientModel> testAddNewNutrient() {
        List<NutrientModel> nutrientModelList = new ArrayList<>();

        // 1
        NutrientModel nutrientModel1 = new NutrientModel();

        List<String> nutrient1SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n1s1");
        nutrient1SymptomList.add("n1s2");
        nutrient1SymptomList.add("n1s3");
        List<String> nutrient1ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n1r1");
        nutrient1ReasonList.add("n1r2");
        nutrient1ReasonList.add("n1r3");
        List<String> nutrient1FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n1f1");
        nutrient1FactoryList.add("n1f2");
        nutrient1FactoryList.add("n1f3");

        nutrientModel1.setNutrientId(1);
        nutrientModel1.setNutrientName("Zinc");
        nutrientModel1.setNutrientSymptomList(nutrient1SymptomList);
        nutrientModel1.setNutrientReasonList(nutrient1ReasonList);
        nutrientModel1.setNutrientFactoryList(nutrient1FactoryList);

        // 2
        NutrientModel nutrientModel2 = new NutrientModel();

        List<String> nutrient2SymptomList = new ArrayList<>();
        nutrient2SymptomList.add("n2s1");
        nutrient2SymptomList.add("n2s2");
        nutrient2SymptomList.add("n2s3");
        List<String> nutrient2ReasonList = new ArrayList<>();
        nutrient2ReasonList.add("n2r1");
        nutrient2ReasonList.add("n2r2");
        nutrient2ReasonList.add("n2r3");
        List<String> nutrient2FactoryList = new ArrayList<>();
        nutrient2FactoryList.add("n2f1");
        nutrient2FactoryList.add("n2f2");
        nutrient2FactoryList.add("n2f3");

        nutrientModel2.setNutrientId(2);
        nutrientModel2.setNutrientName("Sulphur");
        nutrientModel2.setNutrientSymptomList(nutrient2SymptomList);
        nutrientModel2.setNutrientReasonList(nutrient2ReasonList);
        nutrientModel2.setNutrientFactoryList(nutrient2FactoryList);

        // 3
        NutrientModel nutrientModel3 = new NutrientModel();

        List<String> nutrient3SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n3s1");
        nutrient1SymptomList.add("n3s2");
        nutrient1SymptomList.add("n3s3");
        List<String> nutrient3ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n3r1");
        nutrient1ReasonList.add("n3r2");
        nutrient1ReasonList.add("n3r3");
        List<String> nutrient3FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n3f1");
        nutrient1FactoryList.add("n3f2");
        nutrient1FactoryList.add("n3f3");

        nutrientModel3.setNutrientId(3);
        nutrientModel3.setNutrientName("Nitrogen");
        nutrientModel3.setNutrientSymptomList(nutrient3SymptomList);
        nutrientModel3.setNutrientReasonList(nutrient3ReasonList);
        nutrientModel3.setNutrientFactoryList(nutrient3FactoryList);

        // add all
        nutrientModelList.add(nutrientModel1);
        nutrientModelList.add(nutrientModel2);
        nutrientModelList.add(nutrientModel3);

        return nutrientModelList;
    }

}
