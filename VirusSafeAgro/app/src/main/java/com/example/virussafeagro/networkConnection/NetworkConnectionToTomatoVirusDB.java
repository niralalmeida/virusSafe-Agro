package com.example.virussafeagro.networkConnection;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionToTomatoVirusDB {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToTomatoVirusDB() {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)// the time for connection
                .readTimeout(10, TimeUnit.SECONDS)// the time for reading
                .build();
    }

    public String getAllVirus() {
        String resultText = "";
        final String API_URL = "https://5cu3hhvh8d.execute-api.us-east-1.amazonaws.com/virusStage/virusresource";
        Request request = new Request.Builder().url(API_URL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public String getAllQuestions(int virusId) {
        String resultText = "";
        final String API_URL = "https://my077qg7q1.execute-api.us-east-1.amazonaws.com/choiceQuestionStage/choicequestionresource?virusId=";
        String searchURL = API_URL + virusId;
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public String getAllOptions(int choiceQuestionId) {
        String resultText = "";
        final String API_URL = "https://vhknvesdne.execute-api.us-east-1.amazonaws.com/choiceOptionStage/choiceoptionresource/?choiceQuestionId=";
        String searchURL = API_URL + choiceQuestionId;
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public String getQuestionsImage(int choiceQuestionId) {
        String resultText = "";
        final String API_URL = "https://c5mj2eptdc.execute-api.us-east-1.amazonaws.com/questionImageStage/questionimage?choiceQuestionId=";
        String searchURL = API_URL + choiceQuestionId;
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public String getOptionsImage(int choiceOptionId) {
        String resultText = "";
        final String API_URL = "https://o16onbsxxl.execute-api.us-east-1.amazonaws.com/optionImageStage/optionimage?choiceOptionId=";
        String searchURL = API_URL + choiceOptionId;
        Request request = new Request.Builder().url(searchURL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public String getAllNutrients() {
        String resultText = "";
        final String API_URL = "https://l0j2i6t18a.execute-api.us-east-1.amazonaws.com/nutrientStage/nutrientresource";
        Request request = new Request.Builder().url(API_URL).build();
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            resultText = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }
}
