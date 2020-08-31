package com.example.virussafeagro.networkConnection;

import com.example.virussafeagro.models.ImageObject;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnectionToMLModel {
    private static final String BASE_URL = "http://6ee3f0c6903e.ngrok.io/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToMLModel() {
        this.okHttpClient = new OkHttpClient();
    }

    public String getImageIdentificationFeedback(ImageObject imageObject) {
        String resultText = "";
        final String methodPathForImageMLModel = BASE_URL + "Image";

        Gson gson = new Gson();
        String uploadImageJson = gson.toJson(imageObject);

        RequestBody bodyForImage = RequestBody.create(uploadImageJson, JSON);
        Request requestForImage = new Request.Builder()
                .url(methodPathForImageMLModel)
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
