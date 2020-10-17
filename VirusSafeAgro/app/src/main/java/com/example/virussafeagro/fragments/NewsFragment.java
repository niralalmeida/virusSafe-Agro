package com.example.virussafeagro.fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.ListNewsAdapter;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.ImageStorage;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.NewsViewModel;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.utils.bitmap.SaveBitmapCallBack;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private boolean isFromInsights;

    private static List<NewsModel> newsModelList;
    private NewsViewModel newsViewModel;

    private LinearLayout processBarLinearLayout;
    private LinearLayout allViewLinearLayout;
    private LinearLayout networkErrorLinearLayout;

    // recycler view
    private ListNewsAdapter listNewsAdapter;
    private RecyclerView recyclerViewForNews;
    private RecyclerView.LayoutManager layoutManager;

    // loading time no
    private static int loadingTimeNo;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_news, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_news);
        // show search button
//        this.mainActivity.getOpenSearchLinearLayout().setVisibility(View.VISIBLE);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize view model
        this.initializeNewsViewModel();
        // initialize Views
        this.initializeViews();

        // check where from
        Bundle bundle = getArguments();
        assert bundle != null;
        isFromInsights = bundle.getBoolean("fromInsights");
        if (isFromInsights){
            // initialize Data
            this.initializeData();
            // show progress bar
            this.processBarLinearLayout.setVisibility(View.VISIBLE);
            this.allViewLinearLayout.setVisibility(View.GONE);

            // find News List by Google search API
            this.findNewsListByGoogleSearchAPI(1);
        } else {
            // show News Views
            showNewsViews();
            // show the news list
            showNewsRecyclerView();
            // set News Tile On Clicked Listener
            setNewsTileOnClickedListener();
            // show smart refresh layout
            initializeHeaderAndFooter();
            // observe the live data for refreshing or loading news
            observeMore10NewsListLD();
        }

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        return this.view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

        // observe NewsListLD
        this.observeNewsListLD();

    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_news);
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_news);
    }

    private void initializeData() {
        newsModelList = new ArrayList<>();
        loadingTimeNo = 1;
    }

    private void initializeNewsViewModel() {
        this.newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
    }

    private void findNewsListByGoogleSearchAPI(int fromNo) {
        this.newsViewModel.processFindingNewsList(fromNo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                    // observe the live data for refreshing or loading news
                    observeMore10NewsListLD();
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
            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), newsDetailFragment, AppResources.FRAGMENT_TAG_NEWS_DETAIL);
        });
    }

    private void initializeHeaderAndFooter() {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout_news);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()));
        // refresh
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            find10MoreNewsByGoogleSearchAPI(1);
            refreshlayout.finishRefresh(2000/*,false*/);// "false" means refreshing fail
        });

        // load more
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            find10MoreNewsByGoogleSearchAPI(loadingTimeNo * 10 + 1);
            loadingTimeNo++;
            refreshlayout.finishLoadMore(2000/*,false*/);// "false" means loading fail
        });
    }

    // for loading more 10 news
    private void find10MoreNewsByGoogleSearchAPI(int fromNo) {
        this.newsViewModel.processFinding10MoreNewsList(fromNo);
    }
    // for loading more 10 news
    private void observeMore10NewsListLD() {
        this.newsViewModel.getMore10NewsListLD().observe(getViewLifecycleOwner(), resultMore10NewsList -> {
            if ((resultMore10NewsList != null) && (resultMore10NewsList.size() != 0)) {
                // check network connection
                if (resultMore10NewsList.get(0).getNewsSnippet().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    listNewsAdapter.addNewsItem(resultMore10NewsList);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);

        // cancel the 2 AsyncTask
        NewsViewModel.FindNewsListAsyncTask findNewsListAsyncTask = this.newsViewModel.getCurrentFindNewsListAsyncTask();
        findNewsListAsyncTask.cancel(true);
        NewsViewModel.Find10MoreNewsAsyncTask find10MoreNewsAsyncTask = this.newsViewModel.getCurrentFind10MoreNewsAsyncTask();
        find10MoreNewsAsyncTask.cancel(true);

        this.newsViewModel.getNewsListLD().removeObservers(getViewLifecycleOwner());
        List<NewsModel> newsModelList = new ArrayList<>();
        this.newsViewModel.setNewsListLD(newsModelList);

        this.newsViewModel.getMore10NewsListLD().removeObservers(getViewLifecycleOwner());
        List<NewsModel> more10NewsList = new ArrayList<>();
        this.newsViewModel.setMore10NewsListLD(more10NewsList);

        assert getArguments() != null;
        getArguments().clear();

        this.mainActivity.setToolkitButton(false);

        // hide search button
//        this.mainActivity.getOpenSearchLinearLayout().setVisibility(View.GONE);
    }
}
