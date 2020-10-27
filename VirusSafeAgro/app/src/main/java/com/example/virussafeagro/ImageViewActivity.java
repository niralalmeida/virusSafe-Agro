package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {
    private ImageViewActivity imageViewActivity;

    // data
    public static Bitmap currentImageBitmap;
    // views
    private ImageView imageView;

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

        // initialize Views
        this.initializeViews();

        // show image
        this.showImage();

        // set image on click
        this.setImageOnClickListener();
    }

    private void initializeViews() {
        this.imageView = findViewById(R.id.img_image_view_activity);
    }

    // set and show image
    private void showImage() {
        this.imageView.setImageBitmap(currentImageBitmap);
    }

    // set Image On Click Listener
    private void setImageOnClickListener() {
        this.imageView.setOnClickListener(pv -> {
            supportFinishAfterTransition();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentImageBitmap = null;
    }
}
