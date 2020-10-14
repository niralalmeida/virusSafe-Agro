package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;

public class QuizActivity extends AppCompatActivity {
    private int currentVirusModelId;
    private String currentVirusModelFullName;

    // views
    private MotionLayout containerMotionLayout;
    private ImageButton closeImageButton;
    private TextView quizTitleTextView;
    private TextView virusFullNameTextView;
    private TextView virusFullNamePaperTextView;
    private ImageView virusImageView;
    private Button beginnerButton;
    private Button intermediateButton;
    private Button startQuizButton;
    private TextView quizInfoTextView;
    private RelativeLayout envelopeCoverClosedRelativeLayout;
    private RelativeLayout envelopeCoverOpenedRelativeLayout;
    private LinearLayout paperContentLinearLayout;

    // button names
    private String BUTTON_NAME_BEGINNER;
    private String BUTTON_NAME_INTERMEDIATE;
    private String BUTTON_NAME_OPEN_QUIZ;
    private String TEXT_SIMPLE_TIP;
    private String TEXT_TRICKY_TIP;
    private String TEXT_SINGLE_CHOICE_QUESTION;
    private String TEXT_MULTIPLE_CHOICE_QUESTION;

    // current page identification tags
    private String currentPageName;
    private final int QUESTION_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model id and full name
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);
        this.currentVirusModelFullName = getIntent().getStringExtra("currentVirusModelFullName");
        // initialize variables and data
        this.initializeVariablesAndData();
        // initialize views
        this.initializeViews();
        // show activity views
        this.showActivityViews();

    }

    private void initializeVariablesAndData() {
        BUTTON_NAME_BEGINNER = getResources().getString(R.string.btn_beginner);
        BUTTON_NAME_INTERMEDIATE = getResources().getString(R.string.btn_intermediate);
        BUTTON_NAME_OPEN_QUIZ = getResources().getString(R.string.btn_open_quiz);
        TEXT_SIMPLE_TIP = getResources().getString(R.string.text_simple_tip);
        TEXT_TRICKY_TIP = getResources().getString(R.string.text_tricky_tip);
        TEXT_SINGLE_CHOICE_QUESTION = getResources().getString(R.string.text_single_choice_quiz);
        TEXT_MULTIPLE_CHOICE_QUESTION = getResources().getString(R.string.text_multiple_choice_quiz);
    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_activity);
        this.closeImageButton = findViewById(R.id.imgbtn_close_quiz_activity);
        this.quizTitleTextView = findViewById(R.id.tv_title_quiz_activity);
        this.virusFullNameTextView = findViewById(R.id.tv_virus_full_name_quiz_quiz_activity);
        this.virusFullNamePaperTextView = findViewById(R.id.tv_virus_full_name_paper_quiz_activity);
        this.virusImageView = findViewById(R.id.img_pic_virus_quiz_activity);
        this.beginnerButton = findViewById(R.id.btn_beginner_quiz_activity);
        this.intermediateButton = findViewById(R.id.btn_intermediate_quiz_activity);
        this.startQuizButton = findViewById(R.id.btn_start_quiz_activity);
        this.quizInfoTextView = findViewById(R.id.tv_info_quiz_activity);
        this.envelopeCoverClosedRelativeLayout = findViewById(R.id.cv_envelope_cover_closed_quiz_activity);
        this.envelopeCoverOpenedRelativeLayout = findViewById(R.id.cv_envelope_cover_opened_quiz_activity);
        this.paperContentLinearLayout= findViewById(R.id.ll_paper_content_quiz_activity);
    }

    private void showActivityViews() {
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModelId);
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(getResources(), virusPictureDrawableId);
        this.virusImageView.setImageBitmap(virusPictureBitmap);
        // virus full name
        this.virusFullNameTextView.setText(this.currentVirusModelFullName);
        this.virusFullNamePaperTextView.setText(this.currentVirusModelFullName);
    }

    public void beginnerOnClick(View v){
        if (!beginnerButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)) {
            // set the current page identification
            currentPageName = BUTTON_NAME_BEGINNER;
            // move beginner Button + hide intermediate Button
            configureTheAnimation(R.id.start_beginner, R.id.end_beginner, 500);
            // set the button text
            beginnerButton.setText(BUTTON_NAME_OPEN_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_BEGINNER);
        } else { // open the quiz paper
            // open the envelope cover
            openTheEnvelopeCover();

            // change the start button color
            startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_beginner_quiz_activity);
            // move down the envelope
            new Handler().postDelayed(()->{
                configureTheAnimation(R.id.start_open_quiz_beginner, R.id.end_open_quiz_beginner, 650);
            }, 300);
            // change the virus full name size
            virusFullNameTextView.setTextSize(12);
        }
    }

    public void intermediateOnClick(View v){
        if (!intermediateButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)) {
            // set the current page identification
            currentPageName = BUTTON_NAME_INTERMEDIATE;
            // move intermediate Button + hide beginner Button
            configureTheAnimation(R.id.start_intermediate, R.id.end_intermediate, 500);
            // set the button text
            intermediateButton.setText(BUTTON_NAME_OPEN_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_INTERMEDIATE);
        } else { // open the quiz paper
            // open the envelope cover
            openTheEnvelopeCover();

            // change the start button color
            startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_intermediate_quiz_activity);
            // move down the envelope
            new Handler().postDelayed(()->{
                configureTheAnimation(R.id.start_open_quiz_intermediate, R.id.end_open_quiz_intermediate, 650);
            }, 300);
            // change the virus full name size
            virusFullNameTextView.setTextSize(12);
        }
    }

    public void startQuizOnClick(View v){

    }

    // open the envelope cover when click "start quiz" button
    private void openTheEnvelopeCover() {
        containerMotionLayout.clearAnimation();
        // hide bottom cover
        envelopeCoverClosedRelativeLayout.setVisibility(View.INVISIBLE);
        // show top cover
        envelopeCoverOpenedRelativeLayout.setVisibility(View.VISIBLE);
    }

    // close the envelope cover when click "back" button
    private void closeTheEnvelopeCover() {
        containerMotionLayout.clearAnimation();
        // hide top cover
        envelopeCoverOpenedRelativeLayout.setVisibility(View.INVISIBLE);
        // show bottom cover
        envelopeCoverClosedRelativeLayout.setVisibility(View.VISIBLE);
    }

    // change the style when the 2 buttons are clicked
    private void changeOtherViewsStyle(String buttonName) {
        // change the background
        ColorDrawable[] colorDrawablesForBG = {
                new ColorDrawable(getResources().getColor(R.color.colorPrimaryDarkBG)),
                new ColorDrawable(getResources().getColor(R.color.colorWhite))};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawablesForBG);
        containerMotionLayout.setBackground(transitionDrawable);
        transitionDrawable.startTransition(300);
        // change the quiz title color + quiz info text/color
        if(buttonName.equals(BUTTON_NAME_BEGINNER)) {
            quizTitleTextView.setTextColor(getResources().getColor(R.color.btn_beginner_bg));
            quizInfoTextView.setTextColor(getResources().getColor(R.color.btn_beginner_bg));
            quizInfoTextView.setText(TEXT_SIMPLE_TIP);
        } else if(buttonName.equals(BUTTON_NAME_INTERMEDIATE)){
            quizTitleTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            quizInfoTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            quizInfoTextView.setText(TEXT_TRICKY_TIP);
        }
        // change the virus full name color
        virusFullNameTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDarkBG));

        // change the close button color
        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryDarkBG);
        closeImageButton.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
        closeImageButton.setImageTintList(colorStateList);

        // configure the question type list
        setPaperContent();
    }

    // question type list
    private void setPaperContent() {
        paperContentLinearLayout.removeAllViews();
        for (int i = 0; i < QUESTION_COUNT; i++) {
            int id = i + 1;
            // line LinearLayout
            LinearLayout linearLayout = new LinearLayout(this);
            int linearLayoutId = DataConverter.getQuizQuestionId(id);
            linearLayout.setId(linearLayoutId);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            // text for "Q# - "
            TextView questionNoTextView = new TextView(this);
            String questionNoString = "Q" + id;
            questionNoTextView.setText(questionNoString);
            questionNoTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
            questionNoTextView.setTextSize(15);
            if (currentPageName.equals(BUTTON_NAME_BEGINNER)) {
                questionNoTextView.setTextColor(getResources().getColor(R.color.btn_beginner_bg));
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                questionNoTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            }
            // question type
            TextView questionTypeTextView = new TextView(this);
            String questionTypeString = " - " + TEXT_SINGLE_CHOICE_QUESTION;
            questionTypeTextView.setText(questionTypeString);
            questionTypeTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
            questionTypeTextView.setTextSize(13);
            // add the text views and linear layout
            linearLayout.addView(questionNoTextView);
            linearLayout.addView(questionTypeTextView);
            paperContentLinearLayout.addView(linearLayout);
            // set margin top
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.topMargin = 5;
            linearLayout.setLayoutParams(layoutParams);
        }
    }

    public void closeOnClick(View v) {
        // animation
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_slide_out_top);
    }

    public void backOnClick(View v){
        // the start button is not shown
        if (startQuizButton.getVisibility() != View.VISIBLE) {
            if (currentPageName.equals(BUTTON_NAME_BEGINNER)) {
                // move beginner Button + show intermediate Button
                configureTheAnimation(R.id.end_beginner, R.id.start_beginner, 500);
                // set the button text
                beginnerButton.setText(BUTTON_NAME_BEGINNER);
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                // move intermediate Button + show beginner Button
                configureTheAnimation(R.id.end_intermediate, R.id.start_intermediate, 500);
                // set the button text
                intermediateButton.setText(BUTTON_NAME_INTERMEDIATE);
            }
            // resume other views' style
            resumeOtherViewsStyle();
        } // the start button is shown
        else {
            if (currentPageName.equals(BUTTON_NAME_BEGINNER)) {
                // back to the envelope page
                configureTheAnimation(R.id.end_open_quiz_beginner, R.id.start_open_quiz_beginner, 500);
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                // back to the envelope page
                configureTheAnimation(R.id.end_open_quiz_intermediate, R.id.start_open_quiz_intermediate, 500);
            }
            new Handler().postDelayed(this::closeTheEnvelopeCover, 800);
        }
    }

    // resume the style when the back buttons are clicked
    private void resumeOtherViewsStyle() {
        // change the background
        ColorDrawable[] colorDrawablesForBG = {
                new ColorDrawable(getResources().getColor(R.color.colorWhite)),
                new ColorDrawable(getResources().getColor(R.color.colorPrimaryDarkBG))};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawablesForBG);
        containerMotionLayout.setBackground(transitionDrawable);
        transitionDrawable.startTransition(300);
        // change the quiz title color
        quizTitleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryTitle));
        // change the virus full name color
        virusFullNameTextView.setTextColor(getResources().getColor(R.color.colorPrimaryTitle));
        // change the close button color
        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryTitle);
        closeImageButton.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
        closeImageButton.setImageTintList(colorStateList);
    }

    private void configureTheAnimation(int start, int end, int duration) {
        // move beginner Button + show intermediate Button
        containerMotionLayout.clearAnimation();
        containerMotionLayout.setTransition(start, end);
        containerMotionLayout.setTransitionDuration(duration);
        containerMotionLayout.transitionToEnd();
    }
}
