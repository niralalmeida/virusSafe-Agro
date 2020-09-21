package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class InsightsFragment extends Fragment {
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private RelativeLayout newsRelativeLayout;
    private RelativeLayout tweetsRelativeLayout;
    private RelativeLayout videosRelativeLayout;

    public InsightsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_insights, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Insights");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show all views
        this.showAllViews();

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_insights);
        this.newsRelativeLayout = view.findViewById(R.id.rl_news_insights);
        this.tweetsRelativeLayout = view.findViewById(R.id.rl_tweets_insights);
        this.videosRelativeLayout = view.findViewById(R.id.rl_videos_insights);
    }

    // show All Views
    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    private void allTilesOnClickListener() {
        // news
        this.setNewsTileOnClickListener();
        // tweets
        this.setTweetsTileOnClickListener();
        // videos
        this.setVideosTileOnClickListener();
    }

    // news
    private void setNewsTileOnClickListener() {
        this.newsRelativeLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }

    // tweets
    private void setTweetsTileOnClickListener() {
        this.tweetsRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }

    // videos
    private void setVideosTileOnClickListener() {
        this.videosRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }
}
