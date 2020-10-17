package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.virussafeagro.uitilities.DataConverter;
import com.example.virussafeagro.uitilities.ImageStorage;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_launch);

        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // store image for testing virus check
//        this.storeImageForTestingVirusCheck();

        // open main activity after 4 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, android.R.anim.fade_out);
        },4000);
    }

    private void storeImageForTestingVirusCheck(){
        Bitmap bitmap = DataConverter.drawableImageToBitmap(this, R.drawable.map);
        try {
//            ImageStorage.saveImageOld(this, bitmap, "myApp");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
