package com.example.virussafeagro.networkConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionToTomatoVirusDB {
    private static final String BASE_URL = "http://10.0.2.2:46834/TomatoVirusDB/webresources/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToTomatoVirusDB() {
        this.okHttpClient = new OkHttpClient();
    }

    public String getAllVirus() {
        String resultText = "";
        String searchURL = BASE_URL + "tovrestws.virus";
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }
}
