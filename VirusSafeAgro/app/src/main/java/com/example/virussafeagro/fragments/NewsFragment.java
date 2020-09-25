package com.example.virussafeagro.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.NewsViewModel;
import com.example.virussafeagro.viewModel.VirusInfoListViewModel;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsFragment extends Fragment {
    private View view;

    private List<NewsModel> newsModelList;
    private NewsViewModel newsViewModel;

    private LinearLayout processBarLinearLayout;
    private LinearLayout allViewLinearLayout;
    private LinearLayout networkErrorLinearLayout;

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
        // initialize view model
        this.initializeNewsViewModel();
        // initialize Views
        this.initializeViews();
        this.processBarLinearLayout.setVisibility(View.VISIBLE);
        this.allViewLinearLayout.setVisibility(View.GONE);

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // find News List by Google search API
        this.findNewsListByGoogleSearchAPI();
        // observe NewsListLD
        this.observeNewsListLD();

    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_news);
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_news);
    }

    private void initializeData() {
        this.newsModelList = new ArrayList<>();
    }

    private void initializeNewsViewModel() {
        this.newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
    }

    private void findNewsListByGoogleSearchAPI() {
        this.newsViewModel.processFindingNewsList();
    }

    private void observeNewsListLD() {
        this.newsViewModel.getNewsListLD().observe(getViewLifecycleOwner(), resultNewsList -> {
            if ((resultNewsList != null) && (resultNewsList.size() != 0)) {
                // hide process bar
                processBarLinearLayout.setVisibility(View.GONE);
                // check network connection
                if (resultNewsList.get(0).getNewsSnippet().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    newsModelList.clear();
                    newsModelList = resultNewsList;

                    // show News Views
                    showNewsViews();
                    // show the news list
                    showNewsRecyclerView();
                    // set News Tile On Clicked Listener
                    setNewsTileOnClickedListener();
                    // show smart refresh layout
                    initializeHeaderAndFooter();
                }
            }
        });
    }

    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(allViewLinearLayout, 1000);
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
            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
            newsDetailFragment.setArguments(bundle);
            FragmentOperator.replaceFragment(requireActivity(), newsDetailFragment, AppResources.FRAGMENT_TAG_NEWS_DETAIL);
        });
    }

    private void initializeHeaderAndFooter() {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout_news);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(2000/*,false*/);// "false" means refreshing fail
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(2000/*,false*/);// "false" means loading fail
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);

        this.newsViewModel.getNewsListLD().removeObservers(getViewLifecycleOwner());
        List<NewsModel> newsModelList = new ArrayList<>();
        this.newsViewModel.setNewsListLD(newsModelList);
    }
}
