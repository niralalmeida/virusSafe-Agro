package com.example.virussafeagro.viewModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.networkConnection.NetworkConnectionToMLModel;

public class VirusIdentificationViewModel extends ViewModel {
    private Context context;
    private NetworkConnectionToMLModel networkConnectionToMLModel;

    private MutableLiveData<String> identificationFeedbackLD;

    public VirusIdentificationViewModel() {
        this.networkConnectionToMLModel = new NetworkConnectionToMLModel();
        this.identificationFeedbackLD = new MutableLiveData<>();
    }

    public void initiateTheContext(Context context){
        this.context = context;
    }

    public void setIdentificationFeedbackLD(String identificationFeedback){
        this.identificationFeedbackLD.setValue(identificationFeedback);
    }
    public LiveData<String> getIdentificationFeedbackLD(){
        return this.identificationFeedbackLD;
    }

    public void processUploadingTomatoImage(Bitmap tomatoImage) {
        try {
            GettingTomatoImageIdentificationFeedbackAsyncTask gettingTomatoImageIdentificationFeedbackAsyncTask = new GettingTomatoImageIdentificationFeedbackAsyncTask();
            gettingTomatoImageIdentificationFeedbackAsyncTask.execute(tomatoImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class GettingTomatoImageIdentificationFeedbackAsyncTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            String feedback = "";
            try {
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return feedback;
        }

        @Override
        protected void onPostExecute(String resultIdentificationFeedback) {
            setIdentificationFeedbackLD(resultIdentificationFeedback);
        }
    }

}
