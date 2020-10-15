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
import androidx.appcompat.widget.AppCompatCheckBox;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AppCompatCheckBox optionAAppCompatCheckBox;
    private AppCompatCheckBox optionBAppCompatCheckBox;
    private AppCompatCheckBox optionCAppCompatCheckBox;
    private AppCompatCheckBox optionDAppCompatCheckBox;
    private AppCompatCheckBox optionEAppCompatCheckBox;
    private AppCompatCheckBox optionFAppCompatCheckBox;
    private List<AppCompatCheckBox> optionAppCompatCheckBoxList;
    private View optionACheckedBorderView;
    private View optionBCheckedBorderView;
    private View optionCCheckedBorderView;
    private View optionDCheckedBorderView;
    private View optionECheckedBorderView;
    private View optionFCheckedBorderView;
    private Map<String, CardView> optionLabelCardViewBoxMap;
    private Map<String, AppCompatRadioButton> optionLabelAppCompatRadioButtonMap;
    private Map<String, AppCompatCheckBox> optionLabelAppCompatCheckBoxMap;


    // tools
    private int questionNo;
    private int questionTypeNo; // 1 = single, 2 = multiple

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
        this.questionTypeNo = this.currentChoiceQuestionModel.getChoiceQuestionType().equals("single") ? 1 : 2;
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
        this.optionAAppCompatCheckBox = view.findViewById(R.id.cb_option_a_quiz_question_fragment);
        this.optionBAppCompatCheckBox = view.findViewById(R.id.cb_option_b_quiz_question_fragment);
        this.optionCAppCompatCheckBox = view.findViewById(R.id.cb_option_c_quiz_question_fragment);
        this.optionDAppCompatCheckBox = view.findViewById(R.id.cb_option_d_quiz_question_fragment);
        this.optionEAppCompatCheckBox = view.findViewById(R.id.cb_option_e_quiz_question_fragment);
        this.optionFAppCompatCheckBox = view.findViewById(R.id.cb_option_f_quiz_question_fragment);
        this.optionAppCompatCheckBoxList = new ArrayList<>();
        this.optionAppCompatCheckBoxList.add(optionAAppCompatCheckBox);
        this.optionAppCompatCheckBoxList.add(optionBAppCompatCheckBox);
        this.optionAppCompatCheckBoxList.add(optionCAppCompatCheckBox);
        this.optionAppCompatCheckBoxList.add(optionDAppCompatCheckBox);
        this.optionAppCompatCheckBoxList.add(optionEAppCompatCheckBox);
        this.optionAppCompatCheckBoxList.add(optionFAppCompatCheckBox);
        this.optionACheckedBorderView = view.findViewById(R.id.v_option_a_checked_border_quiz_question_fragment);
        this.optionBCheckedBorderView = view.findViewById(R.id.v_option_b_checked_border_quiz_question_fragment);
        this.optionCCheckedBorderView = view.findViewById(R.id.v_option_c_checked_border_quiz_question_fragment);
        this.optionDCheckedBorderView = view.findViewById(R.id.v_option_d_checked_border_quiz_question_fragment);
        this.optionECheckedBorderView = view.findViewById(R.id.v_option_e_checked_border_quiz_question_fragment);
        this.optionFCheckedBorderView = view.findViewById(R.id.v_option_f_checked_border_quiz_question_fragment);
        this.optionLabelCardViewBoxMap = new HashMap<>();
        this.optionLabelCardViewBoxMap.put("A", optionACardView);
        this.optionLabelCardViewBoxMap.put("B", optionBCardView);
        this.optionLabelCardViewBoxMap.put("C", optionCCardView);
        this.optionLabelCardViewBoxMap.put("D", optionDCardView);
        this.optionLabelCardViewBoxMap.put("E", optionECardView);
        this.optionLabelCardViewBoxMap.put("F", optionFCardView);
        this.optionLabelAppCompatRadioButtonMap = new HashMap<>();
        this.optionLabelAppCompatRadioButtonMap.put("A", optionAAppCompatRadioButton);
        this.optionLabelAppCompatRadioButtonMap.put("B", optionBAppCompatRadioButton);
        this.optionLabelAppCompatRadioButtonMap.put("C", optionCAppCompatRadioButton);
        this.optionLabelAppCompatRadioButtonMap.put("D", optionDAppCompatRadioButton);
        this.optionLabelAppCompatRadioButtonMap.put("E", optionEAppCompatRadioButton);
        this.optionLabelAppCompatRadioButtonMap.put("F", optionFAppCompatRadioButton);
        this.optionLabelAppCompatCheckBoxMap = new HashMap<>();
        this.optionLabelAppCompatCheckBoxMap.put("A", optionAAppCompatCheckBox);
        this.optionLabelAppCompatCheckBoxMap.put("B", optionBAppCompatCheckBox);
        this.optionLabelAppCompatCheckBoxMap.put("C", optionCAppCompatCheckBox);
        this.optionLabelAppCompatCheckBoxMap.put("D", optionDAppCompatCheckBox);
        this.optionLabelAppCompatCheckBoxMap.put("E", optionEAppCompatCheckBox);
        this.optionLabelAppCompatCheckBoxMap.put("F", optionFAppCompatCheckBox);
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
        if (questionTypeNo == 1){
            // single
            optionAAppCompatRadioButton.setVisibility(View.VISIBLE);
            optionBAppCompatRadioButton.setVisibility(View.VISIBLE);
            optionCAppCompatRadioButton.setVisibility(View.VISIBLE);
            optionDAppCompatRadioButton.setVisibility(View.VISIBLE);
            optionEAppCompatRadioButton.setVisibility(View.VISIBLE);
            optionFAppCompatRadioButton.setVisibility(View.VISIBLE);
        } else if (questionTypeNo == 2){
            // multiple
            optionAAppCompatCheckBox.setVisibility(View.VISIBLE);
            optionBAppCompatCheckBox.setVisibility(View.VISIBLE);
            optionCAppCompatCheckBox.setVisibility(View.VISIBLE);
            optionDAppCompatCheckBox.setVisibility(View.VISIBLE);
            optionEAppCompatCheckBox.setVisibility(View.VISIBLE);
            optionFAppCompatCheckBox.setVisibility(View.VISIBLE);
        }
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
            if (questionTypeNo == 1) {
                // single
                optionAAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
                optionBAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
                optionCAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
                optionDAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
                optionEAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
                optionFAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_beginner_option_quiz_question_fragment);
            } else if (questionTypeNo == 2) {
                // multiple
                optionAAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
                optionBAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
                optionCAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
                optionDAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
                optionEAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
                optionFAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_beginner_option_quiz_question_fragment);
            }
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
            if (questionTypeNo == 1) {
                // single
                optionAAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
                optionBAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
                optionCAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
                optionDAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
                optionEAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
                optionFAppCompatRadioButton.setBackgroundResource(R.drawable.selector_radio_button_intermediate_option_quiz_question_fragment);
            } else if (questionTypeNo == 2) {
                // multiple
                optionAAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
                optionBAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
                optionCAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
                optionDAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
                optionEAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
                optionFAppCompatCheckBox.setBackgroundResource(R.drawable.selector_checkbox_intermediate_option_quiz_question_fragment);
            }
        }

        for (ChoiceOptionModel choiceOptionModel : currentChoiceQuestionModel.getChoiceQuestionOptionList()) {
            switch (choiceOptionModel.getChoiceOptionLabel()){
                case "A":
                    optionACardView.setVisibility(View.VISIBLE);
                    optionATextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionACardView, optionAAppCompatRadioButton, optionACheckedBorderView, "A");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionACardView, optionAAppCompatCheckBox, optionACheckedBorderView, "A");
                    }
                    break;
                case "B":
                    optionBCardView.setVisibility(View.VISIBLE);
                    optionBTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionBCardView, optionBAppCompatRadioButton, optionBCheckedBorderView, "B");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionBCardView, optionBAppCompatCheckBox, optionBCheckedBorderView, "B");
                    }
                    break;
                case "C":
                    optionCCardView.setVisibility(View.VISIBLE);
                    optionCTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionCCardView, optionCAppCompatRadioButton, optionCCheckedBorderView, "C");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionCCardView, optionCAppCompatCheckBox, optionCCheckedBorderView, "C");
                    }
                    break;
                case "D":
                    optionDCardView.setVisibility(View.VISIBLE);
                    optionDTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionDCardView, optionDAppCompatRadioButton, optionDCheckedBorderView, "D");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionDCardView, optionDAppCompatCheckBox, optionDCheckedBorderView, "D");
                    }
                    break;
                case "E":
                    optionECardView.setVisibility(View.VISIBLE);
                    optionETextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionECardView, optionEAppCompatRadioButton, optionECheckedBorderView, "E");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionECardView, optionEAppCompatCheckBox, optionECheckedBorderView, "E");
                    }
                    break;
                case "F":
                    optionFCardView.setVisibility(View.VISIBLE);
                    optionFTextView.setText(choiceOptionModel.getChoiceOptionContent());
                    if (questionTypeNo == 1) {
                        setCardOnClickListenerForSingle(optionFCardView, optionFAppCompatRadioButton, optionFCheckedBorderView, "F");
                    } else if (questionTypeNo == 2){
                        setCardOnClickListenerForMultiple(optionFCardView, optionFAppCompatCheckBox, optionFCheckedBorderView, "F");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setCardOnClickListenerForSingle(CardView currentCardView,
                                                 AppCompatRadioButton currentAppCompatRadioButton,
                                                 View currentCheckedBorderView,
                                                 String currentOptionNoString
                                                ) {
        // click card
        currentCardView.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility(View.VISIBLE);
            // set card view style
            for (CardView cardView : optionCardViewList){
                if (!cardView.equals(currentCardView)) {
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
                    cardView.setClickable(false);
                }
            }
            // set radio button style
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (!optionLabelAppCompatRadioButtonMap.get(currentOptionNoString).equals(appCompatRadioButton)) {
                    appCompatRadioButton.setClickable(false);
                } else {
                    appCompatRadioButton.setChecked(true);
                }
            }
        });

        // click radio button
        currentAppCompatRadioButton.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility(View.VISIBLE);
            // get selected option index
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (!appCompatRadioButton.equals(currentAppCompatRadioButton)){
                    appCompatRadioButton.setClickable(false);
                }
            }
            for (CardView cardView : optionCardViewList){
                if (!optionLabelCardViewBoxMap.get(currentOptionNoString).equals(cardView)){
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
                    cardView.setClickable(false);
                }
            }
        });
    }

    private void setCardOnClickListenerForMultiple(CardView currentCardView,
                                                   AppCompatCheckBox currentAppCompatCheckBox,
                                                   View currentCheckedBorderView,
                                                   String currentOptionNoString
                                                  ) {
        // get current option label

        // click card
        currentCardView.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility((currentCheckedBorderView.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
            for (AppCompatCheckBox appCompatCheckBox : optionAppCompatCheckBoxList) {
                if (optionLabelAppCompatCheckBoxMap.get(currentOptionNoString).equals(appCompatCheckBox)){
                    appCompatCheckBox.setChecked(!appCompatCheckBox.isChecked());
                }
            }
        });

        // click checkbox
        currentAppCompatCheckBox.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility((currentCheckedBorderView.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
        });
    }
}
