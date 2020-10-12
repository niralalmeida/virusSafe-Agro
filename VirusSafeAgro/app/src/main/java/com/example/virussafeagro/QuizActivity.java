package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.uitilities.AppResources;

public class QuizActivity extends AppCompatActivity {
    private int currentVirusModelId;
    private ImageView virusImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get current virus model
        this.currentVirusModelId = getIntent().getIntExtra("currentVirusModelId", -1);
        // initialize views
        this.initializeViews();
        // show activity views
        this.showActivityViews();

    }

    private void initializeViews() {
        virusImageView = findViewById(R.id.img_pic_virus_quiz);
    }

    private void showActivityViews() {
        // virus image
        int virusPictureDrawableId = AppResources.getVirusPictureDrawableId(currentVirusModelId);
        Bitmap virusPictureBitmap = BitmapFactory.decodeResource(getResources(), virusPictureDrawableId);
        this.virusImageView.setImageBitmap(virusPictureBitmap);
    }

    public void close(View v) {
        // animation
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_fade_out);
    }
}
