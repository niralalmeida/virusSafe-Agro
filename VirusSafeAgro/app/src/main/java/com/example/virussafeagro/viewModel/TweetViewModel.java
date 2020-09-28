package com.example.virussafeagro.viewModel;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.virussafeagro.models.TweetModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToGoogleSearchAPI;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyJsonParser;

import java.util.ArrayList;
import java.util.List;

public class TweetViewModel extends ViewModel {
    private NetworkConnectionToGoogleSearchAPI networkConnectionToGoogleSearchAPI;
    private FragmentActivity fragmentActivity;

    private MutableLiveData<List<TweetModel>> tweetListLD;
    private MutableLiveData<List<TweetModel>> more10TweetListLD;

    public TweetViewModel() {
        this.networkConnectionToGoogleSearchAPI = new NetworkConnectionToGoogleSearchAPI();
        this.tweetListLD = new MutableLiveData<>();
        this.more10TweetListLD = new MutableLiveData<>();
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    // for first 10 tweet live data
    public void setTweetListLD(List<TweetModel> tweetModelList) {
        this.tweetListLD.setValue(tweetModelList);
    }
    public LiveData<List<TweetModel>> getTweetListLD() {
        return this.tweetListLD;
    }


    // for more 10 tweet live data
    public void setMore10TweetListLD(List<TweetModel> more10TweetList){
        this.more10TweetListLD.setValue(more10TweetList);
    }
    public LiveData<List<TweetModel>> getMore10TweetListLD() {
        return this.more10TweetListLD;
    }


    // for first find 10 tweet by AsyncTask
    public void processFindingTweetList(int fromNo) {
        try {
            TweetViewModel.FindTweetListAsyncTask findTweetListAsyncTask = new TweetViewModel.FindTweetListAsyncTask();
            findTweetListAsyncTask.execute(fromNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class FindTweetListAsyncTask extends AsyncTask<Integer, Void, List<TweetModel>> {
        @Override
        protected List<TweetModel> doInBackground(Integer... integers) {
            List<TweetModel> tweetModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToGoogleSearchAPI.getAllTweet("tomato",integers[0]);
                tweetModelList = MyJsonParser.tweetListJsonParser(resultText);
                // get tweet image
                for (TweetModel tweetModel : tweetModelList) {
                    Bitmap tweetImageBitmap = networkConnectionToGoogleSearchAPI.getImageByURL(tweetModel.getTweetImageURL());
                    tweetModel.setTweetImageBitmap(tweetImageBitmap);
                }
                // set tweet portrait
                for (TweetModel tweetModel : tweetModelList){
                    int resourceNo = (int) (1 + Math.random() * (33 - 1 + 1));
                    Bitmap portraitBitmap = DataConverter.drawableImageToBitmap(fragmentActivity, AppResources.getTweetPortraitImageDrawableId(resourceNo));
                    tweetModel.setTweetPortraitBitmap(portraitBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tweetModelList;
        }

        @Override
        protected void onPostExecute(List<TweetModel> resultTweetList) {
            setTweetListLD(resultTweetList);
        }
    }

    // load 10 more tweet items
    public void processFinding10MoreTweetList(int fromNo) {
        try {
            TweetViewModel.Find10MoreTweetAsyncTask find10MoreTweetAsyncTask = new TweetViewModel.Find10MoreTweetAsyncTask();
            find10MoreTweetAsyncTask.execute(fromNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Find10MoreTweetAsyncTask extends AsyncTask<Integer, Void, List<TweetModel>> {
        @Override
        protected List<TweetModel> doInBackground(Integer... integers) {
            List<TweetModel> tweetModelList = new ArrayList<>();
            try {
                String resultText = networkConnectionToGoogleSearchAPI.getAllTweet("tomato",integers[0]);
                tweetModelList = MyJsonParser.tweetListJsonParser(resultText);
                // get tweet image
                for (TweetModel tweetModel : tweetModelList) {
                    Bitmap tweetImageBitmap = networkConnectionToGoogleSearchAPI.getImageByURL(tweetModel.getTweetImageURL());
                    tweetModel.setTweetImageBitmap(tweetImageBitmap);
                }
                // set tweet portrait
                for (TweetModel tweetModel : tweetModelList){
                    int resourceNo = (int) (1 + Math.random() * (33 - 1 + 1));
                    Bitmap portraitBitmap = DataConverter.drawableImageToBitmap(fragmentActivity, AppResources.getTweetPortraitImageDrawableId(resourceNo));
                    tweetModel.setTweetPortraitBitmap(portraitBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tweetModelList;
        }

        @Override
        protected void onPostExecute(List<TweetModel> resultMore10TweetList) {
            setMore10TweetListLD(resultMore10TweetList);
        }
    }
}
