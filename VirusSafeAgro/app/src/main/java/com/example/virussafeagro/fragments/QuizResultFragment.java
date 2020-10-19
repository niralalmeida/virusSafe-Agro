package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private TextView rightCountTextView;
    private TextView wrongCountTextView;
    private TextView timeOutCountTextView;

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
        // show Result Count
        this.showResultCount();
    }

    private void initializeViews(){
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_result);
        this.rightCountTextView = view.findViewById(R.id.tv_right_count_quiz_result);
        this.wrongCountTextView = view.findViewById(R.id.tv_wrong_count_quiz_result);
        this.timeOutCountTextView = view.findViewById(R.id.tv_time_out_count_quiz_result);
    }

    // show titles
    private void showResultTitles(int duration) {
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_title, R.id.end_show_quiz_result_title, 300);
        }, duration);
    }

    // show overview
    private void showResultOverview(int duration) {
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(this.containerMotionLayout, R.id.start_show_quiz_result_overview, R.id.end_show_quiz_result_overview, 300);
        }, duration);
    }

    // show Result Count
    private void showResultCount() {
        String rightCountString = "" + quizStartActivity.rightCount;
        this.rightCountTextView.setText(rightCountString);
        String wrongCountString = "" + quizStartActivity.wrongCount;
        this.wrongCountTextView.setText(wrongCountString);
        String timeOutCountString = "" + quizStartActivity.timeOutCount;
        this.timeOutCountTextView.setText(timeOutCountString);
    }
}
