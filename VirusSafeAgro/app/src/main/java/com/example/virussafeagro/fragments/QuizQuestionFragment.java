package com.example.virussafeagro.fragments;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.virussafeagro.QuizActivity;
import com.example.virussafeagro.QuizStartActivity;
import com.example.virussafeagro.R;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.models.ChoiceOptionModel;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.uitilities.DataComparison;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizQuestionFragment extends Fragment {
    private QuizStartActivity quizStartActivity;
    private View view;

    // data
    private ChoiceQuestionModel currentChoiceQuestionModel;
    private List<String> userAnswerList;

    // views
    private MotionLayout containerMotionLayout;
    private ConstraintLayout questionConstraintLayout;
    private GridLayout optionsGridLayout;
    private ProgressBar readQuestionProgressBar;
    private ProgressBar doQuestionProgressBar;
    private TextView doQuestionTextView;
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
    private List<TextView> optionNoTextViewList;
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
    private Map<AppCompatCheckBox, String> appCompatCheckBoxOptionLabelButtonMap;
    private Button submitButton;
    private ImageView optionARightWrongIconImageView;
    private ImageView optionBRightWrongIconImageView;
    private ImageView optionCRightWrongIconImageView;
    private ImageView optionDRightWrongIconImageView;
    private ImageView optionERightWrongIconImageView;
    private ImageView optionFRightWrongIconImageView;
    private Map<String, ImageView> optionLabelRightWrongIconMap;
    private Map<String, View> optionLabelCheckedBorderMap;
    private Map<String, TextView> optionLabelOptionNoMap;
    private DragYRelativeLayout questionExplanationDragYRelativeLayout;
    private TextView resultTitleTextView;
    private TextView questionExplanationTextView;
    private Map<CardView, String> optionCardViewLabelMap;

    // tools
    private int questionNo;
    private boolean isOptionsShown;
    private int questionTypeNo; // 1 = single, 2 = multiple
    private boolean isAnswered;
    private boolean isAnswerRight;

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

        // set SubmitButton On Click Listener
        this.setSubmitButtonOnClickListener();

        return this.view;
    }

    private void initializeData() {
        this.currentChoiceQuestionModel = QuizStartActivity.choiceQuestionModelFinalList.get(this.questionNo - 1);
        this.questionTypeNo = this.currentChoiceQuestionModel.getChoiceQuestionType().equals("single") ? 1 : 2;
    }

    private void initializeViews() {
        this.containerMotionLayout = view.findViewById(R.id.ml_container_quiz_question_fragment);
        this.questionConstraintLayout = view.findViewById(R.id.cl_question_quiz_question_fragment);
        this.optionsGridLayout = view.findViewById(R.id.gl_options_quiz_question_fragment);
        this.readQuestionProgressBar = view.findViewById(R.id.pb_read_question_quiz_question_fragment);
        this.doQuestionProgressBar = view.findViewById(R.id.pb_do_question_quiz_question_fragment);
        this.doQuestionTextView = view.findViewById(R.id.tv_do_question_quiz_question_fragment);
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
        this.optionCardViewLabelMap = new HashMap<>();
        this.optionCardViewLabelMap.put(optionACardView, "A");
        this.optionCardViewLabelMap.put(optionBCardView, "B");
        this.optionCardViewLabelMap.put(optionCCardView, "C");
        this.optionCardViewLabelMap.put(optionDCardView, "D");
        this.optionCardViewLabelMap.put(optionECardView, "E");
        this.optionCardViewLabelMap.put(optionFCardView, "F");
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
        this.optionNoTextViewList = new ArrayList<>();
        this.optionNoTextViewList.add(optionANoTextView);
        this.optionNoTextViewList.add(optionBNoTextView);
        this.optionNoTextViewList.add(optionCNoTextView);
        this.optionNoTextViewList.add(optionDNoTextView);
        this.optionNoTextViewList.add(optionENoTextView);
        this.optionNoTextViewList.add(optionFNoTextView);
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
        this.appCompatCheckBoxOptionLabelButtonMap = new HashMap<>();
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionAAppCompatCheckBox, "A");
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionBAppCompatCheckBox, "B");
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionCAppCompatCheckBox, "C");
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionDAppCompatCheckBox, "D");
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionEAppCompatCheckBox, "E");
        this.appCompatCheckBoxOptionLabelButtonMap.put(optionFAppCompatCheckBox, "F");
        this.submitButton = view.findViewById(R.id.btn_submit_next_quiz_question_fragment);
        this.optionARightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_a);
        this.optionBRightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_b);
        this.optionCRightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_c);
        this.optionDRightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_d);
        this.optionERightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_e);
        this.optionFRightWrongIconImageView = view.findViewById(R.id.img_icon_right_wrong_option_f);
        this.optionLabelRightWrongIconMap = new HashMap<>();
        this.optionLabelRightWrongIconMap.put("A", optionARightWrongIconImageView);
        this.optionLabelRightWrongIconMap.put("B", optionBRightWrongIconImageView);
        this.optionLabelRightWrongIconMap.put("C", optionCRightWrongIconImageView);
        this.optionLabelRightWrongIconMap.put("D", optionDRightWrongIconImageView);
        this.optionLabelRightWrongIconMap.put("E", optionERightWrongIconImageView);
        this.optionLabelRightWrongIconMap.put("F", optionFRightWrongIconImageView);
        this.optionLabelCheckedBorderMap = new HashMap<>();
        this.optionLabelCheckedBorderMap.put("A", optionACheckedBorderView);
        this.optionLabelCheckedBorderMap.put("B", optionBCheckedBorderView);
        this.optionLabelCheckedBorderMap.put("C", optionCCheckedBorderView);
        this.optionLabelCheckedBorderMap.put("D", optionDCheckedBorderView);
        this.optionLabelCheckedBorderMap.put("E", optionECheckedBorderView);
        this.optionLabelCheckedBorderMap.put("F", optionFCheckedBorderView);
        this.optionLabelOptionNoMap = new HashMap<>();
        this.optionLabelOptionNoMap.put("A", optionANoTextView);
        this.optionLabelOptionNoMap.put("B", optionBNoTextView);
        this.optionLabelOptionNoMap.put("C", optionCNoTextView);
        this.optionLabelOptionNoMap.put("D", optionDNoTextView);
        this.optionLabelOptionNoMap.put("E", optionENoTextView);
        this.optionLabelOptionNoMap.put("F", optionFNoTextView);
        this.questionExplanationDragYRelativeLayout = view.findViewById(R.id.dyrl_result_top_sheet_quiz_question_fragment);
        this.resultTitleTextView = view.findViewById(R.id.tv_result_title_quiz_question_fragment);
        this.questionExplanationTextView = view.findViewById(R.id.tv_result_explanation_content_quiz_question_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();

        // show question content
        showQuestionContent();
    }

    private void showQuestionContent() {
        // show top progress color
        this.showTopProgressColor();
        // show question
        this.showQuestion();
        // count down for reading question
        this.readQuestionCountDown(3);
        // set Container On Click Listener For Showing Options In Advance
        this.setContainerOnClickListenerForShowingOptionsInAdvance();
        // show the question content
        new Handler().postDelayed(() -> {
            if (!isOptionsShown) {
                // show the options
                showOptions();
                // move the views
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_question_content, R.id.end_show_question_content, 300);
                new Handler().postDelayed(() -> {
                    isOptionsShown = true;
                    // show the count down progress bar for doing a question
                    doQuestionCountDown(60);
                }, 300);
            }
        }, 3000);
    }

    private void showTopProgressColor() {
        int currentPageArrayIndex = 2 * (questionNo - 1); // 0, 2, 4, 6, 8
        int lastPageArrayIndex = currentPageArrayIndex - 2;
        // set current page as white
        quizStartActivity.getQuizTopProgressLinearLayout().getChildAt(currentPageArrayIndex).setBackgroundResource(R.color.colorWhite);
        if (questionNo != 1) {
            if (quizStartActivity.getQuizResultArray()[questionNo - 2] == 1) {
                // set last right page as green
                quizStartActivity.getQuizTopProgressLinearLayout().getChildAt(lastPageArrayIndex).setBackgroundResource(R.color.rightAnswer);
            } else if (quizStartActivity.getQuizResultArray()[questionNo - 2] == 2) {
                // set last wrong page as green
                quizStartActivity.getQuizTopProgressLinearLayout().getChildAt(lastPageArrayIndex).setBackgroundResource(R.color.wrongAnswer);
            }
        }
    }

    // set Container On Click Listener For Showing Options In Advance
    public void setContainerOnClickListenerForShowingOptionsInAdvance(){
        containerMotionLayout.setOnClickListener(containerView -> {
            if (!isOptionsShown){
                // show the options
                showOptions();
                // move the views
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_question_content, R.id.end_show_question_content, 300);
                isOptionsShown = true;
                new Handler().postDelayed(() -> {
                    // show the count down progress bar for doing a question
                    doQuestionCountDown(60);
                }, 300);
            }
        });
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

    // count down for doing a question
    private void doQuestionCountDown(int timeForCountDown) {
        CountDownTimer mCountDownTimer;
        Drawable drawable = DataConverter.getDrawableById(quizStartActivity, R.drawable.layer_list_dark_progress_bar_horizontal_quiz_question_fragment);
        doQuestionProgressBar.setProgressDrawable(drawable);
        doQuestionProgressBar.setProgress(100);
        doQuestionProgressBar.setVisibility(View.VISIBLE);
        doQuestionTextView.setVisibility(View.VISIBLE);
        mCountDownTimer = new CountDownTimer(timeForCountDown * 1000,5) {
            int currentTime = timeForCountDown * 1000;

            @Override
            public void onTick(long millisUntilFinished) {
                if (!isAnswered) {
                    doQuestionProgressBar.setProgress((int) millisUntilFinished * 100 / (timeForCountDown * 1000));
                    currentTime = (int)millisUntilFinished * 60 / (timeForCountDown * 1000);
                    String counterNoString = currentTime + "s";
                    doQuestionTextView.setText(counterNoString);
                    // change the count text color
                    if (currentTime == 31){
                        doQuestionTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                } else {
                    // stop counter
                    cancel();
                    doQuestionProgressBar.setVisibility(View.INVISIBLE);
                    doQuestionTextView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                // set user answer
                List<String> timeOutUserAnswerList = new ArrayList<>();
                timeOutUserAnswerList.add("Z");
                currentChoiceQuestionModel.setUserAnswerList(timeOutUserAnswerList);
                // set progress bar and text view invisible
                doQuestionProgressBar.setProgress(0);
                doQuestionProgressBar.setVisibility(View.INVISIBLE);
                doQuestionTextView.setVisibility(View.INVISIBLE);
                // show time out in top sheet
                showResultTopSheet(300);
                // show the answers
                if (questionTypeNo == 1){ // single
                    showResultStyleForSingleWhenTimeOut();
                } else if (questionTypeNo == 2) { // multiple
                    showResultStyleForMultipleWhenTimeOut();
                }
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
        if (QuizActivity.currentPageName.equals(QuizActivity.BUTTON_NAME_SHOW_ENVELOPE)){
            optionACardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
            optionBCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
            optionCCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
            optionDCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
            optionECardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
            optionFCardView.setCardBackgroundColor(getResources().getColor(R.color.btn_show_envelope_bg));
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
                }
                cardView.setClickable(false);
            }
            // set radio button style
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (!optionLabelAppCompatRadioButtonMap.get(currentOptionNoString).equals(appCompatRadioButton)) {
                    appCompatRadioButton.setClickable(false);
                } else {
                    appCompatRadioButton.setChecked(true);
                }
            }
            // store user's answer
            userAnswerList = new ArrayList<>();
            userAnswerList.add(currentOptionNoString);
            currentChoiceQuestionModel.setUserAnswerList(userAnswerList);
            // check Single Choice Question Answer
            isAnswerRight = checkSingleChoiceQuestionAnswer(currentOptionNoString);
            // show result style for single
            new Handler().postDelayed(() -> {
                showResultStyleForSingle();
            }, 500);
        });

        // click radio button
        currentAppCompatRadioButton.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility(View.VISIBLE);
            // disable the other radio buttons
            for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList) {
                if (!appCompatRadioButton.equals(currentAppCompatRadioButton)){
                    appCompatRadioButton.setClickable(false);
                }
            }
            // set card view style
            for (CardView cardView : optionCardViewList){
                if (!optionLabelCardViewBoxMap.get(currentOptionNoString).equals(cardView)){
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
                }
                cardView.setClickable(false);
            }
            // store user's answer
            userAnswerList = new ArrayList<>();
            userAnswerList.add(currentOptionNoString);
            currentChoiceQuestionModel.setUserAnswerList(userAnswerList);
            // check Single Choice Question Answer
            isAnswerRight = checkSingleChoiceQuestionAnswer(currentOptionNoString);
            // show result style for single
            new Handler().postDelayed(() -> {
                showResultStyleForSingle();
            }, 500);
        });
    }

    private void setCardOnClickListenerForMultiple(CardView currentCardView,
                                                   AppCompatCheckBox currentAppCompatCheckBox,
                                                   View currentCheckedBorderView,
                                                   String currentOptionNoString
                                                  ) {
        // click card
        currentCardView.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility((currentCheckedBorderView.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
            currentAppCompatCheckBox.setChecked(!currentAppCompatCheckBox.isChecked());
            // show or hide the submit button
            showOrHideSubmitButton(currentAppCompatCheckBox);
        });

        // click checkbox
        currentAppCompatCheckBox.setOnClickListener(v -> {
            // set checked border
            currentCheckedBorderView.setVisibility((currentCheckedBorderView.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
            // show or hide the submit button
            showOrHideSubmitButton(currentAppCompatCheckBox);
        });
    }

    // show or hide the submit button
    private void showOrHideSubmitButton(AppCompatCheckBox currentAppCompatCheckBox) {
        // show the submit button
        if (currentAppCompatCheckBox.isChecked()) {
            if (submitButton.getVisibility() == View.INVISIBLE) {
                submitButton.clearAnimation();
//                submitButton.setVisibility(View.VISIBLE);
                MyAnimationBox.runFadeInAnimation(submitButton, 100);
            }
        } else {
            boolean hasCheckedBox = false;
            for (AppCompatCheckBox appCompatCheckBox : optionAppCompatCheckBoxList){
                if (appCompatCheckBox.isChecked()){
                    hasCheckedBox = true;
                }
            }
            if (!hasCheckedBox){
                submitButton.clearAnimation();
//                submitButton.setVisibility(View.INVISIBLE);
                MyAnimationBox.runFadeOutAnimationToInvisible(submitButton, 100);
            }
        }
    }

    public void setSubmitButtonOnClickListener() {
        submitButton.setOnClickListener(submitButtonView -> {
            if (submitButton.getText().toString().equals("submit")) {
                // store users' answer
                userAnswerList = new ArrayList<>();
                for (AppCompatCheckBox appCompatCheckBox : optionAppCompatCheckBoxList) {
                    if (appCompatCheckBox.isChecked()) {
                        userAnswerList.add(appCompatCheckBoxOptionLabelButtonMap.get(appCompatCheckBox));
                    }
                }
                currentChoiceQuestionModel.setUserAnswerList(userAnswerList);
                // check multiple Choice Question Answer
                isAnswerRight = checkMultipleChoiceQuestionAnswer();
                // show the result style
                new Handler().postDelayed(this::showResultStyleForMultiple, 500);
                // change the button to "next"
                if (questionNo != 5) {
                    submitButton.setText("next");
                } else {
                    // change the button to "next"
                    submitButton.setText("Close");
                }
            } else if (submitButton.getText().toString().equals("next")) {
                quizStartActivity.getQuestionViewPager2().setCurrentItem(questionNo);
            } else if (submitButton.getText().toString().equals("Close")) {
                // animation
                quizStartActivity.finish();
                quizStartActivity.overridePendingTransition(0, R.anim.activity_slide_out_top);
            }
        });
    }

    // check Single Choice Question Answer
    private boolean checkSingleChoiceQuestionAnswer(String selectedOptionLabel) {
        return currentChoiceQuestionModel.getCorrectAnswerList().get(0).equals(selectedOptionLabel);
    }

    // check multiple Choice Question Answer
    private boolean checkMultipleChoiceQuestionAnswer() {
        // check the answer
        return DataComparison.checkTwoListHaveSameItems(currentChoiceQuestionModel.getCorrectAnswerList(), currentChoiceQuestionModel.getUserAnswerList());
    }

    // single result
    private void showResultStyleForSingle() {
        // set isSubmitted as true to stop the counter
        stopDoQuestionCountDown();
        // show the result top sheet
        showResultTopSheet(300);
        // hide all the radio button
        for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList){
            appCompatRadioButton.setVisibility(View.INVISIBLE);
        }
        // set all question label bg
        for (TextView optionNoTextView: optionNoTextViewList){
            // set not selected right option label
            optionNoTextView.setBackgroundResource(R.drawable.shape_not_selected_option_no_right_quiz_question_fragment);
        }
        if (isAnswerRight){
            // get the right answer label
            String rightAnswerString = currentChoiceQuestionModel.getCorrectAnswerList().get(0);
            // set option right icon
            ImageView rightWrongIconImageView = optionLabelRightWrongIconMap.get(rightAnswerString);
            rightWrongIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
            ColorStateList colorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
            rightWrongIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            rightWrongIconImageView.setImageTintList(colorStateList);
            rightWrongIconImageView.setVisibility(View.VISIBLE);
            // set option border
            optionLabelCheckedBorderMap.get(rightAnswerString).setBackgroundResource(R.drawable.shape_option_border_right_quiz_question_fragment);
            // set option label
            optionLabelOptionNoMap.get(rightAnswerString).setBackgroundResource(R.drawable.shape_selected_option_no_right_quiz_question_fragment);
        } else {
            String correctAnswerString = currentChoiceQuestionModel.getCorrectAnswerList().get(0);
            String userAnswerString = currentChoiceQuestionModel.getUserAnswerList().get(0);
            // set option wrong icon
            ImageView wrongIconImageView = optionLabelRightWrongIconMap.get(userAnswerString);
            wrongIconImageView.setImageResource(R.drawable.ic_wrong_circle_black_50dp);
            ColorStateList colorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.wrongAnswer);
            wrongIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            wrongIconImageView.setImageTintList(colorStateList);
            wrongIconImageView.setVisibility(View.VISIBLE);
            // set option right icon
            ImageView rightIconImageView = optionLabelRightWrongIconMap.get(correctAnswerString);
            rightIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
            ColorStateList rightColorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
            rightIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            rightIconImageView.setImageTintList(rightColorStateList);
            rightIconImageView.setVisibility(View.VISIBLE);
            // set wrong option border
            optionLabelCheckedBorderMap.get(userAnswerString).setBackgroundResource(R.drawable.shape_option_border_wrong_quiz_question_fragment);
            // set wrong option label
            optionLabelOptionNoMap.get(userAnswerString).setBackgroundResource(R.drawable.shape_selected_option_no_wrong_quiz_question_fragment);
        }

        // change the button to "next"
        if (questionNo != 5) {
            submitButton.setText("next");
        } else {
            // change the button to "next"
            submitButton.setText("Close");
        }
        submitButton.setVisibility(View.VISIBLE);
    }

    // multiple result
    private void showResultStyleForMultiple(){
        // set isSubmitted as true to stop the counter
        stopDoQuestionCountDown();
        // show the result top sheet
        showResultTopSheet(300);

        // hide all the checkbox
        for (AppCompatCheckBox appCompatCheckBox : optionAppCompatCheckBoxList){
            appCompatCheckBox.setVisibility(View.INVISIBLE);
        }
        // set all question label bg
        for (TextView optionNoTextView: optionNoTextViewList){
            optionNoTextView.setBackgroundResource(R.drawable.shape_not_selected_option_no_right_quiz_question_fragment);
        }
        // disable all tiles
        for (CardView cardView : optionCardViewList){
            if (!currentChoiceQuestionModel.getUserAnswerList().contains(optionCardViewLabelMap.get(cardView))) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
            }
            cardView.setClickable(false);
        }
        if (isAnswerRight){
            for (String correctAnswerString : currentChoiceQuestionModel.getCorrectAnswerList()) {
                // set option right icon
                ImageView rightWrongIconImageView = optionLabelRightWrongIconMap.get(correctAnswerString);
                rightWrongIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
                ColorStateList colorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
                rightWrongIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                rightWrongIconImageView.setImageTintList(colorStateList);
                rightWrongIconImageView.setVisibility(View.VISIBLE);
                // set option border
                optionLabelCheckedBorderMap.get(correctAnswerString).setBackgroundResource(R.drawable.shape_option_border_right_quiz_question_fragment);
                // set option label
                optionLabelOptionNoMap.get(correctAnswerString).setBackgroundResource(R.drawable.shape_selected_option_no_right_quiz_question_fragment);
            }
        } else {
            for (String correctAnswerString : currentChoiceQuestionModel.getCorrectAnswerList()) {
                // set option right icon
                ImageView rightWrongIconImageView = optionLabelRightWrongIconMap.get(correctAnswerString);
                rightWrongIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
                ColorStateList colorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
                rightWrongIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                rightWrongIconImageView.setImageTintList(colorStateList);
                rightWrongIconImageView.setVisibility(View.VISIBLE);
                // set option border
                if (currentChoiceQuestionModel.getUserAnswerList().contains(correctAnswerString)) {
                    optionLabelCheckedBorderMap.get(correctAnswerString).setBackgroundResource(R.drawable.shape_option_border_right_quiz_question_fragment);
                    // set option label
                    optionLabelOptionNoMap.get(correctAnswerString).setBackgroundResource(R.drawable.shape_selected_option_no_right_quiz_question_fragment);
                }
            }
            for (String userWrongAnswerString : currentChoiceQuestionModel.getUserAnswerList()){
                if (!currentChoiceQuestionModel.getCorrectAnswerList().contains(userWrongAnswerString)) {
                    // set option wrong icon
                    ImageView wrongIconImageView = optionLabelRightWrongIconMap.get(userWrongAnswerString);
                    wrongIconImageView.setImageResource(R.drawable.ic_wrong_circle_black_50dp);
                    ColorStateList colorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.wrongAnswer);
                    wrongIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                    wrongIconImageView.setImageTintList(colorStateList);
                    wrongIconImageView.setVisibility(View.VISIBLE);
                    // set wrong option border
                    optionLabelCheckedBorderMap.get(userWrongAnswerString).setBackgroundResource(R.drawable.shape_option_border_wrong_quiz_question_fragment);
                    // set wrong option label
                    optionLabelOptionNoMap.get(userWrongAnswerString).setBackgroundResource(R.drawable.shape_selected_option_no_wrong_quiz_question_fragment);
                }
            }
        }
    }

    // show top result sheet
    private void showResultTopSheet(int duration) {
        // save the result into the result array and show the result immediately
        int currentPageArrayIndex = 2 * (questionNo - 1);
        if (isAnswered && isAnswerRight){
            // right
            QuizStartActivity.quizResultArray[questionNo - 1] = 1;
            quizStartActivity.getQuizTopProgressLinearLayout().getChildAt(currentPageArrayIndex).setBackgroundResource(R.color.rightAnswer);
        } else {
            // wrong
            QuizStartActivity.quizResultArray[questionNo - 1] = 2;
            quizStartActivity.getQuizTopProgressLinearLayout().getChildAt(currentPageArrayIndex).setBackgroundResource(R.color.wrongAnswer);
        }

        if (isAnswered) {
            // check the result and show it
            if (isAnswerRight) {
                questionExplanationDragYRelativeLayout.setBackgroundResource(R.color.rightAnswer);
                resultTitleTextView.setText("RIGHT");
            } else {
                questionExplanationDragYRelativeLayout.setBackgroundResource(R.color.wrongAnswer);
                resultTitleTextView.setText("WRONG");
            }
        } else {
            questionExplanationDragYRelativeLayout.setBackgroundResource(R.color.colorBlack);
            resultTitleTextView.setText("TIME OUT");
        }
        questionExplanationTextView.setText(currentChoiceQuestionModel.getChoiceQuestionExplanation());
        questionExplanationDragYRelativeLayout.setVisibility(View.VISIBLE);
        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_question_result, R.id.end_show_question_result, duration);
    }

    private void stopDoQuestionCountDown() {
        isAnswered = true;
    }

    // time out - single result
    private void showResultStyleForSingleWhenTimeOut() {
        // hide all the radio button
        for (AppCompatRadioButton appCompatRadioButton : optionAppCompatRadioButtonList){
            appCompatRadioButton.setVisibility(View.INVISIBLE);
        }
        // disable all tiles
        for (CardView cardView : optionCardViewList){
            cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
            cardView.setClickable(false);
        }
        // show answers
        // get the correct answer label
        String correctAnswerString = currentChoiceQuestionModel.getCorrectAnswerList().get(0);
        for (TextView optionNoTextView : optionNoTextViewList){
            if (optionNoTextView.getText().toString().equals(correctAnswerString)) {
                // set option right icon
                ImageView rightIconImageView = optionLabelRightWrongIconMap.get(correctAnswerString);
                rightIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
                ColorStateList rightColorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
                rightIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                rightIconImageView.setImageTintList(rightColorStateList);
                rightIconImageView.setVisibility(View.VISIBLE);
            }
        }

        // change the button to "next"
        if (questionNo != 5) {
            submitButton.setText("next");
        } else {
            // change the button to "next"
            submitButton.setText("Close");
        }
        submitButton.setVisibility(View.VISIBLE);
    }

    // time out - multiple result
    private void showResultStyleForMultipleWhenTimeOut() {
        // hide all the checkbox
        for (AppCompatCheckBox appCompatCheckBox : optionAppCompatCheckBoxList){
            appCompatCheckBox.setVisibility(View.INVISIBLE);
        }
        // set all question label bg
        for (TextView optionNoTextView: optionNoTextViewList){
            optionNoTextView.setBackgroundResource(R.drawable.shape_not_selected_option_no_right_quiz_question_fragment);
        }
        // disable all tiles
        for (CardView cardView : optionCardViewList){
            cardView.setCardBackgroundColor(getResources().getColor(R.color.btn_option_bg_checked));
            cardView.setClickable(false);
        }
        // show correct answers
        for (TextView optionNoTextView : optionNoTextViewList){
            if (currentChoiceQuestionModel.getCorrectAnswerList().contains(optionNoTextView.getText().toString())) {
                // set option right icon
                ImageView rightIconImageView = optionLabelRightWrongIconMap.get(optionNoTextView.getText().toString());
                rightIconImageView.setImageResource(R.drawable.ic_right_circle_white_50dp);
                ColorStateList rightColorStateList = ContextCompat.getColorStateList(quizStartActivity, R.color.rightAnswer);
                rightIconImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                rightIconImageView.setImageTintList(rightColorStateList);
                rightIconImageView.setVisibility(View.VISIBLE);
            }
        }
        // change the button to "next"
        if (questionNo != 5) {
            submitButton.setText("next");
        } else {
            // change the button to "next"
            submitButton.setText("Close");
        }
        submitButton.setVisibility(View.VISIBLE);
    }
}
