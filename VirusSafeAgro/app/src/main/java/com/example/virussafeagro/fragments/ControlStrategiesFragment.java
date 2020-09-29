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

public class ControlStrategiesFragment extends Fragment {
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private RelativeLayout chemicalRelativeLayout;
    private RelativeLayout nonChemicalRelativeLayout;

    public ControlStrategiesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_control_strategies, container, false);

        // set title
        Objects.requireNonNull(Objects.requireNonNull((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Control Strategies");

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
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_control);
        this.chemicalRelativeLayout = view.findViewById(R.id.rl_chemical_control);
        this.nonChemicalRelativeLayout = view.findViewById(R.id.rl_non_chemical_control);
    }

    // show All Views
    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    private void allTilesOnClickListener() {
        // chemical
        this.setChemicalTileOnClickListener();
        // non-chemical
        this.setNonChemicalTileOnClickListener();
    }

    // chemical
    private void setChemicalTileOnClickListener() {
        this.chemicalRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }

    // non-chemical
    private void setNonChemicalTileOnClickListener() {
        this.nonChemicalRelativeLayout.setOnClickListener(llView -> {
//            FragmentOperator.replaceFragment(requireActivity(), new NewsFragment(), AppResources.FRAGMENT_TAG_NEWS_LIST);
        });
    }
}
