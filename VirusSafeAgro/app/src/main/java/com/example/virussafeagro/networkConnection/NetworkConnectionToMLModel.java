package com.example.virussafeagro.networkConnection;

import android.os.Looper;

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
        String searchURL = BASE_URL + "Image";

        this.params.put("image", uploadImageBitmapString);
        Looper.prepare();
//        this.asyncHttpClient.get(searchURL, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
//                // Handle resulting parsed JSON response here
//                try {
//                    resultText = response.getString("prediction");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//        });
        return this.resultText;
    }
}
