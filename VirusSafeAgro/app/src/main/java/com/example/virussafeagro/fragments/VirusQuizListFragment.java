package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.R;

/**
 * Fragment with virus list for quiz
 * @author Haoyu Yang
 */
public class VirusQuizListFragment extends Fragment {
    private View view;

    public VirusQuizListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_virus_quiz_list, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}

