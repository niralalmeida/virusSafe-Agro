package com.example.virussafeagro.uitilities;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virussafeagro.OnBoardingActivity;
import com.example.virussafeagro.PasswordActivity;
import com.example.virussafeagro.R;

public class AppAuthentication {

    private static final String APP_PASSWORD = "ta24app";
    public static final int PASSWORD_REQUEST_CODE = 9;
    public static final int PASSWORD_RESULT_OK = 24;

    public static void setAppPassword(OnBoardingActivity onBoardingActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(onBoardingActivity);
        spp.putAppPassword(APP_PASSWORD);
    }

    // password activity
    public static void serAuthenticationAsNo(AppCompatActivity activity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(activity);
        spp.putHasAuthentication("no");
    }

    // onBoarding activity
    public static void checkAuthentication(OnBoardingActivity onBoardingActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(onBoardingActivity);
        boolean hasPasswordFile = spp.findFileByNameInSPDirectory("sp_app_password");
        String authenticationString = spp.getHasAuthentication();

        // test
        System.out.println("authenticationString ==>[" + authenticationString + "]");

        if (hasPasswordFile || authenticationString.equals("") || authenticationString.equals("no")) {
            Intent intent = new Intent(onBoardingActivity, PasswordActivity.class);
            onBoardingActivity.startActivityForResult(intent, PASSWORD_REQUEST_CODE);
            onBoardingActivity.overridePendingTransition(R.anim.activity_slide_in_bottom, 0);
        }

    }

    // password activity
    public static void checkPassword(PasswordActivity passwordActivity, String inputPassword) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(passwordActivity);
        String appPassword = spp.getAppPassword();
        if (inputPassword.equals(appPassword)) {
            spp.putHasAuthentication("yes");

            // change the lock image
            passwordActivity.changeLockImage();

            new Handler().postDelayed(() -> {
                Intent returnIntent = passwordActivity.getIntent();
                returnIntent.putExtra("isFromPasswordActivity", true);
                passwordActivity.setResult(PASSWORD_RESULT_OK, returnIntent);
                passwordActivity.finish();
                passwordActivity.overridePendingTransition(0, R.anim.activity_slide_out_bottom);
            },800);
        } else {
            Toast.makeText(passwordActivity, "Password is wrong! Please input again!", Toast.LENGTH_SHORT).show();
        }
    }
}
