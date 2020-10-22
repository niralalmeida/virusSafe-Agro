package com.example.virussafeagro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.virussafeagro.fragments.AboutAppFragment;
import com.example.virussafeagro.fragments.ContactUsFragment;
import com.example.virussafeagro.fragments.ControlStrategiesFragment;
import com.example.virussafeagro.fragments.DisclaimerFragment;
import com.example.virussafeagro.fragments.FactorsFragment;
import com.example.virussafeagro.fragments.InsightsFragment;
import com.example.virussafeagro.fragments.NewsDetailFragment;
import com.example.virussafeagro.fragments.NewsFragment;
import com.example.virussafeagro.fragments.NutrientDetailFragment;
import com.example.virussafeagro.fragments.NutrientFragment;
import com.example.virussafeagro.fragments.PesticideStoreMapFragment;
import com.example.virussafeagro.fragments.ToolkitFragment;
import com.example.virussafeagro.fragments.LearnFragment;
import com.example.virussafeagro.fragments.MoreFragment;
import com.example.virussafeagro.fragments.TweetFragment;
import com.example.virussafeagro.fragments.UpdatesFragment;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.fragments.VirusCheckResultFragment;
import com.example.virussafeagro.fragments.VirusDetailFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.fragments.VirusQuizQuestionFragment;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.KeyboardToggleUtils;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivity mainActivity = this;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private boolean isFromPasswordActivity;
    private boolean isFromOnBoardingActivity;
    private SharedPreferenceProcess spp;

    // data
    public static List<VirusModel> virusModelInfoList;

    // toolbar
    private Toolbar toolbar;
    private RelativeLayout toolbarAllViewsRelativeLayout;
    // toolbar - back button
    private RelativeLayout backButtonRelativeLayout;
    // toolbar - title
    private TextView titleTextView;
    // toolbar - search area
    private RelativeLayout allSearchViewRelativeLayout;
    private RelativeLayout searchButtonRelativeLayout;
    private ImageView searchImageView;
    private com.example.virussafeagro.uitilities.ExtendedEditText doSearchEditText;
    private RelativeLayout closeSearchButtonRelativeLayout; // for button
    // toolbar - buttons
    private LinearLayout topButtonsLinearLayout;
    // toolbar - tip / more
    private RelativeLayout tipRelativeLayout;
    private RelativeLayout moreRelativeLayout;
    private ImageView moreImageView;
    // toolbar - quiz
    private Button quizButton;
//    private View lineView2;
//    private View lineView3;
    
    // app tips
    private DragYRelativeLayout tipDragYRelativeLayout;
    // all tips views
    private LinearLayout homeLinearLayout;
    private LinearLayout learnLinearLayout;
    private LinearLayout virusListLinearLayout;
    private LinearLayout nutrientLinearLayout;
    private LinearLayout tomatoGrowTipLinearLayout;
    private LinearLayout toolkitLinearLayout;
    private LinearLayout controlLinearLayout;
    private LinearLayout factorLinearLayout;
    private LinearLayout insightLinearLayout;
    private LinearLayout mapLinearLayout;

    // app lottie animation
    private RelativeLayout animationImageRelativeLayout;

    // bottom bar
    private FloatingActionButton floatingActionButton;
    // bottom bar - custom
    private LinearLayout bottomBarLinearLayout;
    private RelativeLayout learnRelativeLayout; // learn
    private ImageView learnImageView;
    private TextView learnTextView;
    private RelativeLayout toolkitRelativeLayout; // toolkit
    private ImageView toolkitImageView;
    private TextView toolkitTextView;
    private boolean isLearnIconClicked;
    private boolean isToolkitIconClicked;

    public static boolean FROM_VIRUS_INFO_PAGE;
    public static boolean FROM_NUTRIENT_PAGE;

    public static int TOOLBAR_BACK_BUTTON_WIDTH;
    public static int TOOLBAR_SEARCH_BUTTON_WIDTH;
    public static int TOOLBAR_SEARCH_CLOSE_BUTTON_WIDTH;
    public static int TOOLBAR_QUIZ_TIP_MORE_BUTTONS_WIDTH;
    public static int TOOLBAR_TIP_MORE_BUTTONS_LOCATION_LEFT;
    public static int TOOLBAR_TIP_MORE_BUTTONS_LOCATION_RIGHT;

    // request and result code
    public static final int PASSWORD_REQUEST_CODE = 9;
    public static final int PASSWORD_RESULT_OK = 24;

    public static final int ON_BOARDING_REQUEST_CODE = 6;
    public static final int ON_BOARDING_RESULT_OK = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_main);

        // set app password
        AppAuthentication.setAppPassword(this);

        // initialize virus list
        virusModelInfoList = new ArrayList<>();
        // initialize Views
        this.initializeViews();
        // initialize SharedPreferenceProcess
        this.initializeSharedPreferenceProcess();

        // check whether OnBoardingActivity is first show
        if (this.spp.getOnBoardingIsFirstShow()) {
            // show OnBoarding Screen
            this.showOnBoardingScreen();
        }
    }

    private void initializeViews() {
        // tips
        this.homeLinearLayout = findViewById(R.id.ll_home_tip);
        this.learnLinearLayout = findViewById(R.id.ll_learn_tip);
        this.virusListLinearLayout = findViewById(R.id.ll_virus_list_tip);
        this.nutrientLinearLayout = findViewById(R.id.ll_nutrient_tip);
        this.tomatoGrowTipLinearLayout = findViewById(R.id.ll_tomato_tip_list_tip);
        this.toolkitLinearLayout = findViewById(R.id.ll_toolkit_tip);
        this.controlLinearLayout = findViewById(R.id.ll_control_strategies_tip);
        this.factorLinearLayout = findViewById(R.id.ll_factors_tip);
        this.insightLinearLayout = findViewById(R.id.ll_insight_tip);
        this.mapLinearLayout = findViewById(R.id.ll_map_tip);
        // initialize background image
        this.toolbar = findViewById(R.id.toolbar);
        this.toolbarAllViewsRelativeLayout = findViewById(R.id.rl_all_views_toolbar);
        this.backButtonRelativeLayout = findViewById(R.id.rl_back_button_toolbar);
        this.titleTextView = findViewById(R.id.tv_title_toolbar);
        this.allSearchViewRelativeLayout = findViewById(R.id.rl_all_search_views_toolbar);
        this.searchButtonRelativeLayout = findViewById(R.id.rl_search_button_toolbar);
        this.searchImageView = findViewById(R.id.img_search_button_toolbar);
        this.doSearchEditText = findViewById(R.id.et_do_search_toolbar);
        this.closeSearchButtonRelativeLayout = findViewById(R.id.rl_close_btn_search_toolbar);
        this.topButtonsLinearLayout = findViewById(R.id.ll_buttons_toolbar);
        this.tipRelativeLayout = findViewById(R.id.rl_tip_toolbar);
        this.moreRelativeLayout = findViewById(R.id.rl_more_toolbar);
        this.moreImageView = findViewById(R.id.img_more_toolbar);
        this.quizButton = findViewById(R.id.btn_quiz_toolbar);
        this.floatingActionButton = findViewById(R.id.fab);
        this.tipDragYRelativeLayout = findViewById(R.id.drl_tip_app);
        this.animationImageRelativeLayout = findViewById(R.id.rl_animation_image_main);
        // bottom bar
        this.bottomBarLinearLayout = findViewById(R.id.ll_bottom_bar);
        this.learnRelativeLayout = findViewById(R.id.rl_learn_bottom_bar);
        this.learnImageView = findViewById(R.id.img_learn_bottom_bar);
        this.learnTextView = findViewById(R.id.tv_learn_bottom_bar);
        this.toolkitRelativeLayout = findViewById(R.id.rl_toolkit_bottom_bar);
        this.toolkitImageView = findViewById(R.id.img_toolkit_bottom_bar);
        this.toolkitTextView = findViewById(R.id.tv_toolkit_bottom_bar);

        TOOLBAR_BACK_BUTTON_WIDTH = DataConverter.dip2px(this, 40);
        TOOLBAR_SEARCH_BUTTON_WIDTH = DataConverter.dip2px(this, 55);
        TOOLBAR_SEARCH_CLOSE_BUTTON_WIDTH = DataConverter.dip2px(this, 50);
        TOOLBAR_QUIZ_TIP_MORE_BUTTONS_WIDTH = DataConverter.dip2px(this, 150);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        TOOLBAR_TIP_MORE_BUTTONS_LOCATION_RIGHT = width - TOOLBAR_QUIZ_TIP_MORE_BUTTONS_WIDTH;
        TOOLBAR_TIP_MORE_BUTTONS_LOCATION_LEFT = width - TOOLBAR_SEARCH_BUTTON_WIDTH - TOOLBAR_QUIZ_TIP_MORE_BUTTONS_WIDTH;
    }

    private void initializeSharedPreferenceProcess() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(this);
    }

    private void showOnBoardingScreen() {
        Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
        intent.putExtra("whereFrom", "main");
        mainActivity.startActivityForResult(intent, MainActivity.ON_BOARDING_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.displayAllMainActivityViews();

        // check authentication
        this.checkAuthentication();
    }

////////////////////////////////////////////////// -- Pass word -- /////////////////////////////////////////////////////
    private void checkAuthentication() {
//        if ((!this.isFromPasswordActivity) && (!this.isFromOnBoardingActivity) && (!this.spp.getOnBoardingIsFirstShow())) {
//            // set authentication as "no"
//            AppAuthentication.setAuthenticationAsNo(this);
//            // check the authentication --> show the PasswordActivity
//            new Handler().postDelayed(() -> AppAuthentication.checkAuthentication(mainActivity), 600);
//        }
    }

    private void displayAllMainActivityViews() {
        // tip
        this.showTipByPage(AppResources.ACTIVITY_TAG_HOME);
        // configure toolbar
        this.configureToolbar();
        // show or not top action bar (back button + title)
        showTopBarBackButton(this);
        // initialize bottom navigation bar
        this.initializeMyBottomBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PASSWORD_REQUEST_CODE) {
            if (resultCode == PASSWORD_RESULT_OK) { // from password activity
                isFromPasswordActivity = true;

            }
        }
        if (requestCode == ON_BOARDING_REQUEST_CODE) { // from on boarding activity
            if (resultCode == ON_BOARDING_RESULT_OK) {
                isFromOnBoardingActivity = true;
            }
        }
    }

    // add toolbar
    private void configureToolbar() {
        setSupportActionBar(this.toolbar);
        // tip
        this.tipDragYRelativeLayout.setMainActivity(this);
        this.setTipOnClickListener();
        // more
        this.setMoreOnClickListener();
        // back button
        this.setBackButtonOnClickListener();
    }

    private void setTipOnClickListener() {
        tipRelativeLayout.setOnClickListener(v -> {
            // set the button style
            setTipButton(!v.isActivated());
            // show or hide the tip tile
            showTopTip();
        });
    }

    private void showTopTip() {
        if (tipDragYRelativeLayout.getVisibility() == View.GONE) {
            MyAnimationBox.runSlideInAnimationFromTop(tipDragYRelativeLayout, 300);
        } else {
            MyAnimationBox.runSlideOutAnimationToTop(tipDragYRelativeLayout, 300);
        }
    }

    private void setMoreOnClickListener() {
        moreRelativeLayout.setOnClickListener(v -> {
            Fragment foundFragment = fragmentManager.findFragmentById(R.id.fl_fragments);
            if ((foundFragment instanceof MoreFragment) ||
                (foundFragment instanceof AboutAppFragment) ||
                (foundFragment instanceof UpdatesFragment) ||
                (foundFragment instanceof DisclaimerFragment) ||
                (foundFragment instanceof ContactUsFragment)
                ){
                FragmentOperator.backToLastFragment(this);

            }
            else {
                // add new MoreFragment
                new Handler().postDelayed(() -> {
                    FragmentOperator.replaceFragmentWithSlideFromTopAnimation(this, new MoreFragment(), AppResources.FRAGMENT_TAG_MORE);
                }, 200);
            }
        });
    }

    public void setTipButton(boolean isPress) {
        if (isPress) {
            // set activated
            tipRelativeLayout.setActivated(true);
            // change bg
            tipRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorDarkForeground));
        } else {
            tipRelativeLayout.setActivated(false);
            tipRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorPrimaryDarkTheme));
        }
    }

    public void setMoreButton(boolean toPress) {
        if (toPress) {
            // set activated
            moreRelativeLayout.setActivated(true);
            // change bg
            moreRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorPrimaryDarkBG));
            // change image tint color
//            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.item_checked);
//            moreImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
//            moreImageView.setImageTintList(colorStateList);
        } else {
            moreRelativeLayout.setActivated(false);
            // change bg
            moreRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorPrimaryDarkTheme));
            // change image tint color
//            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorWhite);
//            moreImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
//            moreImageView.setImageTintList(colorStateList);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public com.example.virussafeagro.uitilities.ExtendedEditText getDoSearchEditText() {
        return doSearchEditText;
    }

    public Button getQuizButton() {
        return quizButton;
    }

    public void hideTheLottieAnimationView() {
        this.animationImageRelativeLayout.setVisibility(View.GONE);
    }

    // display search function
    public void displaySearch() {
        // show search button
        this.moveForOnlyShowSearchIcon(); // move search button
        // move tip and more + show line 3
        this.moveTipAndMoreToLeft(200);

        new Handler().postDelayed(()->{
            // show search button
            this.setSearchButtonVisible(true, true, 200);
            // set search button on click listener
            this.setSearchOnClickListener();
            // set close search button on click listener
            this.setCloseSearchOnClickListener();
        }, 200);

    }

    // close search function
    public void closeSearch() {
        // set GONE to all search views
        mainActivity.setAllSearchViewLinearLayoutVisibility(View.GONE);
        // hide search area
        // clear the edit text content
        doSearchEditText.clearTextChangedListeners();
        doSearchEditText.setText("");
        // hide keyboard
        KeyboardToggleUtils.hideKeyboard(mainActivity);
    }

    // only show search button (hide edit and close button )
    public void moveForOnlyShowSearchIcon() {
        // change the search icon style
        searchButtonRelativeLayout.setBackgroundResource(R.drawable.ripple_btn_open_search_toolbar);

        // change search button image tint color
        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryTitle);
        searchImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
        searchImageView.setImageTintList(colorStateList);

        // hide EditText and close button
        this.allSearchViewRelativeLayout.setX(this.toolbarAllViewsRelativeLayout.getWidth() - TOOLBAR_SEARCH_BUTTON_WIDTH);
    }

    // move the "tip" and "more" buttons to left
    public void moveTipAndMoreToLeft(int duration) {
        // move Tip And More
        MyAnimationBox.runSlideInAnimationFromRight(
                this.topButtonsLinearLayout,
                this.topButtonsLinearLayout.getX(),
                TOOLBAR_TIP_MORE_BUTTONS_LOCATION_LEFT,//- this.topButtonsLinearLayout.getWidth()
                duration);
    }

    // move the "tip" and "more" buttons to right
    public void moveTipAndMoreToRight(String fragmentTag, int duration) {
        if (FROM_VIRUS_INFO_PAGE || FROM_NUTRIENT_PAGE) {
            FROM_VIRUS_INFO_PAGE = false;
            FROM_NUTRIENT_PAGE = false;
            MyAnimationBox.runSlideInAnimationFromRight(
                    this.topButtonsLinearLayout,
                    this.topButtonsLinearLayout.getX(),
                    TOOLBAR_TIP_MORE_BUTTONS_LOCATION_RIGHT,
                    duration);
//            if (AppResources.FRAGMENT_TAG_VIRUS_DETAIL.equals(fragmentTag)) {
//                MyAnimationBox.runSlideInAnimationFromRight(
//                        this.topButtonsLinearLayout,
//                        this.topButtonsLinearLayout.getX(),
//                        this.toolbarAllViewsRelativeLayout.getWidth() - this.topButtonsLinearLayout.getWidth(),
//                        duration);
//            } else {// hide search button -> move to right
//                MyAnimationBox.runSlideInAnimationFromRight(
//                        this.topButtonsLinearLayout,
//                        this.topButtonsLinearLayout.getX(),
//                        this.toolbarAllViewsRelativeLayout.getWidth() - TOOLBAR_TIP_MORE_BUTTONS_WIDTH - DataConverter.dip2px(mainActivity,10),
//                        duration);
//            }
        }
    }

    // set search button visible
    public void setSearchButtonVisible(boolean showOrHide, boolean withFadeAnimation, int duration) {
        if (showOrHide) {
            if (withFadeAnimation) {
                MyAnimationBox.runFadeInAnimation(this.allSearchViewRelativeLayout, duration);
            } else {
                this.allSearchViewRelativeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (withFadeAnimation) {
                MyAnimationBox.runFadeOutAnimation(this.allSearchViewRelativeLayout, duration);
            } else {
                this.allSearchViewRelativeLayout.setVisibility(View.GONE);
            }
        }
    }

    // set search button on click listener
    public void setSearchOnClickListener() {
        this.searchButtonRelativeLayout.setOnClickListener(v -> {
            if (this.allSearchViewRelativeLayout.getX() != TOOLBAR_BACK_BUTTON_WIDTH) {
                showSearchArea(200);
            }
        });
    }

    // show search area
    public void showSearchArea(int duration) {
        // change the search icon style (green icon)
        searchButtonRelativeLayout.setBackgroundResource(R.drawable.ripple_btn_search_toolbar);
        // change image tint color
        ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimarySearchHint);
        searchImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
        searchImageView.setImageTintList(colorStateList);

        // show search area
        MyAnimationBox.runSlideInAnimationFromRight(
                this.allSearchViewRelativeLayout,
                this.allSearchViewRelativeLayout.getX(),
                TOOLBAR_BACK_BUTTON_WIDTH,
                duration);
    }

    // set close search button on click listener
    public void setCloseSearchOnClickListener() {
        this.closeSearchButtonRelativeLayout.setOnClickListener(v -> {
            hideSearchArea(200);
        });
    }

    // hide search area
    public void hideSearchArea(int duration) {
        // hide keyboard
        KeyboardToggleUtils.hideKeyboard(mainActivity);
        // clear the edit text content
        doSearchEditText.setText("");
        // hide search area
        MyAnimationBox.runSlideInAnimationFromRight(
                allSearchViewRelativeLayout,
                allSearchViewRelativeLayout.getX(),
                toolbarAllViewsRelativeLayout.getWidth() - TOOLBAR_SEARCH_BUTTON_WIDTH,
                duration);
        // change the search icon style (white icon)
        new Handler().postDelayed(() ->{
            searchButtonRelativeLayout.setBackgroundResource(R.drawable.ripple_btn_open_search_toolbar);
            // change image tint color
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryTitle);
            searchImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            searchImageView.setImageTintList(colorStateList);
        }, duration);
    }

    public void setAllSearchViewLinearLayoutVisibility(int visibility) {
        allSearchViewRelativeLayout.setVisibility(visibility);
    }

    // show or not top bar back button
    public static void showTopBarBackButton(MainActivity mainActivity) {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment currentVisibleFragment = fragmentManager.findFragmentById(R.id.fl_fragments);

        boolean isToolkitFragment = currentVisibleFragment instanceof ToolkitFragment;
        boolean isLearnFragment = currentVisibleFragment instanceof LearnFragment;
        boolean isVirusCheckResultFragment = currentVisibleFragment instanceof VirusCheckResultFragment;
        boolean isMoreFragment = currentVisibleFragment instanceof MoreFragment;
        if ((currentVisibleFragment == null)
            || isToolkitFragment
            || isLearnFragment
            || isVirusCheckResultFragment
            || isMoreFragment ){
//            Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
//            Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(false);

            // hide back button
            if (mainActivity.backButtonRelativeLayout.getVisibility() != View.GONE){
                MyAnimationBox.runFadeOutAnimation(mainActivity.backButtonRelativeLayout, 200);
            }

        } else {
            // show back button
            if (mainActivity.backButtonRelativeLayout.getVisibility() == View.GONE) {
                MyAnimationBox.runFadeInAnimation(mainActivity.backButtonRelativeLayout, 200);
            }
        }
    }

    // se on click listener for back button in toolbar
    private void setBackButtonOnClickListener() {
        backButtonRelativeLayout.setOnClickListener(v -> {
            int fragmentCount = fragmentManager.getBackStackEntryCount();
            if (fragmentCount > 0) {
                FragmentOperator.backToLastFragment(this);
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        FragmentOperator.backToLastFragment(this);
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//        if (count == 0) {
//            super.onBackPressed();
//        } else {
//            FragmentOperator.backToLastFragment(this);
//        }
//    }

    private void initializeMyBottomBar() {
        this.setBottomNavigationViewExItemOnSelectedListener();
    }

    // listeners for BottomNavigationViewEx and floatingActionButton
    private void setBottomNavigationViewExItemOnSelectedListener() {

        learnRelativeLayout.setOnClickListener(v -> {
            // get the current fragment
            Fragment foundFragment = fragmentManager.findFragmentById(R.id.fl_fragments);
            // hide the main animation
            hideTheLottieAnimationView();
            // check current fragment
            if (foundFragment instanceof LearnFragment) { // is learn --> hide "learn" show main page

                // hide the fragment
                FragmentOperator.popAllFragmentsInStack(fragmentManager);
                // set title
                titleTextView.setText(R.string.app_name);
                // show the animation
                new Handler().postDelayed(() -> MyAnimationBox.runFadeInAnimation(animationImageRelativeLayout, 700), 200);

            }
            else if( // is sub-fragment of "learn" --> hide all fragment in stack to right, show "learn"
                        (foundFragment instanceof VirusInfoListFragment) ||
                        (foundFragment instanceof VirusDetailFragment) ||
                        (foundFragment instanceof VirusQuizQuestionFragment) ||
                        (foundFragment instanceof NutrientFragment) ||
                        (foundFragment instanceof NutrientDetailFragment)
                    ){

                // pop All Fragments In Stack
                FragmentOperator.popAllFragmentsInStackExceptLearnToolkit(fragmentManager);

            }
            else if (foundFragment instanceof MoreFragment) { // is more fragment --> hide more fragment, show "learn"

                // hide more fragment
                FragmentOperator.backToLastFragment(mainActivity);
                // remove all fragment in stack
                FragmentOperator.popAllFragmentsInStack(fragmentManager);
                new Handler().postDelayed(() -> {
                    // show the learn fragment
                    FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(mainActivity, new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
                }, 200);
            }
            else {
                // hide the fragment
                FragmentOperator.popAllFragmentsInStack(fragmentManager);
                new Handler().postDelayed(() -> {
                    // show learn fragment
                    FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(mainActivity, new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
                }, 200);
            }
        });

        toolkitRelativeLayout.setOnClickListener(v -> {
            // get the current fragment
            Fragment foundFragment = fragmentManager.findFragmentById(R.id.fl_fragments);
            // hide the main animation
            hideTheLottieAnimationView();
            // check current fragment
            if (foundFragment instanceof ToolkitFragment) {
                // hide the fragment
                FragmentOperator.popAllFragmentsInStack(fragmentManager);

                // set title
                titleTextView.setText(R.string.app_name);
                // show the animation
                new Handler().postDelayed(() -> MyAnimationBox.runFadeInAnimation(animationImageRelativeLayout, 700), 200);

            } else if( // is sub-fragment of "toolkit" --> hide all fragment in stack to right, show "toolkit"
                        (foundFragment instanceof ControlStrategiesFragment) ||
                        (foundFragment instanceof PesticideStoreMapFragment) ||
                        (foundFragment instanceof FactorsFragment) ||
                        (foundFragment instanceof InsightsFragment) ||
                        (foundFragment instanceof NewsFragment) ||
                        (foundFragment instanceof NewsDetailFragment) ||
                        (foundFragment instanceof TweetFragment)
                    ){

                // pop All Fragments In Stack
                FragmentOperator.popAllFragmentsInStackExceptLearnToolkit(fragmentManager);

            }
            else if (foundFragment instanceof MoreFragment) { // is more fragment --> hide more fragment, show "toolkit"

                // hide more fragment
                FragmentOperator.backToLastFragment(mainActivity);
                // remove all fragment in stack
                FragmentOperator.popAllFragmentsInStack(fragmentManager);
                new Handler().postDelayed(() -> {
                    // show the learn fragment
                    FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(mainActivity, new ToolkitFragment(), AppResources.FRAGMENT_TAG_TOOLKIT);
                }, 200);
            }
            else {
                // hide the fragment
                FragmentOperator.popAllFragmentsInStack(fragmentManager);
                new Handler().postDelayed(() -> {
                    FragmentOperator.replaceFragmentWithSlideFromBottomAnimation(mainActivity, new ToolkitFragment(), AppResources.FRAGMENT_TAG_TOOLKIT);
                }, 200);
            }
        });

        floatingActionButton.setOnClickListener(v -> {
//            if (fragmentManager.findFragmentByTag(AppResources.FRAGMENT_TAG_VIRUS_CHECK) == null
//                || (!(fragmentManager.findFragmentById(R.id.fl_fragments) instanceof VirusCheckFragment))) {
//                showVirusCheckFragment();
//                if (toolbar.getVisibility() == View.GONE) {
//                    new Handler().postDelayed(() -> MyAnimationBox.runFadeInAnimation(toolbar, 500), 550);
//                }
//            }

            if (fragmentManager.findFragmentByTag(AppResources.FRAGMENT_TAG_VIRUS_CHECK) == null
                || (!(fragmentManager.findFragmentById(R.id.fl_fragments) instanceof VirusCheckFragment))) {
                showVirusCheckFragment();
            }
        });
    }

    public boolean isLearnIconClicked() {
        return isLearnIconClicked;
    }

    public boolean isToolkitIconClicked() {
        return isToolkitIconClicked;
    }

    // set the learn button style
    public void setLearnButton(boolean toPressed) {
        if (toPressed){ // set clicked
            isLearnIconClicked = true;
            // change image tint color
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.item_checked);
            learnImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            learnImageView.setImageTintList(colorStateList);
            // change the text color
            learnTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.item_checked));
        } else { // set not clicked
            isLearnIconClicked = false;
            // change image tint color
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.item_not_checked);
            learnImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            learnImageView.setImageTintList(colorStateList);
            // change the text color
            learnTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.item_not_checked));
        }
    }

    // set the toolkit button style
    public void setToolkitButton(boolean toPressed) {
        if (toPressed){ // set clicked
            isToolkitIconClicked = true;
            // change image tint color
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.item_checked);
            toolkitImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            toolkitImageView.setImageTintList(colorStateList);
            // change the text color
            toolkitTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.item_checked));
        } else { // set not clicked
            isToolkitIconClicked = false;
            // change image tint color
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.item_not_checked);
            toolkitImageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
            toolkitImageView.setImageTintList(colorStateList);
            // change the text color
            toolkitTextView.setTextColor(this.mainActivity.getResources().getColor(R.color.item_not_checked));
        }
    }

    public void showVirusCheckFragment() {
//        VirusCheckFragment virusCheckFragment = new VirusCheckFragment();
//        virusCheckFragment.show(getSupportFragmentManager(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);


    }

    public void showTipByPage(String fragmentTag) {
        // hide all pages
        this.hideAllTipViews();
        switch (fragmentTag){
            case AppResources.ACTIVITY_TAG_HOME:
            case AppResources.FRAGMENT_TAG_MORE:
                this.homeLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_LEARN:
                this.learnLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_VIRUS_INFO:
                this.virusListLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_NUTRIENT:
                this.nutrientLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_TOMATO_GROWING_TIP:
                this.tomatoGrowTipLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_TOOLKIT:
                this.toolkitLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_CONTROL_STRATEGIES:
                this.controlLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_FACTORS:
                this.factorLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_INSIGHTS:
                this.insightLinearLayout.setVisibility(View.VISIBLE);
                break;
            case AppResources.FRAGMENT_TAG_PESTICIDE_STORES:
                this.mapLinearLayout.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void hideAllTipViews() {
        // tips
        this.homeLinearLayout.setVisibility(View.GONE);
        this.learnLinearLayout.setVisibility(View.GONE);
        this.virusListLinearLayout.setVisibility(View.GONE);
        this.nutrientLinearLayout.setVisibility(View.GONE);
        this.tomatoGrowTipLinearLayout.setVisibility(View.GONE);
        this.toolkitLinearLayout.setVisibility(View.GONE);
        this.controlLinearLayout.setVisibility(View.GONE);
        this.factorLinearLayout.setVisibility(View.GONE);
        this.insightLinearLayout.setVisibility(View.GONE);
        this.mapLinearLayout.setVisibility(View.GONE);
    }
}
