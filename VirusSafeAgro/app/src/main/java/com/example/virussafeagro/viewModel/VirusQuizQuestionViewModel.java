package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.MultipleChoiceQuestionModel;
import com.example.virussafeagro.models.SingleChoiceQuestionModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToTomatoVirusDB;

import java.util.ArrayList;
import java.util.List;

public class VirusQuizQuestionViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToTomatoVirusDB networkConnectionToTomatoVirusDB;

    private MutableLiveData<List[]> virusTwoTypeQuestionArrayLD;

    public VirusQuizQuestionViewModel() {
        this.networkConnectionToTomatoVirusDB = new NetworkConnectionToTomatoVirusDB();
        this.virusTwoTypeQuestionArrayLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setVirusTwoTypeQuestionArrayLD(List[] virusTwoTypeQuestionArray){
        this.virusTwoTypeQuestionArrayLD.setValue(virusTwoTypeQuestionArray);
    }
    public LiveData<List[]> getVirusTwoTypeQuestionArrayLD(){
        return this.virusTwoTypeQuestionArrayLD;
    }

    public void processFindingVirusQuizQuestions() {
        try {
            FindVirusQuizQuestionsAsyncTask findVirusQuizQuestionsAsyncTask = new FindVirusQuizQuestionsAsyncTask();
            findVirusQuizQuestionsAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindVirusQuizQuestionsAsyncTask extends AsyncTask<Void, Void, List[]> {
        @Override
        protected List[] doInBackground(Void... voids) {
            List[] virusTwoTypeQuestionArray = new List[2];
            // test
            virusTwoTypeQuestionArray = testViewModel(virusTwoTypeQuestionArray);
            try {
//                String resultText = networkConnectionToTomatoVirusDB.getAllQuestions();
//                List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
//                List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
//                virusTwoTypeQuestionArray = JsonParser.virusInfoListJsonParser(resultText);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return virusTwoTypeQuestionArray;
        }

        @Override
        protected void onPostExecute(List[] resultVirusTwoTypeQuestionArray) {
            setVirusTwoTypeQuestionArrayLD(resultVirusTwoTypeQuestionArray);
        }
    }

    private List[] testViewModel(List[] virusTwoTypeQuestionArray) {
        List<SingleChoiceQuestionModel> singleChoiceQuestionModelList = new ArrayList<>();
        List<MultipleChoiceQuestionModel> multipleChoiceQuestionModelList = new ArrayList<>();
        virusTwoTypeQuestionArray[0] = singleChoiceQuestionModelList;
        virusTwoTypeQuestionArray[1] = multipleChoiceQuestionModelList;

        List<String> options1 = new ArrayList<>();
        options1.add("A. o1a");
        options1.add("B. o1a ++");
        options1.add("C. o1a");
        options1.add("D. o1a");
        SingleChoiceQuestionModel s1 = new SingleChoiceQuestionModel(
                1,
                "s question content 1 : ",
                options1,
                "B",
                2);
        List<String> options2 = new ArrayList<>();
        options1.add("A. o2a");
        options1.add("B. o2b");
        options1.add("C. o2c ++");
        SingleChoiceQuestionModel s2 = new SingleChoiceQuestionModel(
                2,
                "s question content 2 : ",
                options1,
                "C",
                2);
        singleChoiceQuestionModelList.add(s1);
        singleChoiceQuestionModelList.add(s2);

        List<String> m_options1 = new ArrayList<>();
        options1.add("A. m1oa ++");
        options1.add("B. m1ob ++");
        options1.add("C. m1oc");
        options1.add("D. m1od");
        options1.add("E. m1oae ++");
        List<String> ans1 = new ArrayList<>();
        ans1.add("A");
        ans1.add("B");
        ans1.add("E");
        MultipleChoiceQuestionModel m1 = new MultipleChoiceQuestionModel(
                3,
                "m question content 1 : ",
                m_options1,
                ans1,
                2);
        List<String> m_options2 = new ArrayList<>();
        options1.add("A. m1oa");
        options1.add("B. m1ob ++");
        options1.add("C. m1oc ++");
        List<String> ans2 = new ArrayList<>();
        ans1.add("B");
        ans1.add("C");
        MultipleChoiceQuestionModel m2 = new MultipleChoiceQuestionModel(
                4,
                "m question content 2 : ",
                m_options2,
                ans2,
                2);
        singleChoiceQuestionModelList.add(s1);
        singleChoiceQuestionModelList.add(s2);

        return virusTwoTypeQuestionArray;
    }
}
