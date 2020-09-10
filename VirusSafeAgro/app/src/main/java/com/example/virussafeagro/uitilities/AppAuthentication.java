package com.example.virussafeagro.uitilities;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virussafeagro.MainActivity;
import com.example.virussafeagro.PasswordActivity;
import com.example.virussafeagro.R;

public class AppAuthentication {

    private static final String APP_PASSWORD = "ta24app";

    public static void setAppPassword(MainActivity mainActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(mainActivity);
        spp.putAppPassword(APP_PASSWORD);
    }

    // password activity
    public static void setAuthenticationAsNo(AppCompatActivity activity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(activity);
        spp.putHasAuthentication("no");
    }

    // main activity
    public static void checkAuthentication(MainActivity mainActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(mainActivity);
        boolean hasPasswordFile = spp.findFileByNameInSPDirectory("sp_app_password");
        String authenticationString = spp.getHasAuthentication();

        if (hasPasswordFile || authenticationString.equals("") || authenticationString.equals("no")) {
            Intent intent = new Intent(mainActivity, PasswordActivity.class);
            mainActivity.startActivityForResult(intent, MainActivity.PASSWORD_REQUEST_CODE);
            mainActivity.overridePendingTransition(R.anim.activity_slide_in_bottom, 0);
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
                returnIntent.putExtra("whereFrom", "password");
                passwordActivity.setResult(MainActivity.PASSWORD_RESULT_OK, returnIntent);
                passwordActivity.finish();
                passwordActivity.overridePendingTransition(0, R.anim.activity_slide_out_bottom);
            },800);
        } else {
            Toast.makeText(passwordActivity, "Password is wrong! Please input again!", Toast.LENGTH_SHORT).show();
        }
    }
}
