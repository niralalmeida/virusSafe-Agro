package com.example.virussafeagro.viewModel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.models.ImageObject;
import com.example.virussafeagro.networkConnection.NetworkConnectionToMLModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyJsonParser;

public class VirusCheckViewModel extends ViewModel {
    private NetworkConnectionToMLModel networkConnectionToMLModel;

    private MutableLiveData<String> checkFeedbackLD;

    public VirusCheckViewModel() {
        this.networkConnectionToMLModel = new NetworkConnectionToMLModel();
        this.checkFeedbackLD = new MutableLiveData<>();
    }

    private void setCheckFeedbackLD(String checkFeedback){
        this.checkFeedbackLD.setValue(checkFeedback);
    }
    public LiveData<String> getCheckFeedbackLD(){
        return this.checkFeedbackLD;
    }

    public void processUploadingTomatoImage(Bitmap tomatoImage) {
        try {
            GettingTomatoImageCheckFeedbackAsyncTask gettingTomatoImageCheckFeedbackAsyncTask = new GettingTomatoImageCheckFeedbackAsyncTask();
            gettingTomatoImageCheckFeedbackAsyncTask.execute(tomatoImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class GettingTomatoImageCheckFeedbackAsyncTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            String feedback = "";
            Bitmap uploadImageBitmap = bitmaps[0];
            String uploadImageBitmapString = DataConverter.bitmapToStringConverter(uploadImageBitmap);
            ImageObject imageObject = new ImageObject(uploadImageBitmapString);
            try {
                String rawFeedback = networkConnectionToMLModel.getImageCheckFeedback(imageObject);
                feedback = MyJsonParser.imageCheckFeedbackJsonParser(rawFeedback);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            feedback = "Tomato_Mosaic_Virus";
//            feedback = "json error";
//            feedback = "healthy";

            return feedback;
        }

        @Override
        protected void onPostExecute(String resultCheckFeedback) {
            setCheckFeedbackLD(resultCheckFeedback);
        }
    }

}
