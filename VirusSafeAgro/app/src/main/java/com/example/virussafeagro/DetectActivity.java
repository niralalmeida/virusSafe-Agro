package com.example.virussafeagro;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.virussafeagro.animation.MyAnimationBox;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class DetectActivity extends AppCompatActivity {
    //views
    private View outsideTouchView;
    private FrameLayout bottomSheetFrameLayout;
    private MotionLayout containerMotionLayout;
    private ImageButton closeButtonImageButton;
    private LinearLayout twoButtonsLinearLayout;
    private LinearLayout cameraLinearLayout;
    private LinearLayout galleryLinearLayout;
    private ImageView cameraImageView;
    private TextView cameraTextView;
    private ImageView galleryImageView;
    private TextView galleryTextView;
    private ImageView uploadImageView;
    private Button uploadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        // initialize Views
        this.initializeViews();
        // set Outside TouchView On Touch Listener
        this.setOutsideTouchViewOnTouchListener();
        // set CloseButton OnClickListener
        this.setCloseButtonOnClickListener();

        // show the title and 2 buttons
        this.showTitleAndButton();
    }

    private void initializeViews() {
        this.outsideTouchView = findViewById(R.id.touch_outside);
        this.bottomSheetFrameLayout = findViewById(R.id.fl_bottom_sheet);
        this.containerMotionLayout = findViewById(R.id.ml_detect_activity);
        this.closeButtonImageButton = findViewById(R.id.btn_close_detect_activity);
        this.twoButtonsLinearLayout = findViewById(R.id.ll_2_buttons_detect);
        this.cameraLinearLayout = findViewById(R.id.ll_camera);
        this.galleryLinearLayout = findViewById(R.id.ll_gallery);
        this.cameraImageView = findViewById(R.id.img_camera_detect_activity);
        this.cameraTextView = findViewById(R.id.tv_camera_detect_activity);
        this.galleryImageView = findViewById(R.id.img_gallery_detect_activity);
        this.galleryTextView = findViewById(R.id.tv_gallery_detect_activity);
        this.uploadImageView = findViewById(R.id.img_upload_detect_activity);
        this.uploadButton = findViewById(R.id.btn_upload_image_detect_activity);
    }

    // bottom sheet state change listener
    private void setOutsideTouchViewOnTouchListener() {
        this.bottomSheetFrameLayout.setOnClickListener(v -> {
        });
        this.outsideTouchView.setOnClickListener(v -> {
            hideTitleAndButton();
            new Handler().postDelayed(()->{
                supportFinishAfterTransition();
            }, 550);
        });
        BottomSheetBehavior
                .from(bottomSheetFrameLayout)
                .addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_HIDDEN:
                                // hide button image and text
                                cameraImageView.setVisibility(View.INVISIBLE);
                                cameraTextView.setVisibility(View.INVISIBLE);
                                galleryImageView.setVisibility(View.INVISIBLE);
                                galleryTextView.setVisibility(View.INVISIBLE);
                                // add space between the 2 buttons
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galleryLinearLayout.getLayoutParams();
                                layoutParams.setMarginStart(0);
                                galleryLinearLayout.setLayoutParams(layoutParams);
                                // finish this activity
                                supportFinishAfterTransition();
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                setStatusBarDim(false);
                                break;
                            default:
                                setStatusBarDim(true);
                                break;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        // no op
                    }
                });
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray a = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    private void setCloseButtonOnClickListener() {
        this.closeButtonImageButton.setOnClickListener(v ->{
            hideTitleAndButton();
            new Handler().postDelayed(()->{
                supportFinishAfterTransition();
            }, 550);
        });
    }

    // show the title and 2 buttons
    private void showTitleAndButton() {
        // show the buttons
        new Handler().postDelayed(()->{
            // add space between the 2 buttons
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galleryLinearLayout.getLayoutParams();
            layoutParams.setMarginStart(3);
            galleryLinearLayout.setLayoutParams(layoutParams);

            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_button, R.id.end_show_detect_button, 300);
        }, 800);

        // show text
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_text, R.id.end_show_detect_text, 200);
        }, 1100);

        // show button image and text
        new Handler().postDelayed(() -> {
            cameraImageView.setVisibility(View.VISIBLE);
            cameraTextView.setVisibility(View.VISIBLE);
            galleryImageView.setVisibility(View.VISIBLE);
            galleryTextView.setVisibility(View.VISIBLE);
        }, 1300);
    }

    // hide the title and 2 buttons
    private void hideTitleAndButton() {
        // hide button image and text
        cameraImageView.setVisibility(View.INVISIBLE);
        cameraTextView.setVisibility(View.INVISIBLE);
        galleryImageView.setVisibility(View.INVISIBLE);
        galleryTextView.setVisibility(View.INVISIBLE);
        // hide text
        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_text, R.id.start_show_detect_text, 200);
        // hide the buttons
        new Handler().postDelayed(()->{
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_button, R.id.start_show_detect_button, 300);
        }, 200);

        new Handler().postDelayed(()->{
            // add space between the 2 buttons
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galleryLinearLayout.getLayoutParams();
            layoutParams.setMarginStart(0);
            galleryLinearLayout.setLayoutParams(layoutParams);
        }, 500);
    }

}
