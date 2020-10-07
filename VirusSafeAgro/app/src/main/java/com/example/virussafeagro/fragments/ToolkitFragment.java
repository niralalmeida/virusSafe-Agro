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
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ToolkitFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    // top
    private LinearLayout virusCheckLinearLayout;
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

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show Home Views
//        this.showHomeViews();
        // move Calculator And More To Right
        this.mainActivity.moveTipAndMoreToRight(getTag(), 500);
        // set menu selected item
        if (this.mainActivity.getBottomNavigationViewEx().getCurrentItem() != 2) {
            this.mainActivity.getBottomNavigationViewEx().setCurrentItem(2);
        }

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_toolkit);
        this.virusCheckLinearLayout = view.findViewById(R.id.ll_virus_check_toolkit);
        this.learnLinearLayout = view.findViewById(R.id.ll_learn_toolkit);
        this.controlStrategiesLinearLayout = view.findViewById(R.id.ll_control_strategies_toolkit);
        this.pesticideStoresLinearLayout = view.findViewById(R.id.ll_pesticide_store_toolkit);
        this.factorsLinearLayout = view.findViewById(R.id.ll_factors_toolkit);
        this.insightsLinearLayout = view.findViewById(R.id.ll_insights_toolkit);
    }

    // show Home Views
    private void showHomeViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    private void allTilesOnClickListener() {

        // virus check
        this.setVirusCheckTileOnClickListener();
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
            changeTheIconInMenu(AppResources.FRAGMENT_TAG_VIRUS_CHECK);
            FragmentOperator.replaceFragment(requireActivity(), new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
        });
    }

    // [menu] learn
    private void setLearnTileOnClickListener() {
        this.learnLinearLayout.setOnClickListener(llView -> {
            changeTheIconInMenu(AppResources.FRAGMENT_TAG_LEARN);
            FragmentOperator.replaceFragment(requireActivity(), new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
        });
    }

    private void changeTheIconInMenu(String fragmentTag) {
        BottomNavigationViewEx bottomNavigationViewEx = ((MainActivity) requireActivity()).getBottomNavigationViewEx();
        switch (fragmentTag) {
            case AppResources.FRAGMENT_TAG_VIRUS_CHECK:
                bottomNavigationViewEx.setCurrentItem(2);
                break;
            case AppResources.FRAGMENT_TAG_LEARN:
                bottomNavigationViewEx.setCurrentItem(1);
                break;
            case AppResources.FRAGMENT_TAG_WATER_CALCULATOR:
                bottomNavigationViewEx.setCurrentItem(3);
                break;
        }
    }

    // control strategies
    private void setControlStrategiesOnClickListener() {
        this.controlStrategiesLinearLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new ControlStrategiesFragment(), AppResources.FRAGMENT_TAG_CONTROL_STRATEGIES);
        });
    }

    // pesticide stores
    private void setPesticideStoresTileOnClickListener() {
        this.pesticideStoresLinearLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new PesticideStoreMapFragment(), AppResources.FRAGMENT_TAG_PESTICIDE_STORES);
        });
    }

    // factors
    private void setFactorsTileOnClickListener() {
        this.factorsLinearLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new FactorsFragment(), AppResources.FRAGMENT_TAG_FACTORS);
        });
    }

    // insights
    private void setInsightsTileOnClickListener() {
        this.insightsLinearLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new InsightsFragment(), AppResources.FRAGMENT_TAG_INSIGHTS);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        this.allViewsRelativeLayout.setVisibility(View.GONE);
    }
}
