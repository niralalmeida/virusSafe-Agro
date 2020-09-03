package com.example.virussafeagro.networkConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionToTomatoVirusDB {
//    private static final String BASE_URL = "https://jjc8sxzno2.execute-api.us-east-1.amazonaws.com/virusStage/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    public NetworkConnectionToTomatoVirusDB() {
        this.okHttpClient = new OkHttpClient();
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

    public String getAllAnswers(int virusId) {
        String resultText = "";
        String searchURL = "tovrestws.choicequestion/quizQuestion/findAllAnswersByVirusId/" + virusId;
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
