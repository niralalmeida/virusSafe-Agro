package com.example.virussafeagro.networkConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionToGoogleSearchAPI {
    private static final String API_KEY = "AIzaSyCKl7VgrECl32m3i6EoeUGJPnSWwQt9a1E";
    private static final String SEARCH_ID_cx = "351def83661bfe7c1";

    private OkHttpClient okHttpClient;

    public NetworkConnectionToGoogleSearchAPI() {
        this.okHttpClient = new OkHttpClient();
    }

    public String getAllNews(String keyword, int startFromWhichItem) {
        String resultText = "";
        keyword = keyword.replace(" ", "+");
        String searchURL = "https://www.googleapis.com/customsearch/v1?key="
                + API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + "&sort=date:d&start=" + startFromWhichItem;

        // test
        System.out.println("URL ====> " + searchURL);

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
