package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

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

    // tools
    private int questionNo;
    private int questionTextInitialBottomPosition;

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

        return this.view;
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_question_fragment);
        this.containerMotionLayout.setBackgroundResource(QuizStartActivity.backgroundResourceId);
        this.questionRelativeLayout = view.findViewById(R.id.rl_question_quiz_question_fragment);
        this.optionsGridLayout = view.findViewById(R.id.gl_options_quiz_question_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();

        // show question content
        showQuestionContent();
    }

    private void showQuestionContent() {
        // set the option top margin
//        questionTextInitialBottomPosition = questionRelativeLayout.getBottom();
//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(containerMotionLayout);
//        constraintSet.setMargin(optionsGridLayout.getId(), ConstraintSet.TOP, questionTextInitialBottomPosition);
//        constraintSet.applyTo(containerMotionLayout);
        // show the question content
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_question_content, R.id.end_show_question_content, 300);
        }, 2000);

    }
}
