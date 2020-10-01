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
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class LearnFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout allViewsRelativeLayout;
    private RelativeLayout virusRelativeLayout;
    private RelativeLayout nutrientRelativeLayout;

    public LearnFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_learn, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_learn);
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
        // move Calculator And More To Right
        this.mainActivity.moveCalculatorAndMoreToRight(getTag(), 500);
        // set menu selected item
        if (this.mainActivity.getBottomNavigationViewEx().getCurrentItem() != 0) {
            this.mainActivity.getBottomNavigationViewEx().setCurrentItem(0);
        }

        // control all tiles on click listeners
        this.allTilesOnClickListener();
    }

    private void initializeViews() {
        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_learn);
        this.virusRelativeLayout = view.findViewById(R.id.rl_virus_learn);
        this.nutrientRelativeLayout = view.findViewById(R.id.rl_nutrient_learn);
    }

    // show All Views
    private void showAllViews() {
        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    private void allTilesOnClickListener() {
        // virus
        this.setVirusTileOnClickListener();
        // nutrient
        this.setNutrientTileOnClickListener();
    }

    // virus
    private void setVirusTileOnClickListener() {
        this.virusRelativeLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new VirusInfoListFragment(), AppResources.FRAGMENT_TAG_VIRUS_INFO);
        });
    }

    // nutrient
    private void setNutrientTileOnClickListener() {
        this.nutrientRelativeLayout.setOnClickListener(llView -> {
            FragmentOperator.replaceFragment(requireActivity(), new NutrientFragment(), AppResources.FRAGMENT_TAG_NUTRIENT);
        });
    }
}
