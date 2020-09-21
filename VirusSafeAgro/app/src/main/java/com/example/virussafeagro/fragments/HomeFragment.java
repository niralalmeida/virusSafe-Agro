package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private ImageView swipeImageView;
    private RelativeLayout homeImageRelativeLayout;

    private LinearLayout waterCalculatorLinearLayout;
//    private LinearLayout newsLinearLayout;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("virusSafe Agro");

        // show back button
        MainActivity.showTopActionBar((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show Home Views
        this.showHomeViews();

        // set Home Image On Click Listener
        this.setHomeImageOnClickListener();
        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_home);
        this.swipeImageView = view.findViewById(R.id.img_swipe_home);
        this.homeImageRelativeLayout = view.findViewById(R.id.rl_image_home);
        this.waterCalculatorLinearLayout = view.findViewById(R.id.ll_water_calculator_home);
//        this.newsLinearLayout = view.findViewById(R.id.ll_news_home);
    }

    // show Home Views
    private void showHomeViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
        new Handler().postDelayed(()->{
            MyAnimationBox.runRepeatedAnimationBottomToTop(swipeImageView, 1000);
        }, 1000);
    }

    private void setHomeImageOnClickListener() {
        this.homeImageRelativeLayout.setOnClickListener(rlView -> {
            MyAnimationBox.runSlideOutAnimationToTop(rlView, 500);
            rlView.setClickable(false);
            rlView.setVisibility(View.GONE);
        });
    }

    private void allTilesOnClickListener() {

        // news
//        this.setNewsTileOnClickListener();
    }

    // news
//    private void setNewsTileOnClickListener() {
//        this.newsLinearLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        this.allViewsRelativeLayout.setVisibility(View.GONE);
    }
}
