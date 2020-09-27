package com.example.virussafeagro.viewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToGoogleSearchAPI;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;


public class NewsDetailViewModel extends ViewModel {
    private NetworkConnectionToGoogleSearchAPI networkConnectionToGoogleSearchAPI;

    private MutableLiveData<List<String>> newsArticleBodyLD;

    public NewsDetailViewModel() {
        this.networkConnectionToGoogleSearchAPI = new NetworkConnectionToGoogleSearchAPI();
        this.newsArticleBodyLD = new MutableLiveData<>();
    }

    public void setNewsArticleBodyLD(List<String> newsArticleBody) {
        this.newsArticleBodyLD.setValue(newsArticleBody);
    }
    public LiveData<List<String>> getNewsArticleBodyLD() {
        return this.newsArticleBodyLD;
    }

    public void processFindingNewsArticleBody(String newsURL) {
        try{
            FindNewsArticleBodyAsyncTask findNewsArticleBodyAsyncTask = new FindNewsArticleBodyAsyncTask();
            findNewsArticleBodyAsyncTask.execute(newsURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FindNewsArticleBodyAsyncTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> newsArticleBody = new ArrayList<>();
            try {
                String newsItemHTMLResult = networkConnectionToGoogleSearchAPI.getNewsItemHTML(strings[0]);
                newsArticleBody = MyJsonParser.newsArticleBodyHTMLParser(newsItemHTMLResult);
            } catch (Exception e){
                e.printStackTrace();
            }
            return newsArticleBody;
        }

        @Override
        protected void onPostExecute(List<String> resultNewsArticleBody) {
            setNewsArticleBodyLD(resultNewsArticleBody);
        }
    }
}
