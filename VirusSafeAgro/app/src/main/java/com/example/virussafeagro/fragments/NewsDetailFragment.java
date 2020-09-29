package com.example.virussafeagro.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.models.NewsModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.viewModel.NewsDetailViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class NewsDetailFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private NewsModel currentNewsModel;
    private NewsDetailViewModel newsDetailViewModel;

    private LinearLayout allViewLinearLayout;
    // top
    private RelativeLayout topRelativeLayout;
    private ImageView newsImageView;
    private LinearLayout topTextBGLinearLayout;
    private LinearLayout topTitleLinearLayout;
    private TextView newsTitleTextView;
    private TextView newsAuthorTextView;
    private TextView newsPostedTimeAgoTextView;
    private TextView newsTimeTextView;
    // article
    private NestedScrollView newsArticleNestedScrollView;
    private com.uncopt.android.widget.text.justify.JustifiedTextView newsSnippetTextView;
    private com.uncopt.android.widget.text.justify.JustifiedTextView newsArticleBodyTextView;

    // for gesture
    private boolean isNewsDetailViewShown;
    private int originalImageHeight;
    private float startY;
    private float currentY;
    private boolean isImageFolded;

    public NewsDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText("News Content");
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // get passed bundle and the NewsModel within it
        Bundle bundle = getArguments();
        assert bundle != null;
        this.currentNewsModel = bundle.getParcelable("currentNewsModel");

        // initialize news detail view model
        this.initializeNewsDetailViewModel();
        // initialize Views
        this.initializeViews();
        this.allViewLinearLayout.setVisibility(View.GONE);

        return this.view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        // find news article body
        this.findNewsArticleBody();
        // observe NewsArticleBody live data
        this.observeNewsArticleBodyLD();
        // set gesture listener
        this.setGestureListener();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_news_detail);
        this.topRelativeLayout = view.findViewById(R.id.rl_top_news_detail);
        this.newsImageView = view.findViewById(R.id.img_top_pic_virus_detail);
        this.topTextBGLinearLayout = view.findViewById(R.id.ll_top_text_bg_news_detail);
        this.topTitleLinearLayout = view.findViewById(R.id.ll_top_title_news_detail);
        this.newsTitleTextView = view.findViewById(R.id.tv_title_news_detail);
        this.newsAuthorTextView = view.findViewById(R.id.tv_author_news_detail);
        this.newsPostedTimeAgoTextView = view.findViewById(R.id.tv_posted_time_ago_news_detail);
        this.newsTimeTextView = view.findViewById(R.id.tv_time_news_detail);
        this.newsArticleNestedScrollView = view.findViewById(R.id.nsv_article_news_detail);
        this.newsSnippetTextView = view.findViewById(R.id.tv_snippet_news_detail);
        this.newsArticleBodyTextView = view.findViewById(R.id.tv_article_body_news_detail);
    }

    private void initializeNewsDetailViewModel() {
        this.newsDetailViewModel = new ViewModelProvider(requireActivity()).get(NewsDetailViewModel.class);
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


    // show News Views
    private void showNewsViews() {
        MyAnimationBox.runFadeInAnimation(allViewLinearLayout, 1000);
        new Handler().postDelayed(() -> {
            isNewsDetailViewShown = true;
            originalImageHeight = newsImageView.getHeight();
            topRelativeLayout.setActivated(true);
        }, 1100);
    }

    private void showNewsContent() {
        // news title
        newsTitleTextView.setText(currentNewsModel.getNewsTitle());

        // news author
        newsAuthorTextView.setText(currentNewsModel.getNewsAuthor());

        // time
        String originalTimeString = currentNewsModel.getNewsPressTime(); // "yyyy-MM-dd'T'HH:mm:ssXXX"
        String newsPostedTimeAgo = DataConverter.getShortTime(originalTimeString);
            // news posted time ago
        newsPostedTimeAgoTextView.setText(newsPostedTimeAgo);
            // news press time
        newsTimeTextView.setText(originalTimeString);

        // image
        newsImageView.setImageBitmap(currentNewsModel.getNewsImage());

        // news snippet
        newsSnippetTextView.setText(currentNewsModel.getNewsSnippet());

        // news article body
        StringBuilder newsArticleBodyStringBuilder = new StringBuilder();
        for (String newsParagraph : currentNewsModel.getNewsArticleBody()) {
            newsArticleBodyStringBuilder.append(newsParagraph).append("\n\n");
        }
        newsArticleBodyTextView.setText(newsArticleBodyStringBuilder.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void setGestureListener() {
        // listen to nested scroll view --> fold the image
        newsArticleNestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (isNewsDetailViewShown){
                // check swipe up
                if (!isImageFolded){
                    if (newsImageView.getHeight() > topTitleLinearLayout.getBottom()) {
                        if (topTextBGLinearLayout.getVisibility() == View.GONE){
                            MyAnimationBox.runSlideInAnimationFromTop(topTextBGLinearLayout, 100);
                            new Handler().postDelayed(() -> {
                                MyAnimationBox.runChangeViewSizeAnimation(newsTitleTextView, 30f, 22f, 130);
                            }, 80);
                            new Handler().postDelayed(() -> {
                                MyAnimationBox.runFoldViewAnimation(newsImageView, newsImageView.getHeight(), topTitleLinearLayout.getBottom(), 130);
                            }, 190);
                            new Handler().postDelayed(() -> {
                                isImageFolded = true;
                                topRelativeLayout.setClickable(false);
                            }, 300);
                        } else {
                            MyAnimationBox.runChangeViewSizeAnimation(newsTitleTextView, 30f, 22f, 130);
                            new Handler().postDelayed(() -> {
                                MyAnimationBox.runFoldViewAnimation(newsImageView, newsImageView.getHeight(), topTitleLinearLayout.getBottom(), 130);
                            }, 110);

                            new Handler().postDelayed(() -> {
                                isImageFolded = true;
                                topRelativeLayout.setClickable(false);
                            }, 220);
                        }
                    }
                }
            }
        });

        // listen to view --> unfold and show the image again
        view.setOnTouchListener((v, event) -> {
            if (isImageFolded) {
                // when press
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startY = event.getY();
                }
                // when move up
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    currentY = event.getY();

                    if (startY < newsImageView.getHeight() && currentY >= startY){
                        isImageFolded = false;
                        topRelativeLayout.setClickable(true);
                        topRelativeLayout.setActivated(true);
                        MyAnimationBox.runChangeViewSizeAnimation(newsTitleTextView, 22f, 30f, 200);
                        MyAnimationBox.runFoldViewAnimation(newsImageView, newsImageView.getHeight(), originalImageHeight, 200);
                    }
                }
            }
            return true;
        });

        // listen to the image on click listener
        topRelativeLayout.setOnClickListener(topView -> {
            if (!isImageFolded) {
                if (topView.isActivated()){
                    // uncover the image
                    topView.setActivated(false);
                    MyAnimationBox.runSlideOutAnimationToTop(topTextBGLinearLayout, 200);
                } else {
                    // cover the image
                    topView.setActivated(true);
                    MyAnimationBox.runSlideInAnimationFromTop(topTextBGLinearLayout, 200);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewLinearLayout.setVisibility(View.GONE);

        this.newsDetailViewModel.getNewsArticleBodyLD().removeObservers(getViewLifecycleOwner());
        this.newsDetailViewModel.setNewsArticleBodyLD(new ArrayList<>());
    }
}
