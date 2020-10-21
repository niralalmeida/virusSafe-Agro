package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.uitilities.AppResources;

public class UpdatesFragment extends Fragment {
    private MainActivity mainActivity;
    private View view;

    public UpdatesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_updates, container, false);

        // get main activity
        this.mainActivity = (MainActivity)getActivity();
        // set title
        this.mainActivity.getTitleTextView().setText(R.string.fragment_updates);
        // show back button
        MainActivity.showTopBarBackButton((MainActivity)requireActivity());
        // set more button
        this.mainActivity.setMoreButton(true);
        // set tip
        this.mainActivity.showTipByPage(AppResources.FRAGMENT_TAG_MORE);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // set more button
        mainActivity.setMoreButton(false);
    }

}
