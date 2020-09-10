package com.example.virussafeagro.uitilities;

import android.app.Activity;
import android.content.Intent;

import com.example.virussafeagro.PasswordActivity;
import com.example.virussafeagro.R;

public class AppAuthentication {

    public static void checkAuthentication(Activity currentActivity) {
        SharedPreferenceProcess spp = SharedPreferenceProcess.getSharedPreferenceProcessInstance(currentActivity);
        boolean hasPasswordFile = spp.findFileByNameInSPDirectory("sp_app_password");
        String authenticationString = spp.getHasAuthentication();
        if (!hasPasswordFile || authenticationString.equals("no")) {
            Intent intent = new Intent(currentActivity, PasswordActivity.class);
            currentActivity.startActivity(intent);
            currentActivity.overridePendingTransition(R.anim.activity_slide_in_bottom, 0);
        }
    }
}
