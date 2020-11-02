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
import android.os.AsyncTask;
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
import com.example.virussafeagro.models.ImageObject;
import com.example.virussafeagro.models.VirusModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToMLModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.FragmentOperator;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.example.virussafeagro.viewModel.VirusCheckViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class DetectActivity extends AppCompatActivity {
    private DetectActivity detectActivity;
    // data
    private NetworkConnectionToMLModel networkConnectionToMLModel;
    public static Bitmap uploadImageBitmap;
    private DetectActivity.GettingTomatoImageCheckFeedbackAsyncTask gettingTomatoImageCheckFeedbackAsyncTask;
    // tools
    private BottomSheetBehavior bottomSheetBehavior;
    private static final int PERMISSIONS_REQUEST_CAMERA_REQUEST_CODE = 99;
    public static boolean hasDetectRequest;
    public boolean isResultShow;
    private int delayDuration;
    //views
    private View outsideTouchView;
    private FrameLayout bottomSheetFrameLayout;
    private MotionLayout containerMotionLayout;
    private ImageButton closeButtonImageButton;
    private CardView cameraCardView;
    private ImageView cameraImageView;
    private TextView cameraTextView;
    private ImageView uploadImageView;
    private TextView resultTextView;
    private TextView illTitleTextView;
    private View resultTagView;
    private CardView resultCardView;
    private CardView virusButton;
    private CardView controlButton;

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
        // initialize data
        this.initializeData();
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
        delayDuration = this.showTitleAndButtonAndReturnDuration();
        // control the detect
        this.controlDetect(delayDuration);
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
        this.resultTextView = findViewById(R.id.tv_feedback_detect_activity);
        this.illTitleTextView = findViewById(R.id.tv_ill_title_detect_activity);
        this.resultTagView = findViewById(R.id.v_tag_feedback_detect_activity);
        this.resultCardView = findViewById(R.id.cv_feedback_detect_activity);
        this.virusButton = findViewById(R.id.cv_virus_button_detect_activity);
        this.controlButton = findViewById(R.id.cv_control_button_detect_activity);
    }

    private void initializeData() {
        networkConnectionToMLModel = new NetworkConnectionToMLModel();
        gettingTomatoImageCheckFeedbackAsyncTask = new DetectActivity.GettingTomatoImageCheckFeedbackAsyncTask();
    }

    private void initializeBehavior() {
        this.bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFrameLayout);
    }

    // bottom sheet state change listener
    private void setOutsideTouchViewOnTouchListener() {
        this.bottomSheetFrameLayout.setOnClickListener(v -> {
        });
        this.outsideTouchView.setOnClickListener(v -> {
            // collapse the sheet and show the analyse animation
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            new Handler().postDelayed(()->{
                hideTitleAndButton();
            }, 200);
            new Handler().postDelayed(()->{
                supportFinishAfterTransition();
            }, 600);
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
            // collapse the sheet and show the analyse animation
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            new Handler().postDelayed(()->{
                hideTitleAndButton();
            }, 200);
            new Handler().postDelayed(()->{
                supportFinishAfterTransition();
            }, 600);
        });
    }

    // set display according to the tasks
    private void controlDetect(int delayDuration) {
        // set the image
        if(hasDetectRequest) {
            hasDetectRequest = false;
            isResultShow = true;
            // show the animation
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_detect_result, R.id.start_show_detect_result, 300);
            // enable scrollable
            this.bottomSheetBehavior.setDraggable(true);
            new Handler().postDelayed(() -> {
                // show the upload image view
                uploadImageView.setImageBitmap(uploadImageBitmap);
                // expand the sheet and show the analyse animation
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                // upload the image and do the detect
                uploadImageAndDoDetecting();
            }, delayDuration);
        } else {
            // disable scrollable
            this.bottomSheetBehavior.setDraggable(false);
            // hide the upload image view
            uploadImageView.setImageBitmap(null);
            uploadImageBitmap = null;
            if (isResultShow){
                isResultShow = false;
                // collapse the sheet and show the analyse animation
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
    }

    // upload the image and do the detect
    private void uploadImageAndDoDetecting(){
        gettingTomatoImageCheckFeedbackAsyncTask = new GettingTomatoImageCheckFeedbackAsyncTask();
        gettingTomatoImageCheckFeedbackAsyncTask.execute(uploadImageBitmap);
    }
    // AsyncTask for detecting
    private class GettingTomatoImageCheckFeedbackAsyncTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            String feedback = "";
            Bitmap uploadImageBitmap = bitmaps[0];
            String uploadImageBitmapString = DataConverter.bitmapToStringConverter(uploadImageBitmap);
            ImageObject imageObject = new ImageObject(uploadImageBitmapString);
            try {
                String rawFeedback = networkConnectionToMLModel.getImageCheckFeedback(imageObject);
                feedback = MyJsonParser.imageCheckFeedbackJsonParser(rawFeedback);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return feedback;
        }

        @Override
        protected void onPostExecute(String resultCheckFeedback) {
            // show the result image
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_detect_result, R.id.end_show_detect_result, 300);
            new Handler().postDelayed(() -> {
                uploadImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                // show the result
                if (resultCheckFeedback.equals("json error") || resultCheckFeedback.equals(MyJsonParser.WRONG_IMAGE)){
                    resultTagView.setBackgroundResource(R.color.wrongAnswer);
                    resultTextView.setText(getResources().getText(R.string.text_result_error));
                } else if (resultCheckFeedback.equals(MyJsonParser.SERVER_STOP_MESSAGE)){
                    resultTagView.setBackgroundResource(R.color.colorBlack);
                    resultCardView.setCardBackgroundColor(getResources().getColor(R.color.colorBlack));
                    resultTextView.setText("The server has been stopped!");
                }else if (resultCheckFeedback.equals("healthy")){
                    resultTagView.setBackgroundResource(R.color.colorPrimaryDark);
                    resultTextView.setText(getResources().getText(R.string.text_result_healthy));
                } else {
                    // set tag color
                    resultTagView.setBackgroundResource(R.color.iconVirus);
                    // show virus result title
                    illTitleTextView.setVisibility(View.VISIBLE);
                    // set virus name
                    String virusString = resultCheckFeedback.replace("_", " ");
                    resultTextView.setText(virusString);

                    // virus button
                    MyAnimationBox.runFadeInAnimation(virusButton, 200);
                    // get virus id
                    int resultVirusId = AppResources.getVirusIdByNameForML(resultCheckFeedback);
                    // set VirusButton On Click Listener
                    setVirusButtonOnClickListener(resultVirusId);

                    // control button
                    MyAnimationBox.runFadeInAnimation(controlButton, 200);
                    // set ControlButton On Click Listener
                    setControlButtonOnClickListener();
                }
            }, 100);
        }
    }

    // set VirusButton On Click Listener
    private void setVirusButtonOnClickListener(int resultVirusId) {
        virusButton.setOnClickListener(vb -> {
            if (!MainActivity.virusModelInfoList.isEmpty()){
                for (VirusModel virusModel : MainActivity.virusModelInfoList) {
                    if (virusModel.getVirusId() == resultVirusId){
                        MainActivity.requestedVirusModel = virusModel;
                        // collapse the sheet and show the analyse animation
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        new Handler().postDelayed(()->{
                            hideTitleAndButton();
                        }, 200);
                        new Handler().postDelayed(()->{
                            supportFinishAfterTransition();
                        }, 600);
                    }
                }
            }
        });
    }

    // set ControlButton On Click Listener
    private void setControlButtonOnClickListener() {
        controlButton.setOnClickListener(cb ->{
            MainActivity.hasControlStrategyRequest = true;
            // collapse the sheet and show the analyse animation
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            new Handler().postDelayed(()->{
                hideTitleAndButton();
            }, 200);
            new Handler().postDelayed(()->{
                supportFinishAfterTransition();
            }, 600);
        });
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
        if (isResultShow){
            uploadImageBitmap = null;
            uploadImageView.setImageBitmap(null);
            // collapse the sheet and show the analyse animation
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            isResultShow = false;
        }
        new Handler().postDelayed(()->{
            // hide virus-infected results
            illTitleTextView.setVisibility(View.GONE);
            virusButton.setVisibility(View.GONE);
            controlButton.setVisibility(View.GONE);
            // hide title and button for closing
            hideTitleAndButton();
        }, 200);
        new Handler().postDelayed(()->{
            Intent intent = new Intent(DetectActivity.this, CameraActivity.class);
            // animation
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, this.cameraCardView, ViewCompat.getTransitionName(this.cameraCardView));
            startActivity(intent, options.toBundle());
        }, 600);
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
