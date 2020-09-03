package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.virussafeagro.fragments.VirusCheckFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.fragments.VirusQuizListFragment;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // show or not top action bar (back button + title)
        showTopActionBar(this);

        // initialize bottom navigation bar
        this.initializeBottomNavigationView();
    }

    // show or not top action bar
    public static void showTopActionBar(MainActivity mainActivity) {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
//        Fragment currentVisibleFragment = FragmentOperator.getVisibleFragment(fragmentManager);
        Fragment currentVisibleFragment = fragmentManager.findFragmentById(R.id.fl_fragments);

        boolean isVirusInfoListFragment = currentVisibleFragment instanceof VirusInfoListFragment;
        boolean isVirusIdentificationFragment = currentVisibleFragment instanceof VirusCheckFragment;
        boolean isVirusQuizListFragment = currentVisibleFragment instanceof VirusQuizListFragment;
        if ((currentVisibleFragment == null) || isVirusInfoListFragment || isVirusIdentificationFragment || isVirusQuizListFragment){
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(false);
        } else {
            // add back button
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(mainActivity.getSupportActionBar()).setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentOperator.backToLastFragment(this);
    }

    // initialize BottomNavigationView and set OnNavigationItemSelectedListener
    private void initializeBottomNavigationView(){
        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        this.hideHomePagePicture();

        // open fragment according to id
        this.switchFragments(id);

        return true;
    }

    private void switchFragments(int itemId) {
        switch (itemId) {
            case R.id.ic_virus_info:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusInfoListFragment());
                break;
            case R.id.ic_virus_check:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusCheckFragment());
                break;
            case R.id.ic_virus_quiz:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusQuizListFragment());
                break;
        }
    }

    private void hideHomePagePicture(){
        LinearLayout homeLinearLayout = findViewById(R.id.ll_home_picture);
        homeLinearLayout.setVisibility(View.INVISIBLE);
    }
}
