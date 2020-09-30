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
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class FactorsFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private RelativeLayout causeRelativeLayout;
    private RelativeLayout environmentalConditionsRelativeLayout;

    public FactorsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_factors, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_factor);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

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
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_factors);
        this.causeRelativeLayout = view.findViewById(R.id.rl_cause_factors);
        this.environmentalConditionsRelativeLayout = view.findViewById(R.id.rl_environmental_conditions_factors);
    }

    // show All Views
    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    private void allTilesOnClickListener() {
        // cause
        this.setCauseTileOnClickListener();
        // environmental conditions
        this.setEnvironmentalConditionsTileOnClickListener();
    }

    // cause
    private void setCauseTileOnClickListener() {
        this.causeRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }

    // environmental conditions
    private void setEnvironmentalConditionsTileOnClickListener() {
        this.environmentalConditionsRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }
}
