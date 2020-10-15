package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.adapters.ListTweetAdapter;
import com.example.virussafeagro.models.TweetModel;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.TweetViewModel;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class TweetFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;
    private boolean isFromInsights;

    private static List<TweetModel> tweetModelList;
    private TweetViewModel tweetViewModel;

    private LinearLayout processBarLinearLayout;
    private LinearLayout allViewLinearLayout;
    private LinearLayout networkErrorLinearLayout;

    // recycler view
    private ListTweetAdapter listTweetAdapter;
    private RecyclerView recyclerViewForTweet;
    private RecyclerView.LayoutManager layoutManager;

    // loading time no
    private static int loadingTimeNo;

    public TweetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_tweet, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_tweet);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize view model
        this.initializeTweetViewModel();
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

            // find Tweet List by Google search API
            this.findTweetListByGoogleSearchAPI(1);
        } else {
            // show Tweet Views
            showTweetViews();
            // show the tweet list
            showTweetRecyclerView();
            // show smart refresh layout
            initializeHeaderAndFooter();
            // observe the live data for refreshing or loading tweet
            observeMore10TweetListLD();
        }

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();


        // observe TweetListLD
        this.observeTweetListLD();

    }

    private void initializeViews() {
        this.processBarLinearLayout = view.findViewById(R.id.ll_process_bar_tweet);
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_tweet);
        this.networkErrorLinearLayout = view.findViewById(R.id.ll_fail_network_tweet);
    }

    private void initializeData() {
        tweetModelList = new ArrayList<>();
        loadingTimeNo = 1;
    }

    private void initializeTweetViewModel() {
        this.tweetViewModel = new ViewModelProvider(requireActivity()).get(TweetViewModel.class);
        this.tweetViewModel.setFragmentActivity(requireActivity());
    }

    private void findTweetListByGoogleSearchAPI(int fromNo) {
        this.tweetViewModel.processFindingTweetList(fromNo);
    }

    private void observeTweetListLD() {
        this.tweetViewModel.getTweetListLD().observe(getViewLifecycleOwner(), resultTweetList -> {
            if ((resultTweetList != null) && (resultTweetList.size() != 0)) {
                // hide process bar
                processBarLinearLayout.setVisibility(View.GONE);
                // check network connection
                if (resultTweetList.get(0).getTweetContent().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    tweetModelList.clear();
                    tweetModelList = resultTweetList;

                    // show Tweet Views
                    showTweetViews();
                    // show the tweet list
                    showTweetRecyclerView();
                    // show smart refresh layout
                    initializeHeaderAndFooter();
                    // observe the live data for refreshing or loading tweet
                    observeMore10TweetListLD();
                }
            }
        });
    }

    // show Tweet Views
    private void showTweetViews() {
        MyAnimationBox.runFadeInAnimation(allViewLinearLayout, 1000);
    }

    private void showTweetRecyclerView() {
        listTweetAdapter = new ListTweetAdapter(tweetModelList, requireActivity());
        recyclerViewForTweet = view.findViewById(R.id.rv_tweet_list);
        recyclerViewForTweet.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewForTweet.setAdapter(listTweetAdapter);
        layoutManager = new LinearLayoutManager(requireActivity());
        recyclerViewForTweet.setLayoutManager(layoutManager);
    }

    private void initializeHeaderAndFooter() {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout_tweet);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()));
        // refresh
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            find10MoreTweetByGoogleSearchAPI(1);
            refreshlayout.finishRefresh(2000/*,false*/);// "false" means refreshing fail
        });

        // load more
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            find10MoreTweetByGoogleSearchAPI(loadingTimeNo * 10 + 1);
            loadingTimeNo++;
            refreshlayout.finishLoadMore(2000/*,false*/);// "false" means loading fail
        });
    }

    // for loading more 10 tweet
    private void find10MoreTweetByGoogleSearchAPI(int fromNo) {
        this.tweetViewModel.processFinding10MoreTweetList(fromNo);
    }
    // for loading more 10 tweet
    private void observeMore10TweetListLD() {
        this.tweetViewModel.getMore10TweetListLD().observe(getViewLifecycleOwner(), resultMore10TweetList -> {
            if ((resultMore10TweetList != null) && (resultMore10TweetList.size() != 0)) {
                // check network connection
                if (resultMore10TweetList.get(0).getTweetContent().equals(MyJsonParser.CONNECTION_ERROR_MESSAGE)) {
                    Toast.makeText(requireActivity(),MyJsonParser.CONNECTION_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    // show network error image
                    MyAnimationBox.runFadeInAnimation(networkErrorLinearLayout, 1000);
                } else {
                    listTweetAdapter.addTweetItem(resultMore10TweetList);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);

        // cancel the 2 AsyncTask
        TweetViewModel.FindTweetListAsyncTask findTweetListAsyncTask = this.tweetViewModel.getCurrentFindTweetListAsyncTask();
        findTweetListAsyncTask.cancel(true);
        TweetViewModel.Find10MoreTweetAsyncTask find10MoreTweetAsyncTask = this.tweetViewModel.getCurrentFind10MoreTweetAsyncTask();
        find10MoreTweetAsyncTask.cancel(true);

        this.tweetViewModel.getTweetListLD().removeObservers(getViewLifecycleOwner());
        List<TweetModel> tweetModelList = new ArrayList<>();
        this.tweetViewModel.setTweetListLD(tweetModelList);

        this.tweetViewModel.getMore10TweetListLD().removeObservers(getViewLifecycleOwner());
        List<TweetModel> more10TweetList = new ArrayList<>();
        this.tweetViewModel.setMore10TweetListLD(more10TweetList);

        assert getArguments() != null;
        getArguments().clear();

        this.mainActivity.setToolkitButton(false);
    }
}
