package com.example.virussafeagro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.virussafeagro.adapters.OnBoardingSlideAdapter;
import com.example.virussafeagro.uitilities.AppAuthentication;

public class OnBoardingActivity extends AppCompatActivity {
    private AppCompatActivity onBoardingActivity = this;

    private ViewPager slideViewPager;
    private LinearLayout dotButtonsLinearLayout;
    private TextView[] bottomDotsTextViewArray;
    private Button backButton;
    private Button nextButton;

    private OnBoardingSlideAdapter onBoardingSlideAdapter;
    private int currentPagePosition;

    private Button launchAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // set app password
        AppAuthentication.setAppPassword(this);

        // hide top status bar
        this.hideTopStatusBar();

        // initialize Views
        this.initializeViews();

        // initialize SlideAdapter and ViewPager
        this.initializeSlideAdapterAndViewPager();

        // add dots
        this.addDotsIndicator(0);
        // add dots listener
        this.slideViewPager.addOnPageChangeListener(this.viewPagerListener);

        // initialize next button
        this.nextButton.setVisibility(View.VISIBLE);
        this.nextButton.setText("Next");

        // set buttons' listeners
        this.setBackButtonOnClickListener();
        this.setNextButtonOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check authentication
        new Handler().postDelayed(() -> AppAuthentication.checkAuthentication(onBoardingActivity),200);
    }

    private void hideTopStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initializeViews() {
        this.slideViewPager = findViewById(R.id.slide_view_pager_boarding);
        this.dotButtonsLinearLayout = findViewById(R.id.ll_dot_button_boarding);
        this.backButton = findViewById(R.id.btn_back_boarding);
        this.nextButton = findViewById(R.id.btn_next_boarding);
        this.launchAppButton = findViewById(R.id.btn_launch_app_boarding);
    }

    private void initializeSlideAdapterAndViewPager() {
        this.onBoardingSlideAdapter = new OnBoardingSlideAdapter(this);
        this.slideViewPager.setAdapter(this.onBoardingSlideAdapter);
    }

    private void addDotsIndicator(int position) {

        this.bottomDotsTextViewArray = new TextView[3];
        this.dotButtonsLinearLayout.removeAllViews();

        for (int i = 0; i < this.bottomDotsTextViewArray.length; i++) {
            this.bottomDotsTextViewArray[i] = new TextView(this);
            this.bottomDotsTextViewArray[i].setText(Html.fromHtml("&#8226"));
            this.bottomDotsTextViewArray[i].setTextSize(35);
            this.bottomDotsTextViewArray[i].setTextColor(getResources().getColor(R.color.colorGreyForDots));

            dotButtonsLinearLayout.addView(this.bottomDotsTextViewArray[i]);
        }

        if (this.bottomDotsTextViewArray.length > 0) {
            bottomDotsTextViewArray[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPagePosition = position;

            // control the buttons
            if (position == 0) {
                backButton.setEnabled(false);
                backButton.setVisibility(View.INVISIBLE);
                backButton.setText("");

                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setText("Next");

                launchAppButton.setVisibility(View.VISIBLE);
                launchAppButton.setAlpha(0);
            } else if (position == bottomDotsTextViewArray.length - 1) {
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                backButton.setText("BACK");

                nextButton.setEnabled(false);
                nextButton.setVisibility(View.INVISIBLE);
                nextButton.setText("");

                launchAppButton.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .setListener(null);
                setLaunchAppButtonOnClickListener();

            } else {
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                backButton.setText("BACK");

                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setText("NEXT");

                launchAppButton.setVisibility(View.VISIBLE);
                launchAppButton.setAlpha(0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setBackButtonOnClickListener() {
        this.backButton.setOnClickListener(view -> {
            slideViewPager.setCurrentItem(currentPagePosition - 1);
        });
    }

    private void setNextButtonOnClickListener() {
        this.nextButton.setOnClickListener(view -> {
            slideViewPager.setCurrentItem(currentPagePosition + 1);
        });
    }

    private void setLaunchAppButtonOnClickListener() {
        this.launchAppButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, android.R.anim.fade_out);
        });
    }

}
