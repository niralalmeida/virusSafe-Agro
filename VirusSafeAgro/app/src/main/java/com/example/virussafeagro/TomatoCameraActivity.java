package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virussafeagro.adapters.ListImageGalleryAdapter;
import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.fragments.DetectInstructionsFragment;
import com.example.virussafeagro.fragments.DetectTomatoInstructionsFragment;
import com.example.virussafeagro.models.ImageObject;
import com.example.virussafeagro.models.TomatoFruitDetectResultModel;
import com.example.virussafeagro.networkConnection.NetworkConnectionToMLModel;
import com.example.virussafeagro.uitilities.AppResources;
import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.ImageStorage;
import com.example.virussafeagro.uitilities.MyJsonParser;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.huantansheng.easyphotos.ui.PreviewActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class TomatoCameraActivity extends AppCompatActivity {
    private TomatoCameraActivity tomatoCameraActivity;
    private GettingTomatoFruitImageCheckFeedbackAsyncTask getImageCheckFeedbackAsyncTask;

    // data
    private NetworkConnectionToMLModel networkConnectionToMLModel;

    // views
    private MotionLayout containerMotionLayout;
    private FrameLayout containerFrameLayout;
    private PreviewView previewView;
    private ImageButton tomatoCameraImageButton;
    private ImageView tomatoCameraImageView;
    private ImageButton closeImageButton;
    private CardView galleryRetakeCardView;
    private ImageView galleryRetakeImageView;
    private TextView galleryRetakeTextView;
    private CardView tipOpenTomatoCameraCardView;
    private ImageView tipOpenTomatoCameraImageView;
    private TextView tipOpenTomatoCameraTextView;
    private CardView retakeCameraInResultCardView;
    private TextView tomatoCountResultTextView;
    private TextView tomatoGoodNoResultTextView;
    private TextView tomatoGoodPercentageResultTextView;
    private TextView tomatoBadNoResultTextView;
    private TextView tomatoBadPercentageResultTextView;

    // tomatoCamera tools
    private ListenableFuture<ProcessCameraProvider> tomatoCameraProviderFuture;
    private ProcessCameraProvider tomatoCameraProvider;
    private Preview preview;
    private CameraSelector tomatoCameraSelector;
    private Camera tomatoCamera;
    private ImageCapture imageCapture;
    private String imagePath;
    private Uri savedUri;
    private Bitmap tomatoCameraBitmap;

    // tools
    private boolean isRetakeShown;
    private boolean isOpenTomatoCameraShown;
    private boolean isTomatoCameraImageShown;
    public final static int REQUEST_CHOOSE_GALLERY = 5678;
    private boolean isTomatoImageResultBitmapLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(100);
        getWindow().setSharedElementEnterTransition(bounds);
        setContentView(R.layout.activity_tomato_camera);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set activity
        this.tomatoCameraActivity = this;
        // initialize views
        this.initializeViews();
        // initialize Data
        this.initializeData();

        // initialize TomatoCamera
        this.initializeTomatoCamera();
        // set tomatoCamera button on click
        this.setTomatoCameraButtonOnClickListener();
        // set close button on click
        this.setCloseImageButtonOnClickListener();
        // set gallery/retake button on click
        this.setGalleryRetakeCardViewOnClickListener();
        // set tip/open tomatoCamera button on click
        this.setTipOpenTomatoCameraCardViewOnClickListener();
    }

    private void initializeViews() {
        this.containerFrameLayout = findViewById(R.id.container);
        this.containerMotionLayout = findViewById(R.id.ml_tomato_camera_activity);
        this.previewView = findViewById(R.id.previewView);
        this.tomatoCameraImageButton = findViewById(R.id.imgbtn_tomato_camera_activity);
        this.tomatoCameraImageView = findViewById(R.id.img_tomato_camera_image_tomato_camera_activity);
        this.closeImageButton = findViewById(R.id.btn_close_tomato_camera_activity);
        this.galleryRetakeCardView = findViewById(R.id.cv_gallery_retake_tomato_camera_activity);
        this.galleryRetakeImageView = findViewById(R.id.img_gallery_retake_tomato_camera_activity);
        this.galleryRetakeTextView = findViewById(R.id.tv_gallery_retake_tomato_camera_activity);
        this.tipOpenTomatoCameraCardView = findViewById(R.id.cv_tip_open_tomato_camera_activity);
        this.tipOpenTomatoCameraImageView = findViewById(R.id.img_tip_open_tomato_camera_activity);
        this.tipOpenTomatoCameraTextView = findViewById(R.id.tv_tip_open_tomato_camera_activity);
        this.retakeCameraInResultCardView = findViewById(R.id.cv_big_retake_button_tomato_detect);
        this.tomatoCountResultTextView = findViewById(R.id.tv_count_result_tomato_detect);
        this.tomatoGoodNoResultTextView = findViewById(R.id.tv_good_count_no_tomato_detect);
        this.tomatoGoodPercentageResultTextView = findViewById(R.id.tv_good_count_percentage_tomato_detect);
        this.tomatoBadNoResultTextView = findViewById(R.id.tv_bad_count_no_tomato_detect);
        this.tomatoBadPercentageResultTextView = findViewById(R.id.tv_bad_count_percentage_tomato_detect);
    }

    private void initializeData() {
        networkConnectionToMLModel = new NetworkConnectionToMLModel();
    }

    // initialize TomatoCamera
    private void initializeTomatoCamera() {
        boolean hasTomatoCameraService = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        if (hasTomatoCameraService) {
            // request CameraProvider
            this.tomatoCameraProviderFuture = ProcessCameraProvider.getInstance(this);
            // check CameraProvider available
            this.tomatoCameraProviderFuture.addListener(() -> {
                try {
                    tomatoCameraProvider = tomatoCameraProviderFuture.get();
                    bindPreview();
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }, ContextCompat.getMainExecutor(this));
        }
    }

    // bind Preview
    private void bindPreview() {
        this.preview = new Preview.Builder()
                .build();

        this.tomatoCameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        this.preview.setSurfaceProvider(previewView.getSurfaceProvider());
//        this.tomatoCamera = tomatoCameraProvider.bindToLifecycle(this, tomatoCameraSelector, preview);

        // configure ImageCapture
        this.configureImageCapture();
    }

    // configure ImageCapture
    private void configureImageCapture() {
        this.imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        this.tomatoCamera = tomatoCameraProvider.bindToLifecycle(this, tomatoCameraSelector, imageCapture, preview);
    }

    // set tomatoCamera button on click
    private void setTomatoCameraButtonOnClickListener() {
        this.tomatoCameraImageButton.setOnClickListener(v -> {
            // take photo
            if (!isTomatoCameraImageShown) {
                // make tomatoCamera button rotate
                makeTomatoCameraButtonRotate();
                // set retake button
                isRetakeShown = true;
                isTomatoCameraImageShown = true;
                // change the card image
                galleryRetakeImageView.setImageResource(R.drawable.ic_redo);
                // change the card text
                galleryRetakeTextView.setText("Retake");

                new Handler().postDelayed(() -> {
                    tomatoCameraImageButton.clearAnimation();
                    takePhoto();
                }, 200);
            }
            // do analysis
            else {
                // stop the camera
                tomatoCameraProvider.unbindAll();

                // start tomato detecting
                uploadTomatoFruitImageAndDoDetecting();

                // show the scanning lottie + hide buttons
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_run_scan_tomato_image, R.id.end_run_scan_tomato_image, 200);
            }
        });
    }

    // upload the image and do the detect
    private void uploadTomatoFruitImageAndDoDetecting(){
        getImageCheckFeedbackAsyncTask = new GettingTomatoFruitImageCheckFeedbackAsyncTask();
        getImageCheckFeedbackAsyncTask.execute(tomatoCameraBitmap);
    }
    // AsyncTask for detecting
    private class GettingTomatoFruitImageCheckFeedbackAsyncTask extends AsyncTask<Bitmap, Void, TomatoFruitDetectResultModel> {
        @Override
        protected TomatoFruitDetectResultModel doInBackground(Bitmap... bitmaps) {
            TomatoFruitDetectResultModel tomatoFruitDetectResultModel = new TomatoFruitDetectResultModel();
            Bitmap uploadTomatoCameraBitmap = bitmaps[0];
            String uploadTomatoImageBitmapString = DataConverter.bitmapToStringConverter(uploadTomatoCameraBitmap);
            ImageObject imageObject = new ImageObject(uploadTomatoImageBitmapString);
            try {
                String rawFeedback = networkConnectionToMLModel.getTomatoImageCheckFeedback(imageObject);
                tomatoFruitDetectResultModel = MyJsonParser.tomatoImageCheckFeedbackJsonParser(rawFeedback);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tomatoFruitDetectResultModel;
        }

        @Override
        protected void onPostExecute(TomatoFruitDetectResultModel resultTomatoFruitDetectResultModel) {
            if (resultTomatoFruitDetectResultModel != null) {
                if (!resultTomatoFruitDetectResultModel.getTomatoDetectResultImageStringURL().equals(MyJsonParser.SERVER_STOP_MESSAGE)) {
                    // show the results
                    showDetectTomatoImageResult(resultTomatoFruitDetectResultModel);
                }
                // server stops working
                else {
                    // set image
                    tomatoCameraImageView.setImageResource(R.drawable.error);
                    // set error result text
                    tomatoCountResultTextView.setText("Server stopped");
                    // move the image top
                    MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_tomato_detect_result, R.id.end_show_tomato_detect_result, 200);
                    // set RetakeCameraInResultCardView On Click Listener
                    setRetakeCameraInResultCardViewOnClickListener();
                }
            }
            // something wrong with the ML model
            else {
                Toast.makeText(tomatoCameraActivity, "Something wrong with the server!", Toast.LENGTH_SHORT).show();
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_tomato_detect_result, R.id.end_show_tomato_camera_image, 200);
            }
        }
    }

    private void resumeCamera() {
        isRetakeShown = false;
        isTomatoCameraImageShown = false;
        // bind camera again
        tomatoCamera = tomatoCameraProvider.bindToLifecycle(tomatoCameraActivity, tomatoCameraSelector, imageCapture, preview);
        // show preview
        previewView.setVisibility(View.VISIBLE);
        // change the card image
        galleryRetakeImageView.setImageResource(R.drawable.ic_gallery);
        // change the card text
        galleryRetakeTextView.setText("Photos");
        // retake
        tomatoCameraImageView.setImageBitmap(null);
        tomatoCameraImageButton.setImageResource(R.drawable.ic_camera_black_100dp);
    }

    private void showDetectTomatoImageResult(TomatoFruitDetectResultModel resultTomatoFruitDetectResultModel) {
        // set image
        tomatoCameraImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // show the result image
        Picasso.get()
            .load(resultTomatoFruitDetectResultModel.getTomatoDetectResultImageStringURL())
            .resize(
                    DataConverter.dip2px(tomatoCameraActivity, 300),
                    DataConverter.dip2px(tomatoCameraActivity, 300)
            )
            .placeholder(R.color.bg_cart_image_gallery)
            .into(tomatoCameraImageView, new Callback() {
                @Override
                public void onSuccess() {
                    isTomatoImageResultBitmapLoaded = true;
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(tomatoCameraActivity, "Fail to load the result image", Toast.LENGTH_SHORT).show();
                    tomatoCameraImageView.setImageBitmap(tomatoCameraBitmap);
                }
            });

        // set count result text
        String tomatoCountResultString = "" + resultTomatoFruitDetectResultModel.getTomatoCount();
        tomatoCountResultTextView.setText(tomatoCountResultString);
        // set good result text
        String goodResultNoString = "" + resultTomatoFruitDetectResultModel.getGoodCount();
        tomatoGoodNoResultTextView.setText(goodResultNoString);
        String goodResultPercentageString = resultTomatoFruitDetectResultModel.getGoodCountPercentage() + "% of total";
        tomatoGoodPercentageResultTextView.setText(goodResultPercentageString);
        // set bad result text
        String badResultNoString = "" + resultTomatoFruitDetectResultModel.getBadCount();
        tomatoBadNoResultTextView.setText(badResultNoString);
        String badResultPercentageString = resultTomatoFruitDetectResultModel.getBadCountPercentage() + "% of total";
        tomatoBadPercentageResultTextView.setText(badResultPercentageString);
        // move the image top
        MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_tomato_detect_result, R.id.end_show_tomato_detect_result, 200);

        // set TomatoCameraImageView On Click Listener
        setTomatoCameraImageViewOnClickListener();
        // set RetakeCameraInResultCardView On Click Listener
        setRetakeCameraInResultCardViewOnClickListener();
    }

    // set TomatoCameraImageView On Click Listener
    private void setTomatoCameraImageViewOnClickListener() {
        tomatoCameraImageView.setOnClickListener(tciv ->{
            if (isTomatoImageResultBitmapLoaded){
                BitmapDrawable tomatoCameraImageBitmapBitmapDrawable = (BitmapDrawable) tomatoCameraImageView.getDrawable();
                // set tomato image bitmap
                ImageViewActivity.currentImageBitmap = tomatoCameraImageBitmapBitmapDrawable.getBitmap();
                // open the image view activity
                Intent intent = new Intent(tomatoCameraActivity, ImageViewActivity.class);
                // animation
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(tomatoCameraActivity, tomatoCameraImageView, ViewCompat.getTransitionName(tomatoCameraImageView));
                tomatoCameraActivity.startActivity(intent, options.toBundle());
            } else {
                Toast.makeText(tomatoCameraActivity, "Image is loading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // set RetakeCameraInResultCardView On Click Listener
    private void setRetakeCameraInResultCardViewOnClickListener() {
        retakeCameraInResultCardView.setOnClickListener(rcircv ->{
            isTomatoImageResultBitmapLoaded = false;
            resumeCamera();
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_tomato_detect_result, R.id.start_show_tomato_camera_image, 200);
        });
    }

    private void makeTomatoCameraButtonRotate() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.btn_rotate_camera);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);

        if (rotate != null) {
            tomatoCameraImageButton.startAnimation(rotate);
        }  else {
            tomatoCameraImageButton.setAnimation(rotate);
            tomatoCameraImageButton.startAnimation(rotate);
        }
    }

    private void takePhoto() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                System.currentTimeMillis() + ".jpeg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, getMainExecutor(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                savedUri = outputFileResults.getSavedUri();
                if(savedUri == null){
                    savedUri = Uri.fromFile(file);
                    imagePath = savedUri.getPath();
                }
                ContentResolver cr = tomatoCameraActivity.getContentResolver();
                try {
                    tomatoCameraBitmap = BitmapFactory.decodeStream(cr.openInputStream(savedUri));
                } catch (FileNotFoundException e) {
                    Toast.makeText(tomatoCameraActivity, "Catch Image fail!!", Toast.LENGTH_SHORT).show();
                }

                // set rotation
                configureRotation();
                // set tint
                tomatoCameraImageButton.setImageResource(R.drawable.ic_right_circle_black_50dp);
                // show the image
                previewView.setVisibility(View.INVISIBLE);
                tomatoCameraImageView.setImageBitmap(tomatoCameraBitmap);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_tomato_camera_image, R.id.end_show_tomato_camera_image, 200);
                // stop the camera
                tomatoCameraProvider.unbindAll();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
//                    Log.e(TAG, "Photo capture failed: "+exception.getMessage(), exception);
            }
        });
    }

    private void configureRotation() {
        int orientation = -1;
        try {
            ExifInterface ei = new ExifInterface(imagePath);
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

        } catch (Exception e){
            e.printStackTrace();
        }
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                tomatoCameraBitmap = rotateImage(tomatoCameraBitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                tomatoCameraBitmap = rotateImage(tomatoCameraBitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                tomatoCameraBitmap = rotateImage(tomatoCameraBitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // set gallery/retake button on click
    public void setGalleryRetakeCardViewOnClickListener() {
        this.galleryRetakeCardView.setOnClickListener(v ->{
            // back to the tomatoCamera page
            if (isRetakeShown){
                resumeCamera();
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_tomato_camera_image, R.id.start_show_tomato_camera_image, 200);
            }
            // open gallery
            else {
                ImagePicker.Companion.with(this)
                        .galleryOnly()	//User can only select image from Gallery
                        .start(REQUEST_CHOOSE_GALLERY);	//Default Request Code is ImagePicker.REQUEST_CODE
            }
        });
    }

    // set tip/open tomatoCamera button on click
    public void setTipOpenTomatoCameraCardViewOnClickListener() {
        this.tipOpenTomatoCameraCardView.setOnClickListener(v -> {
            // open tip page
            if (!isOpenTomatoCameraShown){
                DetectTomatoInstructionsFragment detectTomatoInstructionsFragment = new DetectTomatoInstructionsFragment();
                detectTomatoInstructionsFragment.show(getSupportFragmentManager(), AppResources.FRAGMENT_TAG_TOMATO_DETECT);
            } // back to camera page
            else {
                // bind camera again
                tomatoCamera = tomatoCameraProvider.bindToLifecycle(tomatoCameraActivity, tomatoCameraSelector, imageCapture, preview);
                // set the photos button
                isOpenTomatoCameraShown = false;
                isTomatoCameraImageShown = false;
                tipOpenTomatoCameraImageView.setImageResource(R.drawable.tip_i);
                tipOpenTomatoCameraTextView.setText("Instructions");
                // show preview
                previewView.setVisibility(View.VISIBLE);
                // retake
                tomatoCameraImageView.setImageBitmap(null);
                tomatoCameraImageButton.setImageResource(R.drawable.ic_camera_black_100dp);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_tomato_camera_image, R.id.start_show_tomato_camera_image, 200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHOOSE_GALLERY) { //Image Uri will not be null for RESULT_OK
            String imagePath = ImagePicker.Companion.getFilePath(data);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            tomatoCameraBitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            if (tomatoCameraBitmap != null) {
                // set tint
                tomatoCameraImageButton.setImageResource(R.drawable.ic_right_circle_black_50dp);
                isTomatoCameraImageShown = true;
                // show the open camera button
                isOpenTomatoCameraShown = true;
                tipOpenTomatoCameraImageView.setImageResource(R.drawable.ic_add_a_photo_white);
                tipOpenTomatoCameraTextView.setText("Camera");
                // show the image
                tomatoCameraImageView.setImageBitmap(tomatoCameraBitmap);
                previewView.setVisibility(View.INVISIBLE);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_tomato_camera_image, R.id.end_show_tomato_camera_image, 200);
            } else {
                Toast.makeText(tomatoCameraActivity, "Image not Selected", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(tomatoCameraActivity, ImagePicker.RESULT_ERROR + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(tomatoCameraActivity, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    // set close button on click
    public void setCloseImageButtonOnClickListener() {
        this.closeImageButton.setOnClickListener(v -> {
//            supportFinishAfterTransition();
            finish();
            overridePendingTransition(0, R.anim.activity_slide_out_top);
        });
    }
}
