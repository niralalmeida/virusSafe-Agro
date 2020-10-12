package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.MyAnimationBox;

public class QuizActivity extends AppCompatActivity {
    private int currentVirusModelId;

    // views
    private MotionLayout containerMotionLayout;
    private ImageView virusImageView;
    private Button beginnerButton;
    private Button intermediateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);
        // initialize views
        this.initializeViews();
        // show activity views
        this.showActivityViews();

    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_activity);
        this.virusImageView = findViewById(R.id.img_pic_virus_quiz_activity);
        this.beginnerButton = findViewById(R.id.btn_beginner_quiz_activity);
        this.intermediateButton = findViewById(R.id.btn_intermediate_quiz_activity);
    }

    private void showActivityViews() {
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModelId);
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(getResources(), virusPictureDrawableId);
        this.virusImageView.setImageBitmap(virusPictureBitmap);
    }

    public void beginnerOnClick(View v){
//        MyAnimationBox.runFadeOutAnimation(intermediateButton, 200);

        if (!beginnerButton.getText().toString().equals("Start Quiz")) {
            intermediateButton.setVisibility(View.GONE);
            containerMotionLayout.clearAnimation();
            containerMotionLayout.setTransition(R.id.start_beginner, R.id.end_beginner);
            containerMotionLayout.transitionToEnd();
            beginnerButton.setText("Start Quiz");
        }
    }

    public void intermediateOnClick(View v){
//        MyAnimationBox.runFadeOutAnimation(beginnerButton, 200);

        if (!intermediateButton.getText().toString().equals("Start Quiz")) {
            beginnerButton.setVisibility(View.GONE);
            containerMotionLayout.clearAnimation();
            containerMotionLayout.setTransition(R.id.start_intermediate, R.id.end_intermediate);
            containerMotionLayout.transitionToEnd();
            intermediateButton.setText("Start Quiz");
        }
    }

    public void close(View v) {
        // animation
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_fade_out);
    }
}
