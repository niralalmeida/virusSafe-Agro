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

import java.util.Objects;

public class CalculatorFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private RelativeLayout allViewsRelativeLayout;

    public CalculatorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_calculator, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_calculator);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set calculator button
        this.mainActivity.setCalculatorButton(true);

        // initialize Views
        this.initializeViews();

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show all views
//        this.showAllViews();
    }

    private void initializeViews() {
//        this.allViewsRelativeLayout = view.findViewById(R.id.rl_all_views_calculator);
    }

    // show All Views
    private void showAllViews() {
//        MyAnimationBox.runFadeInAnimation(this.allViewsRelativeLayout, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();

        mainActivity.setCalculatorButton(false);
    }
}

