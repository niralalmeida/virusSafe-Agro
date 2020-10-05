package com.example.virussafeagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;

import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.ImageStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class LaunchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_launch);

        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // store image for testing virus check
        //this.storeImageForTestingVirusCheck();

        // open main activity after 4 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, android.R.anim.fade_out);
        },4000);
    }

    private void storeImageForTestingVirusCheck(){
        Bitmap bitmap = DataConverter.drawableImageToBitmap(this, R.drawable.one);
//        storeImageMethod1();

//        storeImageMethod2();

        try {
            ImageStorage.storeImageMethod3(this, bitmap, "tomato_image_1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
