package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

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
    private PreviewView previewView;
    private FloatingActionButton cameraFAB;

    // camera tools
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;
    private Preview preview;
    private CameraSelector cameraSelector;
    private Camera camera;
    private ImageCapture imageCapture;
    private String outputFilePath;
    private Bitmap cameraBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // set activity
        this.cameraActivity = this;
        // initialize views
        this.initializeViews();

        // initialize Camera
        this.initializeCamera();
        // set camera button on click
        this.setCameraButtonOnClickListener();
    }

    private void initializeViews() {
        this.previewView = findViewById(R.id.previewView);
        this.cameraFAB = findViewById(R.id.camera_fab);
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

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation;

                // Monitors orientation values to determine the target rotation value
                if (orientation >= 45 && orientation < 135) {
                    rotation = Surface.ROTATION_270;
                } else if (orientation >= 135 && orientation < 225) {
                    rotation = Surface.ROTATION_180;
                } else if (orientation >= 225 && orientation < 315) {
                    rotation = Surface.ROTATION_90;
                } else {
                    rotation = Surface.ROTATION_0;
                }

                imageCapture.setTargetRotation(rotation);
            }
        };
        orientationEventListener.enable();

        this.camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }

    // set camera button on click
    private void setCameraButtonOnClickListener() {
        this.cameraFAB.setOnClickListener(v -> {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                    System.currentTimeMillis() + ".jpeg");
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, getMainExecutor(), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Uri savedUri = outputFileResults.getSavedUri();
                    if(savedUri == null){
                        savedUri = Uri.fromFile(file);
                    }
                    ContentResolver cr = cameraActivity.getContentResolver();
                    try {
                        cameraBitmap = BitmapFactory.decodeStream(cr.openInputStream(savedUri));
                    } catch (FileNotFoundException e) {
                        Toast.makeText(cameraActivity, "Catch Image fail!!", Toast.LENGTH_SHORT).show();
                    }

                    ImageStorage.saveImage(cameraActivity, cameraBitmap, "Virus Camera", "virus_camera", previewView);

                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
//                    Log.e(TAG, "Photo capture failed: "+exception.getMessage(), exception);
                }
            });
        });
    }
}
