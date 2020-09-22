package com.example.virussafeagro.viewModel;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToGoogleSearchAPI;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {
    private NetworkConnectionToGoogleSearchAPI networkConnectionToGoogleSearchAPI;

    private MutableLiveData<List<NewsModel>> newsListLD;

    public NewsViewModel() {
        this.networkConnectionToGoogleSearchAPI = new NetworkConnectionToGoogleSearchAPI();
        this.newsListLD = new MutableLiveData<>();
    }

    // for live data
    public void setNewsListLD(List<NewsModel> newsModelList) {
        this.newsListLD.setValue(newsModelList);
    }
    public LiveData<List<NewsModel>> getNewsListLD() {
        return this.newsListLD;
    }


    // for find all virus by AsyncTask
    public void processFindingNewsList() {
        try {
            NewsViewModel.FindNewsListAsyncTask findNewsListAsyncTask = new NewsViewModel.FindNewsListAsyncTask();
            findNewsListAsyncTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FindNewsListAsyncTask extends AsyncTask<Void, Void, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(Void... voids) {
            List<NewsModel> newsModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToGoogleSearchAPI.getAllNews("vic",1);
                newsModelList = MyJsonParser.newsListJsonParser(resultText);
                // get news image
                for (NewsModel newsModel : newsModelList) {
                    Bitmap newsImageBitmap = networkConnectionToGoogleSearchAPI.getImageByURL(newsModel.getNewsImageURL());
                    newsModel.setNewsImage(newsImageBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newsModelList;
        }

        @Override
        protected void onPostExecute(List<NewsModel> resultNewsList) {
            setNewsListLD(resultNewsList);
        }
    }
}
