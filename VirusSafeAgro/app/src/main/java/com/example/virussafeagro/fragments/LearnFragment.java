package com.example.virussafeagro.fragments;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;

public class LearnFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout virusRelativeLayout;
    private RelativeLayout nutrientRelativeLayout;
    private RelativeLayout tomatoTipRelativeLayout;

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
        // pop all fragment in the stack
        FragmentOperator.popAllFragmentsInStack(getChildFragmentManager());
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_LEARN);

        // initialize Views
        this.initializeViews();

        // set menu selected item
        if (!this.mainActivity.isLearnIconClicked()) {
            this.mainActivity.setLearnButton(true);
        }

        // move Calculator And More To Right
        this.mainActivity.moveTipAndMoreToRight(getTag(), 200);

        // control all tiles on click listeners
        this.allTilesOnClickListener();

        return this.view;
    }

    private void initializeViews() {
        this.virusRelativeLayout = view.findViewById(R.id.rl_virus_learn);
        this.nutrientRelativeLayout = view.findViewById(R.id.rl_nutrient_learn);
        this.tomatoTipRelativeLayout = view.findViewById(R.id.rl_tomato_growth_tip_learn);
    }

    private void allTilesOnClickListener() {
        // virus
        this.setVirusTileOnClickListener();
        // nutrient
        this.setNutrientTileOnClickListener();
        // tomato tip
        this.setTomatoTipTileOnClickListener();
    }

    // virus
    private void setVirusTileOnClickListener() {
        this.virusRelativeLayout.setOnClickListener(llView -> {
            VirusInfoListFragment virusInfoListFragment = new VirusInfoListFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                virusInfoListFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(virusRelativeLayout, ViewCompat.getTransitionName(virusRelativeLayout))
                    .replace(R.id.fl_fragments, virusInfoListFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new VirusInfoListFragment(), AppResources.FRAGMENT_TAG_VIRUS_INFO);
        });
    }

    // nutrient
    private void setNutrientTileOnClickListener() {
        this.nutrientRelativeLayout.setOnClickListener(llView -> {
            NutrientFragment nutrientFragment = new NutrientFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                nutrientFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(nutrientRelativeLayout, ViewCompat.getTransitionName(nutrientRelativeLayout))
                    .replace(R.id.fl_fragments, nutrientFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new NutrientFragment(), AppResources.FRAGMENT_TAG_NUTRIENT);
        });
    }

    // tomato tip
    private void setTomatoTipTileOnClickListener() {
        this.tomatoTipRelativeLayout.setOnClickListener(llView -> {
            TomatoTipFragment tomatoTipFragment = new TomatoTipFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tomatoTipFragment.setSharedElementEnterTransition(
                        TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            }
            mainActivity.getSupportFragmentManager().beginTransaction()
                    .addSharedElement(tomatoTipRelativeLayout, ViewCompat.getTransitionName(tomatoTipRelativeLayout))
                    .replace(R.id.fl_fragments, tomatoTipFragment)
                    .addToBackStack(null)
                    .commit();
//            FragmentOperator.replaceFragmentWithSlideFromRightAnimation(requireActivity(), new NutrientFragment(), AppResources.FRAGMENT_TAG_NUTRIENT);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainActivity.setLearnButton(false);
    }
}
