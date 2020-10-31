package com.example.virussafeagro.fragments;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

public class InsightsFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout newsRelativeLayout;
    private RelativeLayout tweetsRelativeLayout;

    public InsightsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_insights, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_insights);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_INSIGHTS);

        // initialize Views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.newsRelativeLayout = view.findViewById(R.id.rl_news_insights);
        this.tweetsRelativeLayout = view.findViewById(R.id.rl_tweets_insights);
    }

    private void allTilesOnClickListener() {
        // news
        this.setNewsTileOnClickListener();
        // tweets
        this.setTweetsTileOnClickListener();
    }

    // news
    private void setNewsTileOnClickListener() {
        this.newsRelativeLayout.setOnClickListener(llView -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromInsights", true);
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(bundle);

            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), newsFragment, AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }

    // tweets
    private void setTweetsTileOnClickListener() {
        this.tweetsRelativeLayout.setOnClickListener(llView -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromInsights", true);
            TweetFragment tweetFragment = new TweetFragment();
            tweetFragment.setArguments(bundle);

            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), tweetFragment, AppResources.FRAGMENT_TAG_TWEET_LIST);
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        this.mainActivity.setToolkitButton(false);
    }
}
