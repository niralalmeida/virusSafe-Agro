package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.virussafeagro.fragments.VirusIdentificationFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.example.virussafeagro.fragments.VirusQuizListFragment;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeBottomNavigationView();
    }

    // initialize BottomNavigationView and set OnNavigationItemSelectedListener
    private void initializeBottomNavigationView(){
        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        hideHomePagePicture();
        switch (id) {
            case R.id.ic_virus_info:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusInfoListFragment());
                break;
            case R.id.ic_virus_identification:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusIdentificationFragment());
                break;
            case R.id.ic_virus_quiz:
                FragmentOperator.replaceFragmentNoBackStack(this, new VirusQuizListFragment());
                break;
        }
        return true;
    }

    private void hideHomePagePicture(){
        LinearLayout homeLinearLayout = findViewById(R.id.ll_home_picture);
        homeLinearLayout.setVisibility(View.INVISIBLE);
    }
}
