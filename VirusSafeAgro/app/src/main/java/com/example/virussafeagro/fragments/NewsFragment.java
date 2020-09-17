package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.ListNewsAdapter;
import com.example.virussafeagro.adapters.ListQuizResultAdapter;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsFragment extends Fragment {
    private View view;

    private List<NewsModel> newsModelList;

    private LinearLayout allViewLinearLayout;

    // recycler view
    private ListNewsAdapter listNewsAdapter;
    private RecyclerView recyclerViewForNews;
    private RecyclerView.LayoutManager layoutManager;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_news, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("News");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize Data
        this.initializeData();
        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show News Views
        this.showNewsViews();

    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news);
    }

    private void initializeData() {
        this.newsModelList = new ArrayList<>();
    }

    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewLinearLayout, 1000);
    }

    private void showNewsRecyclerView() {
        listNewsAdapter = new ListNewsAdapter(newsModelList, requireActivity());
        recyclerViewForNews = view.findViewById(R.id.rv_news_list);
        recyclerViewForNews.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForNews.setAdapter(listNewsAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForNews.setLayoutManager(layoutManager);
    }

    private void setNewsTileOnClickedListener() {
        listNewsAdapter.setOnNewsTileClickListener(position -> {
            Bundle bundle = new Bundle();
            NewsModel currentNewsModel = newsModelList.get(position);
            bundle.putParcelable("currentNewsModel", currentNewsModel);
//            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
//            newsDetailFragment.setArguments(bundle);
//            FragmentOperator.replaceFragment(requireActivity(), newsDetailFragment, AppResources.FRAGMENT_TAG_NEWS_DETAIL);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);
    }
}