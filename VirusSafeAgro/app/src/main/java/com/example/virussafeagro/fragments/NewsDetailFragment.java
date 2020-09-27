package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.virussafeagro.viewModel.NewsDetailViewModel;
import com.example.virussafeagro.viewModel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsDetailFragment extends Fragment {
    private View view;

    private NewsModel currentNewsModel;
    private NewsDetailViewModel newsDetailViewModel;

    private LinearLayout allViewLinearLayout;
//    private WebView newsWebView;
    private TextView newsArticleBodyTextView;

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

        // initialize news detail view model
        this.initializeNewsDetailViewModel();
        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // find news article body
        this.findNewsArticleBody();
        // observe NewsArticleBody live data
        this.observeNewsArticleBodyLD();

    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news_detail);
//        this.newsWebView = view.findViewById(R.id.wv_news_detail);
        this.newsArticleBodyTextView = view.findViewById(R.id.tv_article_body_news_detail);
    }

    private void initializeNewsDetailViewModel() {
        this.newsDetailViewModel = new ViewModelProvider(requireActivity()).get(NewsDetailViewModel.class);
    }

    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(allViewLinearLayout, 1000);
    }

    // find news article body
    private void findNewsArticleBody() {
        this.newsDetailViewModel.processFindingNewsArticleBody(currentNewsModel.getNewsURL());
    }
    private void observeNewsArticleBodyLD() {
        this.newsDetailViewModel.getNewsArticleBodyLD().observe(getViewLifecycleOwner(), resultNewsArticleBody -> {
            if(resultNewsArticleBody != null && (!resultNewsArticleBody.isEmpty())) {
                currentNewsModel.setNewsArticleBody(resultNewsArticleBody);

                // show news article body
                showNewsContent();
                // show news views
                showNewsViews();
            }
        });
    }

    private void showNewsContent() {
        StringBuilder newsArticleBodyStringBuilder = new StringBuilder();
        for (String newsParagraph : currentNewsModel.getNewsArticleBody()) {
            newsArticleBodyStringBuilder.append(newsParagraph).append("\n\n");
        }
        newsArticleBodyTextView.setText(newsArticleBodyStringBuilder.toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);

        this.newsDetailViewModel.getNewsArticleBodyLD().removeObservers(getViewLifecycleOwner());
        this.newsDetailViewModel.setNewsArticleBodyLD(new ArrayList<>());
    }
}
