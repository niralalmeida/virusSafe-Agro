package com.example.virussafeagro.uitilities;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.virussafeagro.PasswordActivity;
import com.example.virussafeagro.R;

public class AppAuthentication {

    public static final int PASSWORD_REQUEST_CODE = 9;
    public static final int PASSWORD_RESULT_OK = 24;

    public static void setAppPassword(Activity currentActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(currentActivity);
        spp.putAppPassword("ta24app");
    }

    public static void serAuthentication(Activity currentActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(currentActivity);
        spp.putHasAuthentication("entered");
    }

    // onBoarding activity
    public static void checkAuthentication(Activity currentActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(currentActivity);
        boolean hasPasswordFile = spp.findFileByNameInSPDirectory("sp_app_password");
        String authenticationString = spp.getHasAuthentication();

        if (hasPasswordFile || authenticationString.equals("") || authenticationString.equals("entered")) {
            Intent intent = new Intent(currentActivity, PasswordActivity.class);
            currentActivity.startActivityForResult(intent, PASSWORD_REQUEST_CODE);
            currentActivity.overridePendingTransition(R.anim.activity_slide_in_bottom, 0);
        }
    }

    // password activity
    public static void checkPassword(Activity currentActivity, String inputPassword) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(currentActivity);
        String appPassword = spp.getAppPassword();
        if (inputPassword.equals(appPassword)) {
            spp.putHasAuthentication("has");
            Intent returnIntent = currentActivity.getIntent();
            currentActivity.setResult(PASSWORD_RESULT_OK, returnIntent);
            currentActivity.finish();
            currentActivity.overridePendingTransition(0, R.anim.activity_slide_out_bottom);
        } else {
            Toast.makeText(currentActivity, "Password is wrong! Please input again!", Toast.LENGTH_SHORT).show();
        }
    }
}
