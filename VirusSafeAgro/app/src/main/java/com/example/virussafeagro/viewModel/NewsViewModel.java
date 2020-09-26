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
    private MutableLiveData<List<NewsModel>> more10NewsListLD;

    public NewsViewModel() {
        this.networkConnectionToGoogleSearchAPI = new NetworkConnectionToGoogleSearchAPI();
        this.newsListLD = new MutableLiveData<>();
        this.more10NewsListLD = new MutableLiveData<>();
    }

    // for first 10 news live data
    public void setNewsListLD(List<NewsModel> newsModelList) {
        this.newsListLD.setValue(newsModelList);
    }
    public LiveData<List<NewsModel>> getNewsListLD() {
        return this.newsListLD;
    }


    // for more 10 news live data
    public void setMore10NewsListLD(List<NewsModel> more10NewsList){
        this.more10NewsListLD.setValue(more10NewsList);
    }
    public LiveData<List<NewsModel>> getMore10NewsListLD() {
        return this.more10NewsListLD;
    }


    // for first find 10 news by AsyncTask
    public void processFindingNewsList(int fromNo) {
        try {
            NewsViewModel.FindNewsListAsyncTask findNewsListAsyncTask = new NewsViewModel.FindNewsListAsyncTask();
            findNewsListAsyncTask.execute(fromNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindNewsListAsyncTask extends AsyncTask<Integer, Void, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(Integer... integers) {
            List<NewsModel> newsModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToGoogleSearchAPI.getAllNews("vic",integers[0]);
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

    // load 10 more news items
    public void processFinding10MoreNewsList(int fromNo) {
        try {
            Find10MoreNewsAsyncTask find10MoreNewsAsyncTask = new Find10MoreNewsAsyncTask();
            find10MoreNewsAsyncTask.execute(fromNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Find10MoreNewsAsyncTask extends AsyncTask<Integer, Void, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(Integer... integers) {
            List<NewsModel> newsModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToGoogleSearchAPI.getAllNews("vic",integers[0]);
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
        protected void onPostExecute(List<NewsModel> resultMore10NewsList) {
            setMore10NewsListLD(resultMore10NewsList);
        }
    }
}
