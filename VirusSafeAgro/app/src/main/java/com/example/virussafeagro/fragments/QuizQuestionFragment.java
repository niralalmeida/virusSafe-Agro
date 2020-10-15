package com.example.virussafeagro.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;

import java.util.ArrayList;
import java.util.List;

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
    private List<CardView> optionCardViewList;
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
    private AppCompatRadioButton optionAAppCompatRadioButton;
    private AppCompatRadioButton optionBAppCompatRadioButton;
    private AppCompatRadioButton optionCAppCompatRadioButton;
    private AppCompatRadioButton optionDAppCompatRadioButton;
    private AppCompatRadioButton optionEAppCompatRadioButton;
    private AppCompatRadioButton optionFAppCompatRadioButton;
    private List<AppCompatRadioButton> optionAppCompatRadioButtonList;

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
        this.currentChoiceQuestionModel = QuizStartActivity.choiceQuestionModelFinalList.get(this.questionNo - 1);
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
        this.optionCardViewList = new ArrayList<>();
        this.optionCardViewList.add(this.optionACardView);
        this.optionCardViewList.add(this.optionBCardView);
        this.optionCardViewList.add(this.optionCCardView);
        this.optionCardViewList.add(this.optionCCardView);
        this.optionCardViewList.add(this.optionDCardView);
        this.optionCardViewList.add(this.optionECardView);
        this.optionCardViewList.add(this.optionFCardView);
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
        this.optionAAppCompatRadioButton = view.findViewById(R.id.rbtn_option_a_quiz_question_fragment);
        this.optionBAppCompatRadioButton = view.findViewById(R.id.rbtn_option_b_quiz_question_fragment);
        this.optionCAppCompatRadioButton = view.findViewById(R.id.rbtn_option_c_quiz_question_fragment);
        this.optionDAppCompatRadioButton = view.findViewById(R.id.rbtn_option_d_quiz_question_fragment);
        this.optionEAppCompatRadioButton = view.findViewById(R.id.rbtn_option_e_quiz_question_fragment);
        this.optionFAppCompatRadioButton = view.findViewById(R.id.rbtn_option_f_quiz_question_fragment);
        this.optionAppCompatRadioButtonList = new ArrayList<>();
        this.optionAppCompatRadioButtonList.add(optionAAppCompatRadioButton);
        this.optionAppCompatRadioButtonList.add(optionBAppCompatRadioButton);
        this.optionAppCompatRadioButtonList.add(optionCAppCompatRadioButton);
        this.optionAppCompatRadioButtonList.add(optionDAppCompatRadioButton);
        this.optionAppCompatRadioButtonList.add(optionEAppCompatRadioButton);
        this.optionAppCompatRadioButtonList.add(optionFAppCompatRadioButton);
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

    // count down for reading question title
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

    // show question
    private void showQuestion() {
        // show question no
        String questionNoString = "Question " + this.questionNo;
        this.questionNoTextView.setText(questionNoString);
        // show question title
        this.questionTextView.setText(QuizStartActivity.choiceQuestionModelFinalList.get(questionNo - 1).getChoiceQuestionContent());
        this.questionForLayoutTextView.setText(QuizStartActivity.choiceQuestionModelFinalList.get(questionNo - 1).getChoiceQuestionContent());
    }

    // show options
    private void showOptions() {
        // set option card background
        if (QuizActivity.currentPageName.equals(QuizActivity.BUTTON_NAME_BEGINNER)){
            optionACardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionBCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionCCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionDCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionECardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionFCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_beginner_bg));
            optionANoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionBNoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionCNoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionDNoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionENoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionFNoTextView.setBackgroundResource(R.drawable.shape_beginner_option_no_quiz_question_fragment);
            optionAAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            optionBAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            optionCAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            optionDAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            optionEAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            optionFAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
        } else if (QuizActivity.currentPageName.equals(QuizActivity.BUTTON_NAME_INTERMEDIATE)){
            optionACardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionBCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionCCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionDCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionECardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionFCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_intermediate_bg));
            optionANoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionBNoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionCNoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionDNoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionENoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionFNoTextView.setBackgroundResource(R.drawable.shape_intermediate_option_no_quiz_question_fragment);
            optionAAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            optionBAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            optionCAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            optionDAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            optionEAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            optionFAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
        }

        for (ChoiceOptionModel choiceOptionModel : currentChoiceQuestionModel.getChoiceQuestionOptionList()) {
            switch (choiceOptionModel.getChoiceOptionLabel()){
                case "A":
                    optionACardView.setVisibility(View.VISIBLE);
                    optionATextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionACardView, optionAAppCompatRadioButton);
                    break;
                case "B":
                    optionBCardView.setVisibility(View.VISIBLE);
                    optionBTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionBCardView, optionBAppCompatRadioButton);
                    break;
                case "C":
                    optionCCardView.setVisibility(View.VISIBLE);
                    optionCTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionCCardView, optionCAppCompatRadioButton);
                    break;
                case "D":
                    optionDCardView.setVisibility(View.VISIBLE);
                    optionDTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionDCardView, optionDAppCompatRadioButton);
                    break;
                case "E":
                    optionECardView.setVisibility(View.VISIBLE);
                    optionETextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionECardView, optionEAppCompatRadioButton);
                    break;
                case "F":
                    optionFCardView.setVisibility(View.VISIBLE);
                    optionFTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    setCardOnClickListenerForSingle(optionFCardView, optionFAppCompatRadioButton);
                    break;
                default:
                    break;
            }
        }
    }

    private void setCardOnClickListenerForSingle(CardView currentCardView, AppCompatRadioButton currentAppCompatRadioButton) {
        // click card
        currentCardView.setOnClickListener(v -> {
            int optionIndex = -1;
            // set card view style
            for (CardView cardView : optionCardViewList){
                if (!cardView.equals(currentCardView)){
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
                    cardView.setClickable(false);
                } else {
                    optionIndex = optionCardViewList.indexOf(cardView);
                }
            }
            // set radio button style
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (optionIndex != optionAppCompatRadioButtonList.indexOf(appCompatRadioButton)) {
                    appCompatRadioButton.setClickable(false);
                } else {
                    appCompatRadioButton.setChecked(true);
                }
            }
        });

        // click radio button
        currentAppCompatRadioButton.setOnClickListener(v -> {
            int optionIndex = -1;
            // get selected option index
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (appCompatRadioButton.equals(currentAppCompatRadioButton)){
                    optionIndex = optionAppCompatRadioButtonList.indexOf(appCompatRadioButton);
                }
            }
            for (CardView cardView : optionCardViewList){
                if (optionIndex != optionCardViewList.indexOf(cardView)){
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
                    cardView.setClickable(false);
                }
            }
        });
    }

}
