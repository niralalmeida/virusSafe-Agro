package com.example.virussafeagro.networkConnection;

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
//    private AsyncHttpClient asyncHttpClient;

    public NetworkConnectionToMLModel() {
        this.okHttpClient = new OkHttpClient();
    }

//    public String getImageIdentificationFeedback(String uploadImageBitmapString) {
//        String resultText = "";
//        String searchURL = BASE_URL + "Image";
//
////        Gson gson = new Gson();
////        String imageJsonString = gson.toJson(uploadImageBitmapString);
//
//        RequestBody bodyForImage = RequestBody.create(uploadImageBitmapString, JSON);
////        Request request = new Request.Builder().url(searchURL).build();
//        Request.Builder builder = new Request.Builder().url(searchURL);
//        builder.setBody$okhttp(bodyForImage);
//        Request request = new Request.Builder().url(searchURL).setBody$okhttp(bodyForImage);
//        try {
//            Response response = this.okHttpClient.newCall(request).execute();
//            resultText = response.body().string();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultText;
//    }

    public String getImageIdentificationFeedback(String uploadImageBitmapString) {
        return "";
    }
}
