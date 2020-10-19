package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;

public class QuizResultFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    // views
    private MotionLayout containerMotionLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        // initialize quizActivity
        this.quizStartActivity = (QuizStartActivity)requireActivity();
        // initialize Views
        this.initializeViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // show titles
        this.showResultTitles(1000);
        // show overview
        this.showResultOverview(2800);
    }

    private void initializeViews(){
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_result);
    }

    private void showResultTitles(int duration) {
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_title, R.id.end_show_quiz_result_title, 300);
        }, duration);
    }

    private void showResultOverview(int duration) {
        new Handler().postDelayed(() -> {

            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_overview, R.id.end_show_quiz_result_overview, 300);
        }, duration);
    }
}
