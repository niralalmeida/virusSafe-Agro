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

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private View view;

    private LinearLayout allViewLinearLayout;
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

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_home);
        this.waterCalculatorLinearLayout = view.findViewById(R.id.ll_water_calculator_home);
//        this.newsLinearLayout = view.findViewById(R.id.ll_news_home);
    }

    // show Home Views
    private void showHomeViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewLinearLayout, 1000);
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
        this.allViewLinearLayout.setVisibility(View.GONE);
    }
}
