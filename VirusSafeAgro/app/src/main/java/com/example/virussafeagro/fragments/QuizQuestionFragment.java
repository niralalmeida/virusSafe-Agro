package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;

public class QuizQuestionFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    private MotionLayout containerMotionLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        // initialize quizActivity
        this.quizStartActivity = (QuizStartActivity)requireActivity();
        // initialize views
        this.initializeViews();

        return this.view;
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_question_fragment);
        this.containerMotionLayout.setBackgroundResource(QuizStartActivity.backgroundResourceId);
    }
}
