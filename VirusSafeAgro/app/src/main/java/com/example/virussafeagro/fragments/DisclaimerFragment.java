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
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class DisclaimerFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    private LinearLayout allViewLinearLayout;

    public DisclaimerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_disclaimer, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_disclaimer);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set more button
        this.mainActivity.setMoreButton(true);

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initializeViews() {
        this.allViewLinearLayout = view.findViewById(R.id.ll_all_view_disclaimer);
    }

    @Override
    public void onPause() {
        super.onPause();

        // set more button
        mainActivity.setMoreButton(false);
    }

}
