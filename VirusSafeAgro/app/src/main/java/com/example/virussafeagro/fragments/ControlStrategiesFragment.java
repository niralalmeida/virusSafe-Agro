package com.example.virussafeagro.fragments;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;

public class ControlStrategiesFragment extends Fragment {
    private MainActivity mainActivity;
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

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_control_strategies);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isToolkitIconClicked()) {
            this.mainActivity.setToolkitButton(true);
        }

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_control);
        this.chemicalRelativeLayout = view.findViewById(R.id.rl_chemical_control);
        this.nonChemicalRelativeLayout = view.findViewById(R.id.rl_non_chemical_control);
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
            ChemicalControlFragment chemicalControlFragment = new ChemicalControlFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                chemicalControlFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(chemicalRelativeLayout, ViewCompat.getTransitionName(chemicalRelativeLayout))
                    .replace(R.id.fl_fragments, chemicalControlFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // non-chemical
    private void setNonChemicalTileOnClickListener() {
        this.nonChemicalRelativeLayout.setOnClickListener(llView -> {
            NonChemicalControlFragment nonChemicalControlFragment = new NonChemicalControlFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                nonChemicalControlFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(nonChemicalRelativeLayout, ViewCompat.getTransitionName(nonChemicalRelativeLayout))
                    .replace(R.id.fl_fragments, nonChemicalControlFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        this.mainActivity.setToolkitButton(false);
    }
}
