package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.viewModel.QuizActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {
    // data
    private VirusModel currentVirusModel;
    private QuizActivityViewModel quizActivityViewModel;
    public static List<ChoiceQuestionModel> choiceQuestionModelList; // all questions
    public static List<ChoiceQuestionModel> choiceQuestionModelFinalList; // 5 questions

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
    private ProgressBar loadQuestionProgressBar;

    // button names
    public static String BUTTON_NAME_BEGINNER;
    public static String BUTTON_NAME_INTERMEDIATE;
    private String BUTTON_NAME_OPEN_QUIZ;
    private String TEXT_SIMPLE_TIP;
    private String TEXT_TRICKY_TIP;
    private String TEXT_SINGLE_CHOICE_QUESTION;
    private String TEXT_MULTIPLE_CHOICE_QUESTION;

    // current page identification tags
    public static String currentPageName;
    public final static int QUESTION_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModel = Objects.requireNonNull(getIntent().getExtras()).getParcelable("currentVirusModel");
        // initialize variables and data
        this.initializeVariablesAndData();
        // initialize VirusQuizQuestionViewModel
        this.initializeVirusQuizQuestionViewModel();
        // initialize views
        this.initializeViews();

        // start processing the finding question list process
        this.findVirusQuizQuestionsFromDB();
        // set observer for observing VirusQuizQuestionArrayLD
        this.observeVirusQuizQuestionArrayLD();

        // show activity views
        this.showActivityViews();
    }

    private void initializeVariablesAndData() {
        currentPageName = "";
        BUTTON_NAME_BEGINNER = getResources().getString(R.string.btn_beginner);
        BUTTON_NAME_INTERMEDIATE = getResources().getString(R.string.btn_intermediate);
        BUTTON_NAME_OPEN_QUIZ = getResources().getString(R.string.btn_open_quiz);
        TEXT_SIMPLE_TIP = getResources().getString(R.string.text_simple_tip);
        TEXT_TRICKY_TIP = getResources().getString(R.string.text_tricky_tip);
        TEXT_SINGLE_CHOICE_QUESTION = getResources().getString(R.string.text_single_choice_quiz);
        TEXT_MULTIPLE_CHOICE_QUESTION = getResources().getString(R.string.text_multiple_choice_quiz);
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.quizActivityViewModel = new ViewModelProvider(this).get(QuizActivityViewModel.class);
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
        this.loadQuestionProgressBar= findViewById(R.id.pb_load_question_quiz_activity);
    }

    // start processing the finding question list process
    private void findVirusQuizQuestionsFromDB() {
        // test
        System.out.println("yes");
        choiceQuestionModelList = new ArrayList<>();
        choiceQuestionModelFinalList = new ArrayList<>();
        this.quizActivityViewModel.processFindingVirusQuizQuestions(currentVirusModel.getVirusId());
    }

    // observe VirusQuizQuestionArray live data
    private void observeVirusQuizQuestionArrayLD() {
        this.quizActivityViewModel.getQuizQuestionModelListLD().observe(this, resultQuizQuestionModelList -> {
            // set the question model list
            choiceQuestionModelList = resultQuizQuestionModelList;
            // get final list
            choiceQuestionModelFinalList = getFinalChoiceQuestionModelList();
            // when the "open quiz paper" button shows
            if (loadQuestionProgressBar.getVisibility() == View.VISIBLE){
                // hide the progress bar if it is shown
                loadQuestionProgressBar.setVisibility(View.INVISIBLE);
                // configure the question type list
                setPaperContent();
                // run the animation if clicking the "open quiz paper" button
                if (beginnerButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)){
                    // open the envelope cover
                    openTheEnvelopeCover();
                    // change the start button color
                    startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_beginner_quiz_activity);
                    // move down the envelope
                    new Handler().postDelayed(()->{
                        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_open_quiz_beginner, R.id.end_open_quiz_beginner, 650);
                    }, 300);
                } else if (intermediateButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)){
                    // open the envelope cover
                    openTheEnvelopeCover();
                    // change the start button color
                    startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_intermediate_quiz_activity);
                    // move down the envelope
                    new Handler().postDelayed(()->{
                        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_open_quiz_intermediate, R.id.end_open_quiz_intermediate, 650);
                    }, 300);
                }
            }
        });
    }

//////////////////////////////// test //////////////////////////////////////////////////////
    private List<ChoiceQuestionModel> getFinalChoiceQuestionModelList() {
        List<ChoiceQuestionModel> finalList = choiceQuestionModelList;
        return finalList;
    }

    // show all initial views
    private void showActivityViews() {
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModel.getVirusId());
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(getResources(), virusPictureDrawableId);
        this.virusImageView.setImageBitmap(virusPictureBitmap);
        // virus full name
        this.virusFullNameTextView.setText(currentVirusModel.getVirusFullName());
        this.virusFullNamePaperTextView.setText(currentVirusModel.getVirusFullName());
    }

    public void beginnerOnClick(View v){
        if (!beginnerButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)) {
            // set the current page identification
            currentPageName = BUTTON_NAME_BEGINNER;
            // move beginner Button + hide intermediate Button
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_beginner, R.id.end_beginner, 500);
            // set the button text
            beginnerButton.setText(BUTTON_NAME_OPEN_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_BEGINNER);
        } else { // open the quiz paper
            if (!choiceQuestionModelFinalList.isEmpty()) {
                // configure the question type list
                setPaperContent();
                // open the envelope cover
                openTheEnvelopeCover();
                // change the start button color
                startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_beginner_quiz_activity);
                // move down the envelope
                new Handler().postDelayed(() -> {
                    MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_open_quiz_beginner, R.id.end_open_quiz_beginner, 650);
                }, 300);
            } else {
                // show the progress bar
                loadQuestionProgressBar.setIndeterminateDrawable(getDrawable(R.drawable.rotate_progress_bar_beginner_load_question_quiz_activity));
                loadQuestionProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void intermediateOnClick(View v){
        if (!intermediateButton.getText().toString().equals(BUTTON_NAME_OPEN_QUIZ)) {
            // set the current page identification
            currentPageName = BUTTON_NAME_INTERMEDIATE;
            // move intermediate Button + hide beginner Button
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_intermediate, R.id.end_intermediate, 500);
            // set the button text
            intermediateButton.setText(BUTTON_NAME_OPEN_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_INTERMEDIATE);
        } else { // open the quiz paper
            if (!choiceQuestionModelFinalList.isEmpty()) {
                // configure the question type list
                setPaperContent();
                // open the envelope cover
                openTheEnvelopeCover();
                // change the start button color
                startQuizButton.setBackgroundResource(R.drawable.ripple_btn_start_intermediate_quiz_activity);
                // move down the envelope
                new Handler().postDelayed(() -> {
                    MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_open_quiz_intermediate, R.id.end_open_quiz_intermediate, 650);
                }, 300);
            } else {
                // show the progress bar
                loadQuestionProgressBar.setIndeterminateDrawable(getDrawable(R.drawable.rotate_progress_bar_intermediate_load_question_quiz_activity));
                loadQuestionProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void startQuizOnClick(View v){
        Intent intent = new Intent(QuizActivity.this, QuizStartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentVirusModel", currentVirusModel);
        intent.putExtras(bundle);
        // animation
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, this.startQuizButton, ViewCompat.getTransitionName(this.startQuizButton));
        startActivity(intent, options.toBundle());

        // or create directly
//        ChangeImageTransform changeImageTransform = new ChangeImageTransform();
//        getWindow().setSharedElementEnterTransition(changeImageTransform);
        // add quiz fragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fl_quiz_fragments, new QuizQuestionFragment())
//                .addSharedElement(virusImageCardView, getString(R.string.quiz_activity_transition_name))
//                .commit();
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
            String questionNoString = "Q" + id + ". ";
            questionNoTextView.setText(questionNoString);
            questionNoTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
            questionNoTextView.setTextSize(15);
            if (currentPageName.equals(BUTTON_NAME_BEGINNER)) {
                questionNoTextView.setTextColor(getResources().getColor(R.color.btn_beginner_bg));
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                questionNoTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            }
            // question type items
            TextView questionTypeTextView = new TextView(this);
            String questionTypeString = "";
            if (choiceQuestionModelFinalList.get(i).getChoiceQuestionType().equals("single")) {
                questionTypeString = TEXT_SINGLE_CHOICE_QUESTION;
            } else if (choiceQuestionModelFinalList.get(i).getChoiceQuestionType().equals("multiple")){
                questionTypeString = TEXT_MULTIPLE_CHOICE_QUESTION;
            }
            questionTypeTextView.setText(questionTypeString);
            questionTypeTextView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
            questionTypeTextView.setTextSize(12);

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
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_beginner, R.id.start_beginner, 500);
                // set the button text
                beginnerButton.setText(BUTTON_NAME_BEGINNER);
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                // move intermediate Button + show beginner Button
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_intermediate, R.id.start_intermediate, 500);
                // set the button text
                intermediateButton.setText(BUTTON_NAME_INTERMEDIATE);
            }
            // resume other views' style
            resumeOtherViewsStyle();
        } // the start button is shown
        else {
            if (currentPageName.equals(BUTTON_NAME_BEGINNER)) {
                // back to the envelope page
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_open_quiz_beginner, R.id.start_open_quiz_beginner, 500);
            } else if (currentPageName.equals(BUTTON_NAME_INTERMEDIATE)) {
                // back to the envelope page
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_open_quiz_intermediate, R.id.start_open_quiz_intermediate, 500);
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

    // cancel the Current Finding Virus Quiz Questions AsyncTask
    private void cancelCurrentFindVirusQuizQuestionsAsyncTask() {
        // cancel the AsyncTask
        QuizActivityViewModel.FindVirusQuizQuestionsAsyncTask f = this.quizActivityViewModel.getCurrentFindVirusQuizQuestionsAsyncTask();
        f.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // cancel the Current FindVirusQuizQuestionsAsyncTask
        this.cancelCurrentFindVirusQuizQuestionsAsyncTask();
//        // clear the question list
//        choiceQuestionModelList = null;
    }
}
