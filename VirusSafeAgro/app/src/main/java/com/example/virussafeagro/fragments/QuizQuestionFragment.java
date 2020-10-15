package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;

public class QuizQuestionFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    // views
    private MotionLayout containerMotionLayout;
    private RelativeLayout questionRelativeLayout;
    private GridLayout optionsGridLayout;
    private ProgressBar readQuestionProgressBar;
    private TextView questionNoTextView;

    // tools
    private int questionNo;

    public QuizQuestionFragment(int questionNo) {
        this.questionNo = questionNo;
    }

    public QuizQuestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        // initialize quizActivity
        this.quizStartActivity = (QuizStartActivity)requireActivity();
        // initialize views
        this.initializeViews();

        // show question content
        showQuestionContent();

        return this.view;
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_question_fragment);
        this.containerMotionLayout.setBackgroundResource(QuizStartActivity.backgroundResourceId);
        this.questionRelativeLayout = view.findViewById(R.id.rl_question_quiz_question_fragment);
        this.optionsGridLayout = view.findViewById(R.id.gl_options_quiz_question_fragment);
        this.readQuestionProgressBar = view.findViewById(R.id.pb_read_question_quiz_question_fragment);
        this.questionNoTextView = view.findViewById(R.id.tv_no_quiz_question_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showQuestionContent() {
        // show question no
        String questionNoString = "Question " + this.questionNo;
        this.questionNoTextView.setText(questionNoString);
        // count down for reading question
        this.readQuestionCountDown(3);
        // show the question content
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_question_content, R.id.end_show_question_content, 300);
        }, 3000);

    }

    private void readQuestionCountDown(int timeForCountDown) {
        CountDownTimer mCountDownTimer;
        readQuestionProgressBar.setProgress(100);
        mCountDownTimer = new CountDownTimer(timeForCountDown * 1000,10) {

            @Override
            public void onTick(long millisUntilFinished) {
                readQuestionProgressBar.setProgress((int) millisUntilFinished * 100 / (timeForCountDown * 1000));
            }

            @Override
            public void onFinish() {
                readQuestionProgressBar.setProgress(0);
                readQuestionProgressBar.setVisibility(View.GONE);
            }
        };
        mCountDownTimer.start();
    }

}
