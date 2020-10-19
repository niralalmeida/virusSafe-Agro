package com.example.virussafeagro;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.virussafeagro.adapters.QuestionSlideAdapter;
import com.example.virussafeagro.animation.ZoomOutPageTransformer;
import com.example.virussafeagro.fragments.QuizQuestionFragment;
import com.example.virussafeagro.models.ChoiceQuestionModel;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.viewModel.QuizActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class QuizStartActivity extends AppCompatActivity {
    private QuizStartActivity quizStartActivity = this;
    // data
    private VirusModel currentVirusModel;
    public static List<ChoiceQuestionModel> choiceQuestionModelFinalList; // 5 questions
    private QuizActivityViewModel quizActivityViewModel;

    // views
    private MotionLayout containerMotionLayout;
    private LottieAnimationView countDownLottieAnimationView;
    private LinearLayout quizTopProgressLinearLayout;
    private ViewPager2 questionViewPager2;

    // adapter
    private FragmentStateAdapter questionSlideAdapter;

    // tools
    public static final int NUM_PAGES = QuizActivity.QUESTION_COUNT;
    public static int currentQuestionNo = -1;
    private int[] quizResultArray; // 0 -> not reach; 1 -> right; 2 -> wrong
    public int rightCount;
    public int wrongCount;
    public int timeOutCount;
    public int quizTakingTime;
    public static boolean isQuizQuestionActivityClosed;

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
        // initialize VirusQuizQuestion ViewModel
        this.initializeVirusQuizQuestionViewModel();
        // initialize views
        this.initializeViews();

        // configure Top Quiz Progress LinearLayout
        this.configureTopQuizProgressLinearLayout();
        // start count down
        this.showQuestion();

        // set observer for observing VirusQuizQuestionWithImageURLArrayLD
        this.observeVirusQuizQuestionWithImageURLArrayLD();
    }

    public VirusModel getCurrentVirusModel() {
        return currentVirusModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isQuizQuestionActivityClosed = false;
    }

    private void initializeData() {
        choiceQuestionModelFinalList = QuizActivity.choiceQuestionModelFinalList;
        quizResultArray = new int[QuizActivity.QUESTION_COUNT];
    }

    private void initializeVirusQuizQuestionViewModel() {
        this.quizActivityViewModel = new ViewModelProvider(this).get(QuizActivityViewModel.class);
    }

    private void initializeViews() {
        this.containerMotionLayout = findViewById(R.id.ml_container_quiz_start_activity);
        this.countDownLottieAnimationView = findViewById(R.id.lav_count_down_quiz_start);
        this.quizTopProgressLinearLayout = findViewById(R.id.ll_quiz_progress_quiz_start);
        this.questionViewPager2 = findViewById(R.id.vp2_questions_quiz_start);
    }

    // configure Top Quiz Progress LinearLayout
    private void configureTopQuizProgressLinearLayout() {
        for (int i = 0; i < QuizActivity.QUESTION_COUNT; i++) {
            // add progress view
            View progressView = new View(this);
            quizTopProgressLinearLayout.addView(progressView);
            // set the attribute for the progressView
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) progressView.getLayoutParams();
            layoutParams.weight = 1;
            layoutParams.width = 0;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            progressView.setLayoutParams(layoutParams);

            // add vertical line view
            if (i != QuizActivity.QUESTION_COUNT - 1) {
                View verticalLineView = new View(this);
                quizTopProgressLinearLayout.addView(verticalLineView);
                // set the attribute for the verticalLineView
                LinearLayout.LayoutParams layoutParamsForLine = (LinearLayout.LayoutParams) verticalLineView.getLayoutParams();
                layoutParamsForLine.width = 3;
                layoutParamsForLine.height = ViewGroup.LayoutParams.MATCH_PARENT;
                verticalLineView.setLayoutParams(layoutParamsForLine);
                verticalLineView.setBackgroundResource(R.color.colorWhite);
            }
        }
    }

    // control count down animation
    public void showQuestion() {
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
//            this.containerMotionLayout.setBackgroundResource(R.color.colorPrimaryDarkBG);
        },3000);
    }

    public ViewPager2 getQuestionViewPager2() {
        return questionViewPager2;
    }

    public int[] getQuizResultArray() {
        return quizResultArray;
    }

    public LinearLayout getQuizTopProgressLinearLayout() {
        return quizTopProgressLinearLayout;
    }

    public MotionLayout getContainerMotionLayout() {
        return containerMotionLayout;
    }

    public void closeOnClick(View v) {
        // animation
        isQuizQuestionActivityClosed = true;
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_slide_out_top);
    }

    // set observer for observing VirusQuizQuestionWithImageURLArrayLD
    private void observeVirusQuizQuestionWithImageURLArrayLD() {
        this.quizActivityViewModel.getQuizQuestionModelListWithImageURLLD().observe(this, resultQuizQuestionWithImageURLModelList -> {
            QuizActivity.isImageURLGet = true;

            if (questionViewPager2.getAdapter() != null){
                int currentFragmentPosition = questionViewPager2.getCurrentItem();
                if (getSupportFragmentManager().findFragmentByTag("f" + currentFragmentPosition) instanceof QuizQuestionFragment) {
                    QuizQuestionFragment currentFragment = (QuizQuestionFragment)getSupportFragmentManager().findFragmentByTag("f" + currentFragmentPosition);
                    Picasso.get()
                            .load(currentFragment.getCurrentChoiceQuestionModel().getImageURLList().get(0))
                            .placeholder(R.drawable.default_tomato)
                            .resize(DataConverter.dip2px(quizStartActivity, 600), DataConverter.dip2px(quizStartActivity, 300))
                            .centerCrop()
                            .into(currentFragment.getQuestionImageView());
                }
            }
        });
    }
}
