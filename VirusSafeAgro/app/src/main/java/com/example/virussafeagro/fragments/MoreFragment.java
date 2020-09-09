package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;

public class MoreFragment extends Fragment {
    private View view;

    private LinearLayout aboutLinearLayout;

    public MoreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_about, container, false);

        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initializeViews() {
        this.aboutLinearLayout = view.findViewById(R.id.ll_about_more);
    }

    private void setAboutLinearLayoutOnClickListener() {
        this.aboutLinearLayout.setOnClickListener(view -> {

        });
    }

}
