package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.virussafeagro.fragments.HomeFragment;
import com.example.virussafeagro.fragments.MoreFragment;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.fragments.VirusQuizListFragment;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyAnimationBox;
import com.example.virussafeagro.uitilities.SharedPreferenceProcess;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private MainActivity mainActivity = this;
    private boolean isFromPasswordActivity;
    private boolean isFromOnBoardingActivity;
    private SharedPreferenceProcess spp;

    private LinearLayout backgroundLinearLayout;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout launchScreenLinearLayout;

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
        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.backgroundLinearLayout = findViewById(R.id.ll_image_main);
        this.launchScreenLinearLayout = findViewById(R.id.ll_launch_screen);
    }

    private void initializeSharedPreferenceProcess() {
        this.spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(this);
    }

    private void showAndCloseTheLaunchScreen() {
        // hide bottom bar
        this.bottomNavigationView.setVisibility(View.GONE);
        // show launch screen
        this.launchScreenLinearLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            // hide launch screen
            MyAnimationBox.runFadeOutAnimation(launchScreenLinearLayout, 1000);
            // show bottom bar
            new Handler().postDelayed(() -> bottomNavigationView.setVisibility(View.VISIBLE),1000);
        },4000);
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
            new Handler().postDelayed(() -> AppAuthentication.checkAuthentication(mainActivity),200);
        }
    }

    private void displayAllMainActivityViews() {
        // show or not top action bar (back button + title)
        showTopActionBar(this);

        // initialize bottom navigation bar
        this.initializeBottomNavigationView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PASSWORD_REQUEST_CODE){
            if (resultCode == PASSWORD_RESULT_OK) { // from password activity
                isFromPasswordActivity = true;
                this.setMainBackgroundImageVisibility(true);

                // show launch screen
//                this.showAndCloseTheLaunchScreen();
            }
        }
        if(requestCode == ON_BOARDING_REQUEST_CODE){ // from on boarding activity
            if (resultCode == ON_BOARDING_RESULT_OK) {
                isFromOnBoardingActivity = true;
                this.setMainBackgroundImageVisibility(true);
            }
        }
    }

    // show or not top action bar
    public static void showTopActionBar(MainActivity mainActivity) {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment currentVisibleFragment = fragmentManager.findFragmentById(R.id.fl_fragments);

        boolean isHomeFragment = currentVisibleFragment instanceof HomeFragment;
        boolean isVirusInfoListFragment = currentVisibleFragment instanceof VirusInfoListFragment;
        boolean isVirusIdentificationFragment = currentVisibleFragment instanceof VirusCheckFragment;
        boolean isVirusQuizListFragment = currentVisibleFragment instanceof VirusQuizListFragment;
        boolean isMoreFragment = currentVisibleFragment instanceof MoreFragment;
        if ((currentVisibleFragment == null)
                || isHomeFragment
                || isVirusInfoListFragment
                || isVirusIdentificationFragment
                || isVirusQuizListFragment
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
                this.setMainBackgroundImageVisibility(true);
                // set title
                Objects.requireNonNull(getSupportActionBar()).setTitle("virusSafe Agro");
            }
        }
    }

    // initialize BottomNavigationView and set OnNavigationItemSelectedListener
    private void initializeBottomNavigationView(){
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        this.setMainBackgroundImageVisibility(false);

        // open fragment according to id
        this.switchFragments(id);

        return true;
    }

    public void setMainBackgroundImageVisibility(boolean isVisible) {
        if (isVisible) {
            // show tha main background image
            this.backgroundLinearLayout.setVisibility(View.VISIBLE);
        } else {
            // hide tha main background image
            this.backgroundLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void switchFragments(int itemId) {
        switch (itemId) {
            case R.id.ic_home:
                FragmentOperator.replaceFragmentNoBackStack(this, new HomeFragment(), AppResources.FRAGMENT_TAG_HOME);
                break;
            case R.id.ic_virus_info:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusInfoListFragment(), AppResources.FRAGMENT_TAG_VIRUS_INFO);
                break;
            case R.id.ic_virus_check:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment(), AppResources.FRAGMENT_TAG_VIRUS_CHECK);
                break;
            case R.id.ic_virus_quiz:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusQuizListFragment(), AppResources.FRAGMENT_TAG_VIRUS_QUIZ);
                break;
            case R.id.ic_more:
                FragmentOperator.replaceFragmentNoBackStack(this, new MoreFragment(), AppResources.FRAGMENT_TAG_MORE);
                break;
        }
    }

}
