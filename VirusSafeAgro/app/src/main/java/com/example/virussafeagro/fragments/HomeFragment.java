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
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private ImageView swipeImageView;
    private DragYRelativeLayout homeImageDragYRelativeLayout;
    // top
    private LinearLayout virusCheckLinearLayout;
    private LinearLayout learnLinearLayout;
    private LinearLayout waterCalculatorLinearLayout;
    // middle
    private LinearLayout controlStrategiesLinearLayout;
    private LinearLayout pesticideStoresLinearLayout;
    private LinearLayout factorsLinearLayout;
    private LinearLayout insightsLinearLayout;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText("virusSafe Agro");
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());

        // initialize Views
        this.initializeViews();

        // get the bundle for checking where from
        Bundle bundle = getArguments();
        if (bundle != null){
            String fromString = bundle.getString("from", "back");
            if (fromString.equals("back")){
                this.homeImageDragYRelativeLayout.setVisibility(View.GONE);
            }
        }

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
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_home);
        this.swipeImageView = view.findViewById(R.id.img_swipe_home);
        this.homeImageDragYRelativeLayout = view.findViewById(R.id.drl_image_home);
        this.virusCheckLinearLayout = view.findViewById(R.id.ll_virus_check_home);
        this.learnLinearLayout = view.findViewById(R.id.ll_learn_home);
        this.waterCalculatorLinearLayout = view.findViewById(R.id.ll_water_calculator_home);
        this.controlStrategiesLinearLayout = view.findViewById(R.id.ll_control_strategies_home);
        this.pesticideStoresLinearLayout = view.findViewById(R.id.ll_pesticide_store_home);
        this.factorsLinearLayout = view.findViewById(R.id.ll_factors_home);
        this.insightsLinearLayout = view.findViewById(R.id.ll_insights_home);
    }

    public DragYRelativeLayout getHomeImageDragYRelativeLayout() {
        return homeImageDragYRelativeLayout;
    }

    // show Home Views
    private void showHomeViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
        if (homeImageDragYRelativeLayout.getVisibility() == View.VISIBLE) {
            new Handler().postDelayed(() -> {
                MyAnimationBox.runRepeatedAnimationBottomToTop(swipeImageView, 1000);
            }, 1000);
        }
    }

    private void allTilesOnClickListener() {

        // virus check
        this.setVirusCheckTileOnClickListener();
        // virus info
        this.setLearnTileOnClickListener();
        // water calculator
        this.setWaterCalculatorOnClickListener();

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

    // [menu] water calculator
    private void setWaterCalculatorOnClickListener() {
        this.waterCalculatorLinearLayout.setOnClickListener(llView -> {
            changeTheIconInMenu(AppResources.FRAGMENT_TAG_WATER_CALCULATOR);
            FragmentOperator.replaceFragment(requireActivity(), new CalculatorFragment(), AppResources.FRAGMENT_TAG_WATER_CALCULATOR);
        });
    }

    private void changeTheIconInMenu(String fragmentTag) {
        BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).getBottomNavigationView();
        switch (fragmentTag) {
            case AppResources.FRAGMENT_TAG_VIRUS_CHECK:
                bottomNavigationView.setSelectedItemId(R.id.ic_virus_check);
                break;
            case AppResources.FRAGMENT_TAG_LEARN:
                bottomNavigationView.setSelectedItemId(R.id.ic_learn);
                break;
            case AppResources.FRAGMENT_TAG_WATER_CALCULATOR:
                bottomNavigationView.setSelectedItemId(R.id.ic_calculator);
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
//            FragmentOperator.replaceFragment(requireActivity(), new InsightsFragment(), AppResources.FRAGMENT_TAG_INSIGHTS);
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
        this.allViewsRelativeLayout.setVisibility(View.GONE);

        // hide the image when back button is clicked
        Bundle bundle = new Bundle();
        bundle.putString("from", "back");
        setArguments(bundle);
    }
}
