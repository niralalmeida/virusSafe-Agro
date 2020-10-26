package com.example.virussafeagro;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class DetectActivity extends AppCompatActivity {
    private DetectActivity detectActivity;
    // data
    public static Bitmap uploadImageBitmap;
    // tools
    private BottomSheetBehavior bottomSheetBehavior;
    private static final int PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE = 99;
    public static boolean hasDetectRequest;
    //views
    private View outsideTouchView;
    private FrameLayout bottomSheetFrameLayout;
    private MotionLayout containerMotionLayout;
    private ImageButton closeButtonImageButton;
    private CardView cameraCardView;
    private ImageView cameraImageView;
    private TextView cameraTextView;
    private ImageView uploadImageView;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(100);
        getWindow().setSharedElementEnterTransition(bounds);
        setContentView(R.layout.activity_detect);

        // set activity
        this.detectActivity = this;
        // initialize Views
        this.initializeViews();
        // initialize bottom sheet behavior
        this.initializeBehavior();
        // set Outside TouchView On Touch Listener
        this.setOutsideTouchViewOnTouchListener();
        // set CloseButton OnClickListener
        this.setCloseButtonOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // show the title and 2 buttons
        int delayDuration = this.showTitleAndButtonAndReturnDuration();
        // control the display
        this.controlDisplay(delayDuration);
    }

    private void initializeViews() {
        this.outsideTouchView = findViewById(R.id.touch_outside);
        this.bottomSheetFrameLayout = findViewById(R.id.fl_bottom_sheet);
        this.containerMotionLayout = findViewById(R.id.ml_detect_activity);
        this.closeButtonImageButton = findViewById(R.id.btn_close_detect_activity);
        this.cameraCardView = findViewById(R.id.cv_camera_button_detect);
        this.cameraImageView = findViewById(R.id.img_camera_detect_activity);
        this.cameraTextView = findViewById(R.id.tv_camera_detect_activity);
        this.uploadImageView = findViewById(R.id.img_leaf_for_scanning_detect_activity);
        this.uploadButton = findViewById(R.id.btn_upload_image_detect_activity);
    }

    private void initializeBehavior() {
        this.bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFrameLayout);
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
        this.bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        // hide button image and text
                        cameraImageView.setVisibility(View.INVISIBLE);
                        cameraTextView.setVisibility(View.INVISIBLE);
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

    // set display according to the tasks
    private void controlDisplay(int delayDuration) {
        if(hasDetectRequest) {
            hasDetectRequest = false;
            // enable scrollable
            this.bottomSheetBehavior.setDraggable(true);
            new Handler().postDelayed(() -> {
                // show the upload image view
                uploadImageView.setImageBitmap(uploadImageBitmap);
                // expand the sheet and show the analyse animation
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }, delayDuration);

            // test
            new Handler().postDelayed(() -> {
                uploadImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_result, R.id.end_show_detect_result, 300);
            }, delayDuration+2000);
        } else {
            uploadImageBitmap = null;
            // disable scrollable
            this.bottomSheetBehavior.setDraggable(false);
            // show the animation
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_result, R.id.start_show_detect_result, 300);
        }
    }

    // show the title and 2 buttons
    private int showTitleAndButtonAndReturnDuration() {
        // show the buttons
        new Handler().postDelayed(()->{

            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_button, R.id.end_show_detect_button, 200);
        }, 300);

        // show text
        new Handler().postDelayed(() -> {
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_text, R.id.end_show_detect_text, 200);
        }, 500);

        // show button image and text
        new Handler().postDelayed(() -> {
            cameraImageView.setVisibility(View.VISIBLE);
            cameraTextView.setVisibility(View.VISIBLE);
        }, 700);

        return 700;
    }

    // hide the title and 2 buttons
    private void hideTitleAndButton() {
        // hide button image and text
        cameraImageView.setVisibility(View.INVISIBLE);
        cameraTextView.setVisibility(View.INVISIBLE);
        // hide text
        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_text, R.id.start_show_detect_text, 200);
        // hide the buttons
        new Handler().postDelayed(()->{
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_button, R.id.start_show_detect_button, 200);
        }, 200);
    }

    // camera button onclick
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openCameraOnClick(View v) {
//        ImagePicker.Companion.with(this)
//                .cameraOnly()
//                .start(REQUEST_OPEN_CAMERA);

        // intent way
//        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(getImageByCamera, REQUEST_OPEN_CAMERA);

        this.checkCameraPermissions();

    }

    private void startCamera() {
        hideTitleAndButton();
        new Handler().postDelayed(()->{
            Intent intent = new Intent(DetectActivity.this, CameraActivity.class);
            // animation
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, this.cameraCardView, ViewCompat.getTransitionName(this.cameraCardView));
            startActivity(intent, options.toBundle());
        }, 400);
    }

    // ask for getting user's current location permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCameraPermissions() {
        if (ActivityCompat.checkSelfPermission(detectActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(detectActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(detectActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE);

        } else { // grant the permission
            // start camera activity
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults){
        if (requestCode == PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        detectActivity,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // start camera activity
                    startCamera();
                }
            } else {
                Toast.makeText(detectActivity, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
