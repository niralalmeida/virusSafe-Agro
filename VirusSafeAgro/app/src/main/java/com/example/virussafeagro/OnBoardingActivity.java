package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.virussafeagro.adapters.OnBoardingSlideAdapter;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;

public class OnBoardingActivity extends AppCompatActivity {
    private SharedPreferenceProcess spp;

    private ViewPager slideViewPager;
    private LinearLayout dotButtonsLinearLayout;
    private TextView[] bottomDotsTextViewArray;
    private Button skipButton;
    private TextView swipeTextView;
    private Button backButton;
    private Button nextButton;

    private OnBoardingSlideAdapter onBoardingSlideAdapter;
    private int currentPagePosition;
    private boolean isLaunchAppListenerSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_onboarding);
        // hide top status bar
        this.hideTopStatusBar();

        // initialize Views
        this.initializeViews();

        // initialize SlideAdapter and ViewPager
        this.initializeSlideAdapterAndViewPager();

        // show swipe tip
        this.showSwipe();

        // configure dots
            // add dots
        this.addDotsIndicator(0);
            // add dots listener
        this.slideViewPager.addOnPageChangeListener(this.viewPagerListener);

        // configure back and next buttons
        this.configureButtons();

        // show password activity
        new Handler().postDelayed(this::showPasswordActivity,200);
    }

    private void showPasswordActivity() {
        Intent returnIntent = getIntent();
        String whereFromString = returnIntent.getStringExtra("whereFrom");
        if (whereFromString.equals("main")){
            Intent intent = new Intent(OnBoardingActivity.this, PasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_slide_in_bottom, 0);
        }
    }

    private void hideTopStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initializeViews() {
        this.slideViewPager = findViewById(R.id.slide_view_pager_boarding);
        this.dotButtonsLinearLayout = findViewById(R.id.ll_dot_button_boarding);
        this.skipButton = findViewById(R.id.btn_skip_boarding);
        this.swipeTextView = findViewById(R.id.tv_swipe_boarding);
        this.backButton = findViewById(R.id.btn_back_boarding);
        this.nextButton = findViewById(R.id.btn_next_boarding);
    }

    private void initializeSlideAdapterAndViewPager() {
        this.onBoardingSlideAdapter = new OnBoardingSlideAdapter(this);
        this.slideViewPager.setAdapter(this.onBoardingSlideAdapter);
    }

    private void configureButtons() {
        // set skip button on click listener
        this.setSkipButtonOnClickListener();

        // initialize next button
        this.nextButton.setVisibility(View.VISIBLE);
        this.nextButton.setText("Next");

        // set buttons' listeners
        this.setBackButtonOnClickListener();
        this.setNextButtonOnClickListener();
    }

    private void addDotsIndicator(int position) {

        this.bottomDotsTextViewArray = new TextView[OnBoardingSlideAdapter.slide_headings.length];
        this.dotButtonsLinearLayout.removeAllViews();

        for (int i = 0; i < this.bottomDotsTextViewArray.length; i++) {
            this.bottomDotsTextViewArray[i] = new TextView(this);
            this.bottomDotsTextViewArray[i].setText(Html.fromHtml("&#8226&nbsp"));
            this.bottomDotsTextViewArray[i].setTextSize(50);
            this.bottomDotsTextViewArray[i].setTextColor(getResources().getColor(R.color.colorGreyForDots));

            dotButtonsLinearLayout.addView(this.bottomDotsTextViewArray[i]);
        }

        if (this.bottomDotsTextViewArray.length > 0) {
            bottomDotsTextViewArray[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void setDotOnClickListener() {
        for (int dotIndex = 0; dotIndex < OnBoardingSlideAdapter.slide_headings.length; dotIndex++) {
            // add on click listener
            if (dotIndex != currentPagePosition) {
                TextView tv = bottomDotsTextViewArray[dotIndex];
                int finalDotIndex = dotIndex;
                tv.setOnClickListener(view -> {
                    slideViewPager.setCurrentItem(finalDotIndex);
                });
            }
        }
    }

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // store position
            currentPagePosition = position;

            // set launch app button on click listener
            if ((onBoardingSlideAdapter.getLaunchAppButton() != null) && (!isLaunchAppListenerSet)){
                isLaunchAppListenerSet = true;
                setLaunchAppButtonOnClickListener();
            }

            // set dots
            addDotsIndicator(position);
            setDotOnClickListener();

            // set swipe tip
            if (position != 0) {
                // hide swipe tip
                swipeTextView.setVisibility(View.INVISIBLE);
                // clear the animation
                swipeTextView.clearAnimation();
            }

            // set skip
            if (position == OnBoardingSlideAdapter.slide_headings.length - 1){
                skipButton.setVisibility(View.GONE);
            } else {
                if (skipButton.getVisibility() == View.GONE) {
                    MyAnimationBox.runFadeInAnimation(skipButton, 200);
                }
            }

            //control the buttons
            if (position == 0) { // slide 1

                // set back button
                backButton.setEnabled(false);
                backButton.setVisibility(View.INVISIBLE);
                backButton.setText("");
                // set next button
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setText("Next");

            } else if (position == OnBoardingSlideAdapter.slide_headings.length - 1) { // slide last

                // set back button
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                backButton.setText("BACK");
                // set next button
                nextButton.setEnabled(false);
                nextButton.setVisibility(View.INVISIBLE);
                nextButton.setText("");

            } else { // slide others

                // set back button
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                backButton.setText("BACK");
                // set next button
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void showSwipe() {
        MyAnimationBox.runFlickerAnimation(swipeTextView, 2000);
    }

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
        this.onBoardingSlideAdapter.getLaunchAppButton().setOnClickListener(view -> launchAppProcess());
    }

    private void setSkipButtonOnClickListener() {
        this.skipButton.setOnClickListener(view -> launchAppProcess());
    }

    private void launchAppProcess() {
        // save on boarding show status
        initializeSharedPreferenceProcess();
        spp.putOnBoardingIsFirstShow(false);

        Intent returnIntent = getIntent();
        setResult(MainActivity.ON_BOARDING_RESULT_OK, returnIntent);
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    private void initializeSharedPreferenceProcess() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppAuthentication.setAuthenticationAsNo(this);
    }
}
