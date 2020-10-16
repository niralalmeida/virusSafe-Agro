package com.example.virussafeagro;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.virussafeagro.adapters.QuestionSlideAdapter;
import com.example.virussafeagro.animation.DepthPageTransformer;
import com.example.virussafeagro.animation.ZoomOutPageTransformer;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;

import java.util.List;
import java.util.Objects;

public class QuizStartActivity extends AppCompatActivity {
    // data
    private VirusModel currentVirusModel;
    public static List<ChoiceQuestionModel> choiceQuestionModelFinalList; // 5 questions

    // views
    private MotionLayout containerMotionLayout;
    private LottieAnimationView countDownLottieAnimationView;
    private ViewPager2 questionViewPager2;

    // adapter
    private FragmentStateAdapter questionSlideAdapter;

    // tools
    public static final int NUM_PAGES = QuizActivity.QUESTION_COUNT;
    @DrawableRes
    public static int backgroundResourceId;
    public static int currentQuestionNo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModel = Objects.requireNonNull(getIntent().getExtras()).getParcelable("currentVirusModel");

        // initialize data
        this.initializeData();
        // initialize views
        this.initializeViews();

        // start count down
        this.showQuestion();
    }

    private void initializeData() {
        choiceQuestionModelFinalList = QuizActivity.choiceQuestionModelFinalList;
    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_start_activity);
        if (QuizActivity.currentPageName.equals(QuizActivity.BUTTON_NAME_BEGINNER)) {
            backgroundResourceId = R.color.btn_beginner_bg;
        } else if (QuizActivity.currentPageName.equals(QuizActivity.BUTTON_NAME_INTERMEDIATE)) {
            backgroundResourceId = R.color.btn_intermediate_bg;
        }
        this.containerMotionLayout.setBackgroundResource(backgroundResourceId);
        this.countDownLottieAnimationView = findViewById(R.id.lav_count_down_quiz_start);
        this.questionViewPager2 = findViewById(R.id.vp2_questions_quiz_start);
    }

    // control count down animation
    private void showQuestion() {
        new Handler().postDelayed(() ->{
            // set view pager and adapter
            questionSlideAdapter = new QuestionSlideAdapter(this);
            questionViewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
            ((RecyclerView)questionViewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);
            questionViewPager2.setAdapter(questionSlideAdapter);
            questionViewPager2.setUserInputEnabled(false); // deny swiping
            questionViewPager2.setPageTransformer(new ZoomOutPageTransformer());
            // set current page on change listener
            questionViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentQuestionNo = position + 1;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
            // hide lottie
            countDownLottieAnimationView.setVisibility(View.GONE);
            // set activity background
            this.containerMotionLayout.setBackgroundResource(R.color.colorPrimaryDarkBG);
        },3000);
    }

    public ViewPager2 getQuestionViewPager2() {
        return questionViewPager2;
    }
}
