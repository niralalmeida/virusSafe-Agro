package com.example.virussafeagro.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.DetectActivity;
import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.TomatoCameraActivity;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

public class ToolkitFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    // top
    private LinearLayout virusCheckLinearLayout;
    private LinearLayout tomatoDetectLinearLayout;
    private LinearLayout tomatoDetectIconLinearLayout;
    private LinearLayout learnLinearLayout;
    // middle
    private LinearLayout controlStrategiesLinearLayout;
    private LinearLayout pesticideStoresLinearLayout;
    private LinearLayout factorsLinearLayout;
    private LinearLayout insightsLinearLayout;

    public ToolkitFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_toolkit, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_toolkit);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // pop all fragment in the stack
        FragmentOperator.popAllFragmentsInStack(getChildFragmentManager());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_TOOLKIT);

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

        // move Calculator And More To Right
        this.mainActivity.moveTipAndMoreToRight(getTag(), 200);

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.virusCheckLinearLayout = view.findViewById(R.id.ll_virus_check_toolkit);
        this.tomatoDetectLinearLayout = view.findViewById(R.id.ll_tomato_detect_toolkit);
        this.tomatoDetectIconLinearLayout = view.findViewById(R.id.ll_tomato_detect_icon_toolkit);
        this.learnLinearLayout = view.findViewById(R.id.ll_learn_toolkit);
        this.controlStrategiesLinearLayout = view.findViewById(R.id.ll_control_strategies_toolkit);
        this.pesticideStoresLinearLayout = view.findViewById(R.id.ll_pesticide_store_toolkit);
        this.factorsLinearLayout = view.findViewById(R.id.ll_factors_toolkit);
        this.insightsLinearLayout = view.findViewById(R.id.ll_insights_toolkit);
    }

    private void allTilesOnClickListener() {

        // virus check
        this.setVirusCheckTileOnClickListener();
        // tomato detect
        this.setTomatoDetectTileOnClickListener();
        // virus info
        this.setLearnTileOnClickListener();

        // control strategies
        this.setControlStrategiesOnClickListener();
        // pesticide stores
        this.setPesticideStoresTileOnClickListener();
        // factors
        this.setFactorsTileOnClickListener();
        // insights
        this.setInsightsTileOnClickListener();
    }

    // [menu] virus check
    private void setVirusCheckTileOnClickListener() {
        this.virusCheckLinearLayout.setOnClickListener(llView -> {
            mainActivity.showVirusCheckFragment();
        });
    }

    // [menu] tomato detect
    private void setTomatoDetectTileOnClickListener() {
        this.tomatoDetectLinearLayout.setOnClickListener(llView -> {
            openCameraForTomatoDetect();
        });
    }

    private void openCameraForTomatoDetect() {
        Intent intent = new Intent(mainActivity, TomatoCameraActivity.class);
        // animation
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, this.tomatoDetectIconLinearLayout, ViewCompat.getTransitionName(this.tomatoDetectIconLinearLayout));
        startActivity(intent, options.toBundle());
    }

    // [menu] learn
    private void setLearnTileOnClickListener() {
        this.learnLinearLayout.setOnClickListener(llView -> {
            // remove all fragment in stack
            FragmentOperator.popAllFragmentsInStack(getChildFragmentManager());
            FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(requireActivity(), new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
        });
    }

    // control strategies
    private void setControlStrategiesOnClickListener() {
        this.controlStrategiesLinearLayout.setOnClickListener(llView -> {
            ControlStrategiesFragment controlStrategiesFragment = new ControlStrategiesFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                controlStrategiesFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(controlStrategiesLinearLayout, ViewCompat.getTransitionName(controlStrategiesLinearLayout))
                    .replace(R.id.fl_fragments, controlStrategiesFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // pesticide stores
    private void setPesticideStoresTileOnClickListener() {
        this.pesticideStoresLinearLayout.setOnClickListener(llView -> {
            PesticideStoreMapFragment pesticideStoreMapFragment = new PesticideStoreMapFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pesticideStoreMapFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(pesticideStoresLinearLayout, ViewCompat.getTransitionName(pesticideStoresLinearLayout))
                    .replace(R.id.fl_fragments, pesticideStoreMapFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // factors
    private void setFactorsTileOnClickListener() {
        this.factorsLinearLayout.setOnClickListener(llView -> {
            FactorsFragment factorsFragment = new FactorsFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                factorsFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(factorsLinearLayout, ViewCompat.getTransitionName(factorsLinearLayout))
                    .replace(R.id.fl_fragments, factorsFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new FactorsFragment(), AppResources.FRAGMENT_TAG_FACTORS);
        });
    }

    // insights
    private void setInsightsTileOnClickListener() {
        this.insightsLinearLayout.setOnClickListener(llView -> {
            InsightsFragment insightsFragment = new InsightsFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                insightsFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(insightsLinearLayout, ViewCompat.getTransitionName(insightsLinearLayout))
                    .replace(R.id.fl_fragments, insightsFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new InsightsFragment(), AppResources.FRAGMENT_TAG_INSIGHTS);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainActivity.setToolkitButton(false);
    }
}
