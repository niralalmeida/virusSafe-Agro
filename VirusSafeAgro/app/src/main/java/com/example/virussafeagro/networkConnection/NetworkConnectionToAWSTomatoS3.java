package com.example.virussafeagro.networkConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionToAWSTomatoS3 {
    private OkHttpClient okHttpClient;

    public NetworkConnectionToAWSTomatoS3() {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)// the time for connection
                .readTimeout(10, TimeUnit.SECONDS)// the time for reading
                .build();
    }

    // get all Question Images by virus name(id in parameter)
    public String getAllQuestionImages(String virusName) {
        String resultText = "";
        final String API_URL = "https://qcqanvg6il.execute-api.us-east-2.amazonaws.com/v1/Newlambda?id=";
        String searchURL = API_URL + virusName; // id in parameter
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public Bitmap getImageFromURLold(String rawImageURL){
        int index = rawImageURL.indexOf("/QUESTION");
        StringBuilder stringBuilder = new StringBuilder(rawImageURL);
        stringBuilder.insert(index, "+");
        final String IMAGE_URL = stringBuilder.toString();
        Request request = new Request.Builder().url(IMAGE_URL).build();
        Bitmap bitmap = null;
        try{
            Response response = this.okHttpClient.newCall(request).execute();
            InputStream resultStream = Objects.requireNonNull(response.body()).byteStream();
            bitmap = BitmapFactory.decodeStream(resultStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap getImageFromURL(String rawImageURL){
        final String IMAGE_URL = rawImageURL.replace(" ", "%20");
        Request request = new Request.Builder().url(IMAGE_URL).build();
        Bitmap bitmap = null;
        try{
            Response response = this.okHttpClient.newCall(request).execute();
            InputStream resultStream = Objects.requireNonNull(response.body()).byteStream();
            bitmap = BitmapFactory.decodeStream(resultStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
