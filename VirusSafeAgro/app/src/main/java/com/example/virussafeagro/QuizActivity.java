package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void close(View v) {
//        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
//        this.startActivity(intent);
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_slide_out_bottom);
    }
}
