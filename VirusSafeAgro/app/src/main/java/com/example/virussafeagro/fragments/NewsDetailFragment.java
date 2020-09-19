package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.ListNewsAdapter;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.viewModel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsDetailFragment extends Fragment {
    private View view;

    private NewsModel currentNewsModel;

    private LinearLayout allViewLinearLayout;
    private WebView newsWebView;

    public NewsDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("News Content");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // get passed bundle and the NewsModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentNewsModel = bundle.getParcelable("currentNewsModel");

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show news views
        this.showNewsViews();
        // show news content
        this.showNewsWebView();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news_detail);
        this.newsWebView = view.findViewById(R.id.wv_news_detail);
    }

    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(allViewLinearLayout, 1000);
    }

    private void showNewsWebView() {
        this.newsWebView.loadUrl(currentNewsModel.getNewsURL());
        this.newsWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);
    }
}