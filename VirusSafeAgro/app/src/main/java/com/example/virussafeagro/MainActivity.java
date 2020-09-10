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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.virussafeagro.fragments.HomeFragment;
import com.example.virussafeagro.fragments.MoreFragment;
import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.fragments.VirusQuizListFragment;
import com.example.virussafeagro.uitilities.AppAuthentication;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private MainActivity mainActivity = this;
    private boolean isFromPasswordActivity;

    private LinearLayout backgroundLinearLayout;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set app password
        AppAuthentication.setAppPassword(this);

        // show or not top action bar (back button + title)
        showTopActionBar(this);

        // show home fragment
        FragmentOperator.replaceFragment(this, new HomeFragment());

        // initialize background image
        this.backgroundLinearLayout = findViewById(R.id.ll_image_main);
        // initialize bottom navigation bar
        this.initializeBottomNavigationView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check authentication
        if (!this.isFromPasswordActivity){
            AppAuthentication.setAuthenticationAsNo(this);
            new Handler().postDelayed(() -> AppAuthentication.checkAuthentication(mainActivity),200);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppAuthentication.PASSWORD_REQUEST_CODE){
            if (resultCode == AppAuthentication.PASSWORD_RESULT_OK) {
                isFromPasswordActivity = true;
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
//        super.onOptionsItemSelected(item);
        FragmentOperator.backToLastFragment(this);
        return true;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            FragmentOperator.backToLastFragment(this);
        }
    }

    // initialize BottomNavigationView and set OnNavigationItemSelectedListener
    private void initializeBottomNavigationView(){
        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        this.backgroundLinearLayout.setVisibility(View.INVISIBLE);

        // open fragment according to id
        this.switchFragments(id);

        return true;
    }

    private void switchFragments(int itemId) {
        switch (itemId) {
            case R.id.ic_home:
                FragmentOperator.replaceFragment(this, new HomeFragment());
                break;
            case R.id.ic_virus_info:
                FragmentOperator.replaceFragment(this, new VirusInfoListFragment());
                break;
            case R.id.ic_virus_check:
                FragmentOperator.replaceFragment(this, new VirusCheckFragment());
                break;
            case R.id.ic_virus_quiz:
                FragmentOperator.replaceFragment(this, new VirusQuizListFragment());
                break;
            case R.id.ic_more:
                FragmentOperator.replaceFragment(this, new MoreFragment());
                break;
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        AppAuthentication.setAuthenticationAsNo(this);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        AppAuthentication.setAuthenticationAsNo(this);
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AppAuthentication.setAuthenticationAsNo(this);
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        AppAuthentication.setAuthenticationAsNo(this);
//    }
}
