package com.example.virussafeagro.networkConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class NetworkConnectionToMLModel {
    private static final String BASE_URL = "";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToMLModel() {
        this.okHttpClient = new OkHttpClient();
    }


}
