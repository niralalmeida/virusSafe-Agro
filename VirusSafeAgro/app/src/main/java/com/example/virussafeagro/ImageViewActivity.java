package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.virussafeagro.animation.MyAnimationBox;

public class ImageViewActivity extends AppCompatActivity {
    private ImageViewActivity imageViewActivity;

    // data
    public static Bitmap currentImageBitmap;
    // views
    private ImageView imageView;
    // tools
    private int lastX; // on touch x
    private int lastY; // on touch y

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

    }

    private void initializeViews() {
        this.imageView = findViewById(R.id.img_image_view_activity);
    }

    // set and show image
    private void showImage() {
        this.imageView.setImageBitmap(currentImageBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(x == lastX && y == lastY){
                    supportFinishAfterTransition();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentImageBitmap = null;
    }
}
