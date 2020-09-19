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
        return "";
    }

    public Bitmap getPictureFromURL(String pictureURL){
        final String pictureSearchPath = "https://quziquzitions.s3.us-east-2.amazonaws.com/BACTERIAL%20SPOT/QUESTION%201/chemical-control.jpg";
        Request request = new Request.Builder().url(pictureSearchPath).build();
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
