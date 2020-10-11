package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.virussafeagro.models.VirusModel;

public class QuizActivity extends AppCompatActivity {
    private int currentVirusModelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);

    }

    public void close(View v) {
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_slide_out_bottom);
    }
}
