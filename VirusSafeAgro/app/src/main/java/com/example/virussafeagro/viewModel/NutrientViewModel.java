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

        // 4
        NutrientModel nutrientModel4 = new NutrientModel();

        List<String> nutrient4SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n4s1");
        nutrient1SymptomList.add("n4s2");
        nutrient1SymptomList.add("n4s3");
        List<String> nutrient4ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n4r1");
        nutrient1ReasonList.add("n4r2");
        nutrient1ReasonList.add("n4r3");
        List<String> nutrient4FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n4f1");
        nutrient1FactoryList.add("n4f2");
        nutrient1FactoryList.add("n4f3");

        nutrientModel4.setNutrientId(4);
        nutrientModel4.setNutrientName("Magnesium");
        nutrientModel4.setNutrientSymptomList(nutrient4SymptomList);
        nutrientModel4.setNutrientReasonList(nutrient4ReasonList);
        nutrientModel4.setNutrientFactoryList(nutrient4FactoryList);

        // 5
        NutrientModel nutrientModel5 = new NutrientModel();

        List<String> nutrient5SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n5s1");
        nutrient1SymptomList.add("n5s2");
        nutrient1SymptomList.add("n5s3");
        List<String> nutrient5ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n5r1");
        nutrient1ReasonList.add("n5r2");
        nutrient1ReasonList.add("n5r3");
        List<String> nutrient5FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n5f1");
        nutrient1FactoryList.add("n5f2");
        nutrient1FactoryList.add("n5f3");

        nutrientModel5.setNutrientId(5);
        nutrientModel5.setNutrientName("Potassium");
        nutrientModel5.setNutrientSymptomList(nutrient5SymptomList);
        nutrientModel5.setNutrientReasonList(nutrient5ReasonList);
        nutrientModel5.setNutrientFactoryList(nutrient5FactoryList);

        // 6
        NutrientModel nutrientModel6 = new NutrientModel();

        List<String> nutrient6SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n6s1");
        nutrient1SymptomList.add("n6s2");
        nutrient1SymptomList.add("n6s3");
        List<String> nutrient6ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n6r1");
        nutrient1ReasonList.add("n6r2");
        nutrient1ReasonList.add("n6r3");
        List<String> nutrient6FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n6f1");
        nutrient1FactoryList.add("n6f2");
        nutrient1FactoryList.add("n6f3");

        nutrientModel6.setNutrientId(6);
        nutrientModel6.setNutrientName("Phosphorus");
        nutrientModel6.setNutrientSymptomList(nutrient6SymptomList);
        nutrientModel6.setNutrientReasonList(nutrient6ReasonList);
        nutrientModel6.setNutrientFactoryList(nutrient6FactoryList);

        // 7
        NutrientModel nutrientModel7 = new NutrientModel();

        List<String> nutrient7SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n7s1");
        nutrient1SymptomList.add("n7s2");
        nutrient1SymptomList.add("n7s3");
        List<String> nutrient7ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n7r1");
        nutrient1ReasonList.add("n7r2");
        nutrient1ReasonList.add("n7r3");
        List<String> nutrient7FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n7f1");
        nutrient1FactoryList.add("n7f2");
        nutrient1FactoryList.add("n7f3");

        nutrientModel7.setNutrientId(7);
        nutrientModel7.setNutrientName("nutrient7");
        nutrientModel7.setNutrientSymptomList(nutrient7SymptomList);
        nutrientModel7.setNutrientReasonList(nutrient7ReasonList);
        nutrientModel7.setNutrientFactoryList(nutrient7FactoryList);

        // 8
        NutrientModel nutrientModel8 = new NutrientModel();

        List<String> nutrient8SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n8s1");
        nutrient1SymptomList.add("n8s2");
        nutrient1SymptomList.add("n8s3");
        List<String> nutrient8ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n8r1");
        nutrient1ReasonList.add("n8r2");
        nutrient1ReasonList.add("n8r3");
        List<String> nutrient8FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n8f1");
        nutrient1FactoryList.add("n8f2");
        nutrient1FactoryList.add("n8f3");

        nutrientModel8.setNutrientId(8);
        nutrientModel8.setNutrientName("nutrient8");
        nutrientModel8.setNutrientSymptomList(nutrient8SymptomList);
        nutrientModel8.setNutrientReasonList(nutrient8ReasonList);
        nutrientModel8.setNutrientFactoryList(nutrient8FactoryList);

        // 9
        NutrientModel nutrientModel9 = new NutrientModel();

        List<String> nutrient9SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n9s1");
        nutrient1SymptomList.add("n9s2");
        nutrient1SymptomList.add("n9s3");
        List<String> nutrient9ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n9r1");
        nutrient1ReasonList.add("n9r2");
        nutrient1ReasonList.add("n9r3");
        List<String> nutrient9FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n9f1");
        nutrient1FactoryList.add("n9f2");
        nutrient1FactoryList.add("n9f3");

        nutrientModel9.setNutrientId(9);
        nutrientModel9.setNutrientName("nutrient9");
        nutrientModel9.setNutrientSymptomList(nutrient9SymptomList);
        nutrientModel9.setNutrientReasonList(nutrient9ReasonList);
        nutrientModel9.setNutrientFactoryList(nutrient9FactoryList);

        // 10
        NutrientModel nutrientModel10 = new NutrientModel();

        List<String> nutrient10SymptomList = new ArrayList<>();
        nutrient1SymptomList.add("n10s1");
        nutrient1SymptomList.add("n10s2");
        nutrient1SymptomList.add("n10s3");
        List<String> nutrient10ReasonList = new ArrayList<>();
        nutrient1ReasonList.add("n10r1");
        nutrient1ReasonList.add("n10r2");
        nutrient1ReasonList.add("n10r3");
        List<String> nutrient10FactoryList = new ArrayList<>();
        nutrient1FactoryList.add("n10f1");
        nutrient1FactoryList.add("n10f2");
        nutrient1FactoryList.add("n10f3");

        nutrientModel10.setNutrientId(10);
        nutrientModel10.setNutrientName("nutrient10");
        nutrientModel10.setNutrientSymptomList(nutrient10SymptomList);
        nutrientModel10.setNutrientReasonList(nutrient10ReasonList);
        nutrientModel10.setNutrientFactoryList(nutrient10FactoryList);

        // add all
        nutrientModelList.add(nutrientModel1);
        nutrientModelList.add(nutrientModel2);
        nutrientModelList.add(nutrientModel3);
        nutrientModelList.add(nutrientModel4);
        nutrientModelList.add(nutrientModel5);
        nutrientModelList.add(nutrientModel6);
        nutrientModelList.add(nutrientModel7);
        nutrientModelList.add(nutrientModel8);
        nutrientModelList.add(nutrientModel9);
        nutrientModelList.add(nutrientModel10);

        return nutrientModelList;
    }

}
