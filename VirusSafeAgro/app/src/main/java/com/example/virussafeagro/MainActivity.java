package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.virussafeagro.fragments.CalculatorFragment;
import com.example.virussafeagro.fragments.ToolkitFragment;
import com.example.virussafeagro.fragments.LearnFragment;
import com.example.virussafeagro.fragments.MoreFragment;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.KeyboardToggleUtils;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private MainActivity mainActivity = this;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private boolean isFromPasswordActivity;
    private boolean isFromOnBoardingActivity;
    private SharedPreferenceProcess spp;

    // toolbar
    private Toolbar toolbar;
    private RelativeLayout toolbarAllViewsRelativeLayout;
    // toolbar - title
    private LinearLayout titleLinearLayout;
    private TextView titleTextView;
    // toolbar - search area
    private LinearLayout allSearchViewLinearLayout;
    private LinearLayout searchLinearLayout;
    private ImageView searchImageView;
    private com.example.virussafeagro.uitilities.ExtendedEditText doSearchEditText;
    private LinearLayout closeSearchLinearLayout; // for button
    // toolbar - buttons
    private LinearLayout topButtonsLinearLayout;
    // toolbar - calculator / more
    private RelativeLayout calculatorRelativeLayout;
    private RelativeLayout moreRelativeLayout;
    private View lineView1;
    // toolbar - quiz
    private RelativeLayout quizRelativeLayout;
    private View lineView2;
    // bottom bar
//    private BottomNavigationView bottomNavigationView;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private FloatingActionButton floatingActionButton;
    // swipe up image
    private ImageView swipeImageView;
    private DragYRelativeLayout swipeImageDragYRelativeLayout;

    public static int INITIAL_PAGE_POSITION = 3; // empty -> check fragment

    public static int TOOLBAR_WIDTH;
    public static int TOOLBAR_SEARCH_BUTTON;
    public static int TOOLBAR_SEARCH_EDIT_AND_CLOSE;
    public static boolean FROM_VIRUS_INFO_PAGE;

    public static final int PASSWORD_REQUEST_CODE = 9;
    public static final int PASSWORD_RESULT_OK = 24;

    public static final int ON_BOARDING_REQUEST_CODE = 6;
    public static final int ON_BOARDING_RESULT_OK = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set app password
        AppAuthentication.setAppPassword(this);

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
        // initialize background image
        this.toolbar = findViewById(R.id.toolbar);
        this.toolbarAllViewsRelativeLayout = findViewById(R.id.rl_all_views_toolbar);
        this.titleLinearLayout = findViewById(R.id.ll_title_toolbar);
        this.titleTextView = findViewById(R.id.tv_title_toolbar);
        this.allSearchViewLinearLayout = findViewById(R.id.ll_all_search_views_toolbar);
        this.searchLinearLayout = findViewById(R.id.ll_search_toolbar);
        this.searchImageView = findViewById(R.id.img_search_toolbar);
        this.doSearchEditText = findViewById(R.id.et_do_search_toolbar);
        this.closeSearchLinearLayout = findViewById(R.id.ll_close_btn_search_toolbar);
        this.topButtonsLinearLayout = findViewById(R.id.ll_buttons_toolbar);
        this.calculatorRelativeLayout = findViewById(R.id.rl_calculator_toolbar);
        this.moreRelativeLayout = findViewById(R.id.rl_more_toolbar);
        this.lineView1 = findViewById(R.id.v_line1_vertical_toolbar);
        this.lineView2 = findViewById(R.id.v_line2_vertical_toolbar);
        this.quizRelativeLayout = findViewById(R.id.rl_quiz_toolbar);
//        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        this.floatingActionButton = findViewById(R.id.fab);
        this.swipeImageView = findViewById(R.id.img_swipe_app);
        this.swipeImageDragYRelativeLayout = findViewById(R.id.drl_image_app);
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
        if ((!this.isFromPasswordActivity) && (!this.isFromOnBoardingActivity) && (!this.spp.getOnBoardingIsFirstShow())){
            // set authentication as "no"
            AppAuthentication.setAuthenticationAsNo(this);
            // check the authentication --> show the PasswordActivity
            new Handler().postDelayed(() -> AppAuthentication.checkAuthentication(mainActivity),600);
        }
    }

    private void displayAllMainActivityViews() {
        // configure toolbar
        this.configureToolbar();
        // show or not top action bar (back button + title)
        showTopBarBackButton(this);
        // initialize bottom navigation bar
        this.initializeBottomNavigationView();
        // set swipe up animation
        this.setSwipeUpAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PASSWORD_REQUEST_CODE){
            if (resultCode == PASSWORD_RESULT_OK) { // from password activity
                isFromPasswordActivity = true;
                this.bottomNavigationViewEx.setCurrentItem(INITIAL_PAGE_POSITION);
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
            }
        }
        if(requestCode == ON_BOARDING_REQUEST_CODE){ // from on boarding activity
            if (resultCode == ON_BOARDING_RESULT_OK) {
                isFromOnBoardingActivity = true;
                this.bottomNavigationViewEx.setCurrentItem(INITIAL_PAGE_POSITION);
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
            }
        }
    }

    private void setSwipeUpAnimation() {
        new Handler().postDelayed(() -> {
            MyAnimationBox.runRepeatedAnimationBottomToTop(swipeImageView, 1000);
        }, 1000);
    }

    // add toolbar
    private void configureToolbar() {
        setSupportActionBar(this.toolbar);
        // calculator
        this.setCalculatorOnClickListener();
        // more
        this.setMoreOnClickListener();
    }

    private void setCalculatorOnClickListener() {
        calculatorRelativeLayout.setOnClickListener(v -> {
            // add new CalculatorFragment
            FragmentOperator.replaceFragment(this, new CalculatorFragment(), AppResources.FRAGMENT_TAG_WATER_CALCULATOR);
        });
    }

    private void setMoreOnClickListener() {
        moreRelativeLayout.setOnClickListener(v -> {
            // add new MoreFragment
            FragmentOperator.replaceFragment(this, new MoreFragment(), AppResources.FRAGMENT_TAG_MORE);
        });
    }

    public void setVirusCheckButton(boolean isPress) {
        if (isPress) {
            bottomNavigationViewEx.setCurrentItem(3);
            // change bg
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryTile);
            floatingActionButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            floatingActionButton.setBackgroundTintList(colorStateList);
        } else {
            // change bg
            ColorStateList colorStateList = ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryLight);
            floatingActionButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            floatingActionButton.setBackgroundTintList(colorStateList);
        }
    }

    public void setCalculatorButton(boolean isPress) {
        if (isPress) {
            // select none item of menu
            bottomNavigationViewEx.setCurrentItem(3);
            // set activated
            calculatorRelativeLayout.setActivated(true);
            // change bg
            calculatorRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorBlack));
            // hide vertical line
            lineView2.setVisibility(View.INVISIBLE);
        } else {
            calculatorRelativeLayout.setActivated(false);
            calculatorRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorPrimaryDark));
            if ((!calculatorRelativeLayout.isActivated()) && (!moreRelativeLayout.isActivated())){
                lineView2.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setMoreButton(boolean isPress) {
        if (isPress) {
            // select none item of menu
            bottomNavigationViewEx.setCurrentItem(3);
            // set activated
            moreRelativeLayout.setActivated(true);
            // change bg
            moreRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorBlack));
            // hide vertical line
            lineView2.setVisibility(View.INVISIBLE);
        } else {
            moreRelativeLayout.setActivated(false);
            moreRelativeLayout.setBackground(DataConverter.getDrawableById(mainActivity, R.color.colorPrimaryDark));
            if ((!calculatorRelativeLayout.isActivated()) && (!moreRelativeLayout.isActivated())){
                lineView2.setVisibility(View.VISIBLE);
            }
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

    public RelativeLayout getQuizRelativeLayout() {
        return quizRelativeLayout;
    }

    public View getLineView1() {
        return lineView1;
    }

    // display search function
    public void displaySearch() {
        // show search button
        this.moveForOnlyShowSearchIcon(); // move search button
        TOOLBAR_SEARCH_BUTTON = this.searchLinearLayout.getWidth();
        this.moveCalculatorAndMoreToLeft(1000); // move calculator and more
        this.setSearchButtonVisible(true, true, 1000); // show search button
        // set search button on click listener
        this.setSearchOnClickListener();
        // set close search button on click listener
        this.setCloseSearchOnClickListener();
    }

    // close search function
    public void closeSearch() {
        // hide search area
        // clear the edit text content
        doSearchEditText.clearTextChangedListeners();
        doSearchEditText.setText("");
        // hide keyboard
        KeyboardToggleUtils.hideKeyboard(mainActivity);
        // set GONE to all search views
        mainActivity.setAllSearchViewLinearLayoutVisibility(View.GONE);

    }

    // only show search button (hide edit and close button )
    public void moveForOnlyShowSearchIcon() {
        // change the search icon style
        searchLinearLayout.setBackgroundResource(R.drawable.ripple_btn_open_search_toolbar);
        searchImageView.setImageResource(R.drawable.ic_search_white_30dp);
        // hide EditText and close button
        TOOLBAR_SEARCH_EDIT_AND_CLOSE = doSearchEditText.getWidth() + closeSearchLinearLayout.getWidth();
        this.allSearchViewLinearLayout.setX(TOOLBAR_SEARCH_EDIT_AND_CLOSE + 1);
    }

    // move the "calculator" and "more" buttons to left
    public void moveCalculatorAndMoreToLeft(int duration) {
        // move Calculator And More
        MyAnimationBox.runSlideInAnimationFromRight(
                this.topButtonsLinearLayout,
                this.topButtonsLinearLayout.getX(),
                this.toolbarAllViewsRelativeLayout.getWidth() - this.searchLinearLayout.getWidth() - this.topButtonsLinearLayout.getWidth(),
                duration);
    }

    // move the "calculator" and "more" buttons to right
    public void moveCalculatorAndMoreToRight(String fragmentTag, int duration) {
        switch (fragmentTag) {
            case AppResources.FRAGMENT_TAG_LEARN:
            case AppResources.FRAGMENT_TAG_VIRUS_CHECK:
            case AppResources.FRAGMENT_TAG_HOME:

                if (FROM_VIRUS_INFO_PAGE) {
                    FROM_VIRUS_INFO_PAGE = false;
                    MyAnimationBox.runSlideInAnimationFromRight(
                            this.topButtonsLinearLayout,
                            this.topButtonsLinearLayout.getX() + TOOLBAR_SEARCH_BUTTON,
                            this.toolbarAllViewsRelativeLayout.getWidth() - this.topButtonsLinearLayout.getWidth() + TOOLBAR_SEARCH_BUTTON,
                            duration);
                }
                break;
            case AppResources.FRAGMENT_TAG_WATER_CALCULATOR:
            case AppResources.FRAGMENT_TAG_MORE:

                if (FROM_VIRUS_INFO_PAGE) {
                    FROM_VIRUS_INFO_PAGE = false;

                    MyAnimationBox.runSlideInAnimationFromRight(
                            this.topButtonsLinearLayout,
                            this.topButtonsLinearLayout.getX() + TOOLBAR_SEARCH_BUTTON,
                            this.toolbarAllViewsRelativeLayout.getWidth() - this.topButtonsLinearLayout.getWidth(),
                            duration);
                }
                break;
            default:
                if (FROM_VIRUS_INFO_PAGE) {
                    FROM_VIRUS_INFO_PAGE = false;

                    MyAnimationBox.runSlideInAnimationFromRight(
                            this.topButtonsLinearLayout,
                            this.topButtonsLinearLayout.getX(),
                            this.toolbarAllViewsRelativeLayout.getWidth() - this.topButtonsLinearLayout.getWidth(),
                            duration);
                }
                break;
        }
    }

    // set search button visible
    public void setSearchButtonVisible(boolean showOrHide, boolean withFadeAnimation, int duration) {
        if (showOrHide) {
            if (withFadeAnimation) {
                MyAnimationBox.runFadeInAnimation(this.allSearchViewLinearLayout, duration);
            } else {
                this.allSearchViewLinearLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (withFadeAnimation) {
                MyAnimationBox.runFadeOutAnimation(this.allSearchViewLinearLayout, duration);
            } else {
                this.allSearchViewLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    // set search button on click listener
    public void setSearchOnClickListener() {
        this.searchLinearLayout.setOnClickListener(v -> {
            if (this.allSearchViewLinearLayout.getX() != 0) {
                showSearchArea(500);
            }
        });
    }

    // show search area
    public void showSearchArea(int duration) {
        // change the search icon style (green icon)
        searchLinearLayout.setBackgroundResource(R.drawable.ripple_btn_search_toolbar);
        searchImageView.setImageResource(R.drawable.ic_search_green_30dp);

        // show search area
        MyAnimationBox.runSlideInAnimationFromRight(
                this.allSearchViewLinearLayout,
                this.allSearchViewLinearLayout.getX(),
                0,
                duration);
    }

    // set close search button on click listener
    public void setCloseSearchOnClickListener() {
        this.closeSearchLinearLayout.setOnClickListener(v -> {
            hideSearchArea(500);
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
                allSearchViewLinearLayout,
                allSearchViewLinearLayout.getX(),
                TOOLBAR_SEARCH_EDIT_AND_CLOSE + 1,
                duration);
        // change the search icon style (white icon)
        new Handler().postDelayed(() ->{
            searchLinearLayout.setBackgroundResource(R.drawable.ripple_btn_open_search_toolbar);
            searchImageView.setImageResource(R.drawable.ic_search_white_30dp);
        }, duration);
    }

    public void setAllSearchViewLinearLayoutVisibility(int visibility) {
        allSearchViewLinearLayout.setVisibility(visibility);
    }

    // show or not top bar back button
    public static void showTopBarBackButton(MainActivity mainActivity) {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment currentVisibleFragment = fragmentManager.findFragmentById(R.id.fl_fragments);

        boolean isHomeFragment = currentVisibleFragment instanceof ToolkitFragment;
        boolean isLearnFragment = currentVisibleFragment instanceof LearnFragment;
        boolean isVirusIdentificationFragment = currentVisibleFragment instanceof VirusCheckFragment;
        if ((currentVisibleFragment == null)
                || isHomeFragment
                || isLearnFragment
                || isVirusIdentificationFragment){
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(false);
        } else {
            // add back button
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentOperator.backToLastFragment(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            FragmentOperator.backToLastFragment(this);
            if (count == 1){
                // set title
                Objects.requireNonNull(getSupportActionBar()).setTitle("virusSafe Agro");
            }
        }
    }

    // initialize BottomNavigationView and set OnNavigationItemSelectedListener
    private void initializeBottomNavigationView(){
        this.bottomNavigationViewEx.setCurrentItem(3);
        this.bottomNavigationViewEx.enableItemShiftingMode(false);
        this.bottomNavigationViewEx.enableShiftingMode(false);
        this.setBottomNavigationViewExItemOnSelectedListener();
    }

    // listeners for BottomNavigationViewEx and floatingActionButton
    private void setBottomNavigationViewExItemOnSelectedListener() {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            // open fragment according to id
            switchFragments(id);
            return true;
        });

        floatingActionButton.setOnClickListener(view -> {
            showVirusCheckFragment();
        });
    }

    private void switchFragments(int itemId) {
        switch (itemId) {
            case R.id.ic_learn:
                FragmentOperator.replaceFragmentNoBackStack(this, new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
                break;
            case R.id.ic_virus_check:
                showVirusCheckFragment();
                break;
            case R.id.ic_toolkit:
                FragmentOperator.replaceFragmentNoBackStack(this, new ToolkitFragment(), AppResources.FRAGMENT_TAG_HOME);
                break;
//            case R.id.ic_calculator:
////                FragmentOperator.replaceFragmentNoBackStack(this, new VirusQuizListFragment(), AppResources.FRAGMENT_TAG_VIRUS_QUIZ);
//                FragmentOperator.replaceFragmentNoBackStack(this, new CalculatorFragment(), AppResources.FRAGMENT_TAG_WATER_CALCULATOR);
//                break;
//            case R.id.ic_more:
//                FragmentOperator.replaceFragmentNoBackStack(this, new MoreFragment(), AppResources.FRAGMENT_TAG_MORE);
//                break;
        }
    }

    private void showVirusCheckFragment() {
        bottomNavigationViewEx.setCurrentItem(3);
        FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
    }

    public BottomNavigationViewEx getBottomNavigationViewEx() {
        return bottomNavigationViewEx;
    }
}
