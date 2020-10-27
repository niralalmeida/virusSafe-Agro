package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.WindowManager;

import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageViewActivity extends AppCompatActivity {
    private ImageViewActivity imageViewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(100);
        getWindow().setSharedElementEnterTransition(bounds);
        setContentView(R.layout.activity_image_view);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set activity
        this.imageViewActivity = this;


        PhotoView photoView = findViewById(R.id.photo_view);
        photoView.setImageBitmap(VirusInfoListFragment.currentVirusImageBitmap);
    }
}
