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
    private final String BUTTON_NAME_BEGINNER = "beginner";
    private final String BUTTON_NAME_INTERMEDIATE = "intermediate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model id and full name
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);
        this.currentVirusModelFullName = getIntent().getStringExtra("currentVirusModelFullName");
        // initialize views
        this.initializeViews();
        // show activity views
        this.showActivityViews();

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
        if (!beginnerButton.getText().toString().equals("Start Quiz")) {
            // move beginner Button + hide intermediate Button
            containerMotionLayout.clearAnimation();
            containerMotionLayout.setTransition(R.id.start_beginner, R.id.end_beginner);
            containerMotionLayout.transitionToEnd();
            beginnerButton.setText("Start Quiz");
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_BEGINNER);
        }
    }

    public void intermediateOnClick(View v){
        if (!intermediateButton.getText().toString().equals("Start Quiz")) {
            // move intermediate Button + hide beginner Button
            containerMotionLayout.clearAnimation();
            containerMotionLayout.setTransition(R.id.start_intermediate, R.id.end_intermediate);
            containerMotionLayout.transitionToEnd();
            intermediateButton.setText("Start Quiz");
            // change other views' style
            changeOtherViewsStyle(BUTTON_NAME_INTERMEDIATE);
        }
    }

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
            quizInfoTextView.setText("< 5 Simple Questions >");
        } else if(buttonName.equals(BUTTON_NAME_INTERMEDIATE)){
            quizTitleTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            quizInfoTextView.setTextColor(getResources().getColor(R.color.btn_intermediate_bg));
            quizInfoTextView.setText("< 5 Tricky Questions >");
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

    }
}
