package com.example.virussafeagro.networkConnection;

import android.os.Looper;

import com.example.virussafeagro.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.RequestBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnectionToMLModel {
    private static final String BASE_URL = "http://6ee3f0c6903e.ngrok.io/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    private AsyncHttpClient asyncHttpClient;
    private RequestParams params;
    private String resultText;

    public NetworkConnectionToMLModel() {
        this.okHttpClient = new OkHttpClient();

        this.asyncHttpClient = new AsyncHttpClient();
        this.params = new RequestParams();
        this.resultText = "";
    }

    public String getImageIdentificationFeedback(String uploadImageBitmapString) {
        String resultText = "";
        final String methodPathForImageMLModel = BASE_URL + "Image";

//        String uploadImageJson = "{\"image\":\"" + uploadImageBitmapString + "\"}";
        Gson gson = new Gson();
        String uploadImageJson = gson.toJson(uploadImageBitmapString);

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
