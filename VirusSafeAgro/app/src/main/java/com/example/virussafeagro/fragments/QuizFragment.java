package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.R;

public class QuizFragment extends Fragment {
    private QuizActivity quizActivity;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // initialize quizActivity
        this.quizActivity = (QuizActivity)requireActivity();

        return this.view;
    }
}
