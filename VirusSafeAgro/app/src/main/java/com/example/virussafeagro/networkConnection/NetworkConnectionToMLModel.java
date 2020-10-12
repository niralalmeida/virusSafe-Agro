package com.example.virussafeagro.networkConnection;

import com.example.virussafeagro.models.ImageObject;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnectionToMLModel {
    private static final String API_URL = "http://ec2-3-236-71-249.compute-1.amazonaws.com:5000/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToMLModel() {
        this.okHttpClient = new OkHttpClient();
    }

    public String getImageCheckFeedback(ImageObject imageObject) {
        String resultText = "";

        Gson gson = new Gson();
        String uploadImageJson = gson.toJson(imageObject);

        RequestBody bodyForImage = RequestBody.create(uploadImageJson, JSON);
        Request requestForImage = new Request.Builder()
                .url(API_URL)
                .post(bodyForImage)
                .build();
        try {
            Response responseForImage = this.okHttpClient.newCall(requestForImage).execute();
            resultText = responseForImage.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }
}
