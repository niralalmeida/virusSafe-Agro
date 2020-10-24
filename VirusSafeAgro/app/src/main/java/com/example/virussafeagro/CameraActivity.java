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
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
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

import com.example.virussafeagro.animation.MyAnimationBox;
import com.example.virussafeagro.uitilities.ImageStorage;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;
import com.huantansheng.easyphotos.ui.PreviewActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    private CameraActivity cameraActivity;

    // views
    private MotionLayout containerMotionLayout;
    private FrameLayout containerFrameLayout;
    private PreviewView previewView;
    private ImageButton cameraImageButton;
    private ImageView cameraImageView;
    private ImageButton closeImageButton;
    private CardView galleryRetakeCardView;
    private ImageView galleryRetakeImageView;
    private TextView galleryRetakeTextView;
    private CardView tipOpenCameraCardView;
    private ImageView tipOpenCameraImageView;
    private TextView tipOpenCameraTextView;

    // camera tools
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;
    private Preview preview;
    private CameraSelector cameraSelector;
    private Camera camera;
    private ImageCapture imageCapture;
    private String imagePath;
    private Uri savedUri;
    private Bitmap cameraBitmap;

    // tools
    private boolean isRetakeShown;
    private boolean isOpenCameraShown;
    private boolean isCameraImageShown;
    public final static int REQUEST_CHOOSE_GALLERY = 5678;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(100);
        getWindow().setSharedElementEnterTransition(bounds);
        setContentView(R.layout.activity_camera);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set activity
        this.cameraActivity = this;
        // initialize views
        this.initializeViews();

        // initialize Camera
        this.initializeCamera();
        // set camera button on click
        this.setCameraButtonOnClickListener();
        // set close button on click
        this.setCloseImageButtonOnClickListener();
        // set gallery/retake button on click
        this.setGalleryRetakeCardViewOnClickListener();
        // set tip/open camera button on click
        this.setTipOpenCameraCardViewOnClickListener();
    }

    private void initializeViews() {
        this.containerFrameLayout = findViewById(R.id.container);
        this.containerMotionLayout = findViewById(R.id.ml_camera_activity);
        this.previewView = findViewById(R.id.previewView);
        this.cameraImageButton = findViewById(R.id.imgbtn_camera_activity);
        this.cameraImageView = findViewById(R.id.img_camera_image_camera_activity);
        this.closeImageButton = findViewById(R.id.btn_close_camera_activity);
        this.galleryRetakeCardView = findViewById(R.id.cv_gallery_retake_camera_activity);
        this.galleryRetakeImageView = findViewById(R.id.img_gallery_retake_camera_activity);
        this.galleryRetakeTextView = findViewById(R.id.tv_gallery_retake_camera_activity);
        this.tipOpenCameraCardView = findViewById(R.id.cv_tip_open_camera_activity);
        this.tipOpenCameraImageView = findViewById(R.id.img_tip_open_camera_activity);
        this.tipOpenCameraTextView = findViewById(R.id.tv_tip_open_camera_activity);
    }

    // initialize Camera
    private void initializeCamera() {
        boolean hasCameraService = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        if (hasCameraService) {
            // request CameraProvider
            this.cameraProviderFuture = ProcessCameraProvider.getInstance(this);
            // check CameraProvider available
            this.cameraProviderFuture.addListener(() -> {
                try {
                    cameraProvider = cameraProviderFuture.get();
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

        this.cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        this.preview.setSurfaceProvider(previewView.getSurfaceProvider());
//        this.camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);

        // configure ImageCapture
        this.configureImageCapture();
    }

    // configure ImageCapture
    private void configureImageCapture() {
        this.imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();
        this.camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }

    // set camera button on click
    private void setCameraButtonOnClickListener() {
        this.cameraImageButton.setOnClickListener(v -> {
            // take photo
            if (!isCameraImageShown) {
                // make camera button rotate
                makeCameraButtonRotate();
                // set retake button
                isRetakeShown = true;
                isCameraImageShown = true;
                // change the card image
                galleryRetakeImageView.setImageResource(R.drawable.ic_redo);
                // change the card text
                galleryRetakeTextView.setText("Redo");

                new Handler().postDelayed(() -> {
                    cameraImageButton.clearAnimation();
                    takePhoto();
                }, 200);
            } // do analysis
            else {

            }
        });
    }

    private void makeCameraButtonRotate() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.btn_rotate_camera);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);

        if (rotate != null) {
            cameraImageButton.startAnimation(rotate);
        }  else {
            cameraImageButton.setAnimation(rotate);
            cameraImageButton.startAnimation(rotate);
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
                ContentResolver cr = cameraActivity.getContentResolver();
                try {
                    cameraBitmap = BitmapFactory.decodeStream(cr.openInputStream(savedUri));
                } catch (FileNotFoundException e) {
                    Toast.makeText(cameraActivity, "Catch Image fail!!", Toast.LENGTH_SHORT).show();
                }

                // set rotation
                configureRotation();
                // set tint
                cameraImageButton.setImageResource(R.drawable.ic_right_circle_black_50dp);
                // show the image
                previewView.setVisibility(View.INVISIBLE);
                cameraImageView.setImageBitmap(cameraBitmap);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_camera_image, R.id.end_show_camera_image, 200);
                //ImageStorage.saveImage(cameraActivity, cameraBitmap, "Virus Camera", "virus_camera", previewView);
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
                cameraBitmap = rotateImage(cameraBitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                cameraBitmap = rotateImage(cameraBitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                cameraBitmap = rotateImage(cameraBitmap, 270);
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
            // back to the camera page
            if (isRetakeShown){
                isRetakeShown = false;
                isCameraImageShown = false;
                // show preview
                previewView.setVisibility(View.VISIBLE);
                // change the card image
                galleryRetakeImageView.setImageResource(R.drawable.ic_gallery);
                // change the card text
                galleryRetakeTextView.setText("Photos");
                // retake
                cameraImageView.setImageBitmap(null);
                cameraImageButton.setImageResource(R.drawable.ic_camera_black_100dp);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_camera_image, R.id.start_show_camera_image, 200);
            } // open gallery
            else {
                ImagePicker.Companion.with(this)
                        .galleryOnly()	//User can only select image from Gallery
                        .start(REQUEST_CHOOSE_GALLERY);	//Default Request Code is ImagePicker.REQUEST_CODE
            }
        });
    }

    // set tip/open camera button on click
    public void setTipOpenCameraCardViewOnClickListener() {
        this.tipOpenCameraCardView.setOnClickListener(v -> {
            // open tip page
            if (!isOpenCameraShown){

            } // back to camera page
            else {
                // set the photos button
                isOpenCameraShown = false;
                isCameraImageShown = false;
                tipOpenCameraImageView.setImageResource(R.drawable.tip_i);
                tipOpenCameraTextView.setText("Tips");
                // show preview
                previewView.setVisibility(View.VISIBLE);
                // retake
                cameraImageView.setImageBitmap(null);
                cameraImageButton.setImageResource(R.drawable.ic_camera_black_100dp);
                MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.end_show_camera_image, R.id.start_show_camera_image, 200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHOOSE_GALLERY) { //Image Uri will not be null for RESULT_OK
            String imagePath = ImagePicker.Companion.getFilePath(data);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            cameraBitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            // set tint
            cameraImageButton.setImageResource(R.drawable.ic_right_circle_black_50dp);
            isCameraImageShown = true;
            // show the open camera button
            isOpenCameraShown = true;
            tipOpenCameraImageView.setImageResource(R.drawable.ic_add_a_photo_white);
            tipOpenCameraTextView.setText("Camera");
            // show the image
            cameraImageView.setImageBitmap(cameraBitmap);
            previewView.setVisibility(View.INVISIBLE);
            MyAnimationBox.configureTheAnimation(containerMotionLayout, R.id.start_show_camera_image, R.id.end_show_camera_image, 200);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(cameraActivity, ImagePicker.RESULT_ERROR + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(cameraActivity, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    // set close button on click
    public void setCloseImageButtonOnClickListener() {
        this.closeImageButton.setOnClickListener(v -> {
            supportFinishAfterTransition();
        });
    }
}
