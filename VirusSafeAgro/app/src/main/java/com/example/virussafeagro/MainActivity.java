package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.virussafeagro.fragments.CalculatorFragment;
import com.example.virussafeagro.fragments.HomeFragment;
import com.example.virussafeagro.fragments.LearnFragment;
import com.example.virussafeagro.fragments.MoreFragment;
import com.example.virussafeagro.fragments.NewsFragment;
import com.example.virussafeagro.fragments.NutrientFragment;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.DragYRelativeLayout;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.KeyboardToggleUtils;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private MainActivity mainActivity = this;
    private boolean isFromPasswordActivity;
    private boolean isFromOnBoardingActivity;
    private SharedPreferenceProcess spp;

    // toolbar
    private Toolbar toolbar;
    // toolbar - title
    private LinearLayout titleLinearLayout;
    private TextView titleTextView;
    // toolbar - search area
    private LinearLayout allSearchViewLinearLayout;
    private LinearLayout searchLinearLayout;
    private ImageView searchImageView;
    private com.example.virussafeagro.uitilities.ExtendedEditText doSearchEditText;
    private LinearLayout closeSearchLinearLayout; // for button
    // bottom bar
//    private BottomNavigationView bottomNavigationView;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private FloatingActionButton floatingActionButton;

    public static int TOOLBAR_WIDTH;
    public static int TOOLBAR_SEARCH_EDIT_AND_CLOSE;

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
        this.titleLinearLayout = findViewById(R.id.ll_title_toolbar);
        this.titleTextView = findViewById(R.id.tv_title_toolbar);
        this.allSearchViewLinearLayout = findViewById(R.id.ll_all_search_views_toolbar);
        this.searchLinearLayout = findViewById(R.id.ll_search_toolbar);
        this.searchImageView = findViewById(R.id.img_search_toolbar);
        this.doSearchEditText = findViewById(R.id.et_do_search_toolbar);
        this.closeSearchLinearLayout = findViewById(R.id.ll_close_btn_search_toolbar);
//        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.bottomNavigationViewEx = findViewById(R.id.bottom_navigation);
        this.floatingActionButton = findViewById(R.id.fab);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PASSWORD_REQUEST_CODE){
            if (resultCode == PASSWORD_RESULT_OK) { // from password activity
                isFromPasswordActivity = true;
                // show home page
                this.showHomePage();
            }
        }
        if(requestCode == ON_BOARDING_REQUEST_CODE){ // from on boarding activity
            if (resultCode == ON_BOARDING_RESULT_OK) {
                isFromOnBoardingActivity = true;
                // show home page
                this.showHomePage();
            }
        }
    }

    // add toolbar
    private void configureToolbar() {
        setSupportActionBar(this.toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
    public LinearLayout getTitleLinearLayout() {
        return titleLinearLayout;
    }
    public TextView getTitleTextView() {
        return titleTextView;
    }
    public com.example.virussafeagro.uitilities.ExtendedEditText getDoSearchEditText() {
        return doSearchEditText;
    }

    // display search function
    public void displaySearch() {
        // show search button
        mainActivity.onlyShowSearchIcon();
        mainActivity.showSearchButton(true, true, 1000);
        // set search button on click listener
        mainActivity.setSearchOnClickListener();
        // set close search button on click listener
        mainActivity.setCloseSearchOnClickListener();
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
    public void onlyShowSearchIcon() {
        // change the search icon style
        searchLinearLayout.setBackgroundResource(R.drawable.ripple_btn_open_search_toolbar);
        searchImageView.setImageResource(R.drawable.ic_search_white_30dp);
        // hide EditText and close button
        TOOLBAR_SEARCH_EDIT_AND_CLOSE = doSearchEditText.getWidth() + closeSearchLinearLayout.getWidth();
        this.allSearchViewLinearLayout.setX(TOOLBAR_SEARCH_EDIT_AND_CLOSE + 1);
    }

    // show search button
    public void showSearchButton(boolean showOrHide, boolean withFadeAnimation, int duration) {
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

        boolean isHomeFragment = currentVisibleFragment instanceof HomeFragment;
        boolean isLearnFragment = currentVisibleFragment instanceof LearnFragment;
        boolean isVirusIdentificationFragment = currentVisibleFragment instanceof VirusCheckFragment;
        boolean isCalculatorFragment = currentVisibleFragment instanceof CalculatorFragment;
        boolean isMoreFragment = currentVisibleFragment instanceof MoreFragment;
        if ((currentVisibleFragment == null)
                || isHomeFragment
                || isLearnFragment
                || isVirusIdentificationFragment
                || isCalculatorFragment
                || isMoreFragment){
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
//        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.bottomNavigationViewEx.enableItemShiftingMode(false);
        this.bottomNavigationViewEx.enableShiftingMode(false);
        this.setBottomNavigationViewExItemOnSelectedListener();
    }

    // show home page
    private void showHomePage() {
        FragmentOperator.replaceFragmentNoBackStack(this, new HomeFragment(), AppResources.FRAGMENT_TAG_HOME);
    }

    private void setBottomNavigationViewExItemOnSelectedListener() {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                // open fragment according to id
                switchFragments(id);
                return true;
            }
        });

        floatingActionButton.setOnClickListener(view -> {
            bottomNavigationViewEx.setCurrentItem(2);
        });
    }

    private void switchFragments(int itemId) {
        switch (itemId) {
            case R.id.ic_home:
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                Fragment currentVisibleFragment = fragmentManager.findFragmentById(R.id.fl_fragments);
                if (currentVisibleFragment instanceof HomeFragment){ // if it is in home fragment now
                    HomeFragment currentHomeFragment = (HomeFragment)currentVisibleFragment;
                    DragYRelativeLayout homeImageDragYRelativeLayout = currentHomeFragment.getHomeImageDragYRelativeLayout();
                    if (homeImageDragYRelativeLayout.getVisibility() == View.GONE){
                        MyAnimationBox.runSlideInAnimationFromTop(homeImageDragYRelativeLayout, 500); // show
                    } else {
                        MyAnimationBox.runSlideOutAnimationToTop(homeImageDragYRelativeLayout, 500); // hide
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "menu");
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    FragmentOperator.replaceFragmentNoBackStack(this, homeFragment, AppResources.FRAGMENT_TAG_HOME);
                }
                break;
            case R.id.ic_learn:
                FragmentOperator.replaceFragmentNoBackStack(this, new LearnFragment(), AppResources.FRAGMENT_TAG_LEARN);
                break;
            case R.id.ic_virus_check:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
                break;
            case R.id.ic_calculator:
//                FragmentOperator.replaceFragmentNoBackStack(this, new VirusQuizListFragment(), AppResources.FRAGMENT_TAG_VIRUS_QUIZ);
                FragmentOperator.replaceFragmentNoBackStack(this, new CalculatorFragment(), AppResources.FRAGMENT_TAG_WATER_CALCULATOR);
                break;
            case R.id.ic_more:
                FragmentOperator.replaceFragmentNoBackStack(this, new MoreFragment(), AppResources.FRAGMENT_TAG_MORE);
                break;
        }
    }

//    public BottomNavigationView getBottomNavigationView() {
//        return bottomNavigationView;
//    }


    public BottomNavigationViewEx getBottomNavigationViewEx() {
        return bottomNavigationViewEx;
    }
}
