package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;

public class QuizQuestionFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    // data
    private ChoiceQuestionModel currentChoiceQuestionModel;

    // views
    private MotionLayout containerMotionLayout;
    private RelativeLayout questionRelativeLayout;
    private GridLayout optionsGridLayout;
    private ProgressBar readQuestionProgressBar;
    private TextView questionNoTextView;
    private com.uncopt.android.widget.text.justify.JustifiedTextView questionTextView;
    private com.uncopt.android.widget.text.justify.JustifiedTextView questionForLayoutTextView;
    private CardView optionACardView;
    private CardView optionBCardView;
    private CardView optionCCardView;
    private CardView optionDCardView;
    private CardView optionECardView;
    private CardView optionFCardView;
    private ImageView optionAImageView;
    private ImageView optionBImageView;
    private ImageView optionCImageView;
    private ImageView optionDImageView;
    private ImageView optionEImageView;
    private ImageView optionFImageView;
    private TextView optionANoTextView;
    private TextView optionBNoTextView;
    private TextView optionCNoTextView;
    private TextView optionDNoTextView;
    private TextView optionENoTextView;
    private TextView optionFNoTextView;
    private TextView optionATextView;
    private TextView optionBTextView;
    private TextView optionCTextView;
    private TextView optionDTextView;
    private TextView optionETextView;
    private TextView optionFTextView;

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
        // initialize data
        this.initializeData();
        // initialize views
        this.initializeViews();

        // show question content
        showQuestionContent();

        return this.view;
    }

    private void initializeData() {
        this.currentChoiceQuestionModel = QuizActivity.choiceQuestionModelFinalList.get(this.questionNo - 1);
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_question_fragment);
        this.containerMotionLayout.setBackgroundResource(QuizStartActivity.backgroundResourceId);
        this.questionRelativeLayout = view.findViewById(R.id.rl_question_quiz_question_fragment);
        this.optionsGridLayout = view.findViewById(R.id.gl_options_quiz_question_fragment);
        this.readQuestionProgressBar = view.findViewById(R.id.pb_read_question_quiz_question_fragment);
        this.questionNoTextView = view.findViewById(R.id.tv_no_quiz_question_fragment);
        this.questionTextView = view.findViewById(R.id.tv_question_quiz_question_fragment);
        this.questionForLayoutTextView = view.findViewById(R.id.tv_question_for_layout_quiz_question_fragment);
        this.optionACardView = view.findViewById(R.id.cv_option_a_quiz_question_fragment);
        this.optionBCardView = view.findViewById(R.id.cv_option_b_quiz_question_fragment);
        this.optionCCardView = view.findViewById(R.id.cv_option_c_quiz_question_fragment);
        this.optionDCardView = view.findViewById(R.id.cv_option_d_quiz_question_fragment);
        this.optionECardView = view.findViewById(R.id.cv_option_e_quiz_question_fragment);
        this.optionFCardView = view.findViewById(R.id.cv_option_f_quiz_question_fragment);
        this.optionAImageView = view.findViewById(R.id.img_option_a_quiz_question_fragment);
        this.optionBImageView = view.findViewById(R.id.img_option_b_quiz_question_fragment);
        this.optionCImageView = view.findViewById(R.id.img_option_c_quiz_question_fragment);
        this.optionDImageView = view.findViewById(R.id.img_option_d_quiz_question_fragment);
        this.optionEImageView = view.findViewById(R.id.img_option_e_quiz_question_fragment);
        this.optionFImageView = view.findViewById(R.id.img_option_f_quiz_question_fragment);
        this.optionANoTextView = view.findViewById(R.id.tv_option_a_no_quiz_question_fragment);
        this.optionBNoTextView = view.findViewById(R.id.tv_option_b_no_quiz_question_fragment);
        this.optionCNoTextView = view.findViewById(R.id.tv_option_c_no_quiz_question_fragment);
        this.optionDNoTextView = view.findViewById(R.id.tv_option_d_no_quiz_question_fragment);
        this.optionENoTextView = view.findViewById(R.id.tv_option_e_no_quiz_question_fragment);
        this.optionFNoTextView = view.findViewById(R.id.tv_option_f_no_quiz_question_fragment);
        this.optionATextView = view.findViewById(R.id.tv_option_a_quiz_question_fragment);
        this.optionBTextView = view.findViewById(R.id.tv_option_b_quiz_question_fragment);
        this.optionCTextView = view.findViewById(R.id.tv_option_c_quiz_question_fragment);
        this.optionDTextView = view.findViewById(R.id.tv_option_d_quiz_question_fragment);
        this.optionETextView = view.findViewById(R.id.tv_option_e_quiz_question_fragment);
        this.optionFTextView = view.findViewById(R.id.tv_option_f_quiz_question_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showQuestionContent() {
        // show question
        this.showQuestion();
        // count down for reading question
        this.readQuestionCountDown(3);
        // show the question content
        new Handler().postDelayed(() -> {
            // show the options
            showOptions();
            // move the views
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

    private void showQuestion() {
        // show question no
        String questionNoString = "Question " + this.questionNo;
        this.questionNoTextView.setText(questionNoString);
        // show question title
        this.questionTextView.setText(QuizActivity.choiceQuestionModelFinalList.get(questionNo - 1).getChoiceQuestionContent());
        this.questionForLayoutTextView.setText(QuizActivity.choiceQuestionModelFinalList.get(questionNo - 1).getChoiceQuestionContent());
    }

    private void showOptions() {
        for (ChoiceOptionModel choiceOptionModel : currentChoiceQuestionModel.getChoiceQuestionOptionList()) {
            switch (choiceOptionModel.getChoiceOptionLabel()){
                case "A":
                    optionACardView.setVisibility(View.VISIBLE);
                    optionATextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                case "B":
                    optionBCardView.setVisibility(View.VISIBLE);
                    optionBTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                case "C":
                    optionCCardView.setVisibility(View.VISIBLE);
                    optionCTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                case "D":
                    optionDCardView.setVisibility(View.VISIBLE);
                    optionDTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                case "E":
                    optionECardView.setVisibility(View.VISIBLE);
                    optionETextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                case "F":
                    optionFCardView.setVisibility(View.VISIBLE);
                    optionFTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    break;
                default:
                    break;
            }
        }
    }

}
