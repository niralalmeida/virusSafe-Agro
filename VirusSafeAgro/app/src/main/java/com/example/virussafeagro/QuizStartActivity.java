package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.MyAnimationBox;

import java.util.Objects;

public class QuizStartActivity extends AppCompatActivity {
    // data
    private VirusModel currentVirusModel;

    // views
    private MotionLayout containerMotionLayout;
    private LottieAnimationView countDownLottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModel = Objects.requireNonNull(getIntent().getExtras()).getParcelable("currentVirusModel");

        // initialize views
        this.initializeViews();

        // start count down
        this.showQuestion();
    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_start_activity);
        this.countDownLottieAnimationView = findViewById(R.id.lav_count_down_quiz_start);
    }

    // control count down animation
    private void showQuestion() {
        new Handler().postDelayed(() ->{
            // expand the lottie
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_count_down_expand, R.id.end_count_down_expand, 500);
        },4000);

        new Handler().postDelayed(() ->{
            containerMotionLayout.clearAnimation();
            // change the background
            ColorDrawable[] colorDrawablesForBG = {
                    new ColorDrawable(getResources().getColor(R.color.colorPrimaryDarkBG)),
                    new ColorDrawable(getResources().getColor(R.color.bg_quiz_start))};
            TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawablesForBG);
            containerMotionLayout.setBackground(transitionDrawable);
            transitionDrawable.startTransition(100);
        },4500);


        // hide the lottie
        new Handler().postDelayed(() ->{
            countDownLottieAnimationView.setVisibility(View.GONE);
        },4600);
    }
}
