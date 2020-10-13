package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.MyAnimationBox;

public class QuizActivity extends AppCompatActivity {
    private int currentVirusModelId;
    private String currentVirusModelFullName;

    // views
    private MotionLayout containerMotionLayout;
    private ImageButton closeImageButton;
    private TextView quizTitleTextView;
    private TextView virusFullNameTextView;
    private ImageView virusImageView;
    private Button beginnerButton;
    private Button intermediateButton;
    private TextView quizInfoTextView;

    // button names
    private String BUTTON_NAME_BEGINNER;
    private String BUTTON_NAME_INTERMEDIATE;
    private String BUTTON_NAME_START_QUIZ;
    private String TEXT_SIMPLE_TIP;
    private String TEXT_TRICKY_TIP;

    // current page identification tags
    private String currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model id and full name
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);
        this.currentVirusModelFullName = getIntent().getStringExtra("currentVirusModelFullName");
        // initialize variables
        this.initializeVariables();
        // initialize views
        this.initializeViews();
        // show activity views
        this.showActivityViews();

    }

    private void initializeVariables() {
        BUTTON_NAME_BEGINNER = getResources().getString(R.string.btn_beginner);
        BUTTON_NAME_INTERMEDIATE = getResources().getString(R.string.btn_intermediate);
        BUTTON_NAME_START_QUIZ = getResources().getString(R.string.btn_start_quiz);
        TEXT_SIMPLE_TIP = getResources().getString(R.string.text_simple_tip);
        TEXT_TRICKY_TIP = getResources().getString(R.string.text_tricky_tip);
    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_activity);
        this.closeImageButton = findViewById(R.id.imgbtn_close_quiz_activity);
        this.quizTitleTextView = findViewById(R.id.tv_title_quiz_activity);
        this.virusFullNameTextView = findViewById(R.id.tv_virus_full_name_quiz_quiz_activity);
        this.virusImageView = findViewById(R.id.img_pic_virus_quiz_activity);
        this.beginnerButton = findViewById(R.id.btn_beginner_quiz_activity);
        this.intermediateButton = findViewById(R.id.btn_intermediate_quiz_activity);
        this.quizInfoTextView = findViewById(R.id.tv_info_quiz_activity);
    }

    private void showActivityViews() {
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModelId);
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(getResources(), virusPictureDrawableId);
        this.virusImageView.setImageBitmap(virusPictureBitmap);
        // virus full name
        this.virusFullNameTextView.setText(this.currentVirusModelFullName);
    }

    public void beginnerOnClick(View v){
        if (!beginnerButton.getText().toString().equals(BUTTON_NAME_START_QUIZ)) {
            // set the current page identification
            currentPage = BUTTON_NAME_BEGINNER;
            // move beginner Button + hide intermediate Button
            configureTheAnimation(R.id.start_beginner, R.id.end_beginner);
            // set the button text
            beginnerButton.setText(BUTTON_NAME_START_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_BEGINNER);
        }
    }

    public void intermediateOnClick(View v){
        if (!intermediateButton.getText().toString().equals(BUTTON_NAME_START_QUIZ)) {
            // set the current page identification
            currentPage = BUTTON_NAME_INTERMEDIATE;
            // move intermediate Button + hide beginner Button
            configureTheAnimation(R.id.start_intermediate, R.id.end_intermediate);
            // set the button text
            intermediateButton.setText(BUTTON_NAME_START_QUIZ);
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_INTERMEDIATE);
        }
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
        // change the virus full name color and style (cancel bold)
        virusFullNameTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDarkBG));
        Paint paint = virusFullNameTextView.getPaint();
        if (paint != null) {
            paint.setFakeBoldText(false);
        }
        virusFullNameTextView.postInvalidate();

        // change the close button color
        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryDarkBG);
        closeImageButton.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
        closeImageButton.setImageTintList(colorStateList);
    }

    public void close(View v) {
        // animation
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_fade_out);
    }

    public void back(View v){
        if (currentPage.equals(BUTTON_NAME_BEGINNER)) {
            // move beginner Button + show intermediate Button
            configureTheAnimation(R.id.end_beginner, R.id.start_beginner);
            // set the button text
            intermediateButton.setText(BUTTON_NAME_BEGINNER);
        } else if (currentPage.equals(BUTTON_NAME_INTERMEDIATE)){
            // move intermediate Button + show beginner Button
            configureTheAnimation(R.id.end_intermediate, R.id.start_intermediate);
            // set the button text
            intermediateButton.setText(BUTTON_NAME_INTERMEDIATE);
        }
    }

    // resume the style when the back buttons are clicked
    private void resumeOtherViewsStyle(String buttonName) {

    }

    private void configureTheAnimation(int start, int end) {
        // move beginner Button + show intermediate Button
        containerMotionLayout.clearAnimation();
        containerMotionLayout.setTransition(start, end);
        containerMotionLayout.transitionToEnd();
    }
}
