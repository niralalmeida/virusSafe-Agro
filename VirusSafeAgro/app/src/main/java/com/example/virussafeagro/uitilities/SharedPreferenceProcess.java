package com.example.virussafeagro.uitilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class SharedPreferenceProcess {
    private static SharedPreferenceProcess sharedPreferenceProcess;
    private static SharedPreferences virusCheckSP;

    private static final String FILE_NAME_VIRUS_CHECK_INFO = "sp_virus_check_info";

    private static final String KEY_VIRUS_CHECK_IMAGE = "virus_check_image";

    private SharedPreferenceProcess(Context context){
        virusCheckSP = context.getSharedPreferences(FILE_NAME_VIRUS_CHECK_INFO, Context.MODE_PRIVATE);
    }

    // get SharedPreferenceProcess instance
    public static SharedPreferenceProcess getSharedPreferenceProcessInstance(Context context){
        if (sharedPreferenceProcess == null){
            sharedPreferenceProcess = new SharedPreferenceProcess(context);
        }
        return sharedPreferenceProcess;
    }

    public void putCurrentVirusCheckImage(Bitmap bitmap) {
        SharedPreferences.Editor edit = virusCheckSP.edit();
        String virusCheckImageBitmapString = DataConverter.bitmapToStringConverter(bitmap);
        edit.putString(KEY_VIRUS_CHECK_IMAGE, virusCheckImageBitmapString).apply();
    }

    public Bitmap getCurrentVirusCheckImage(){
        String virusCheckImageBitmapString = virusCheckSP.getString(KEY_VIRUS_CHECK_IMAGE, "");
        Bitmap virusCheckImageBitmapBitmap = DataConverter.stringToBitmapConverter(virusCheckImageBitmapString);
        return virusCheckImageBitmapBitmap;
    }
}
