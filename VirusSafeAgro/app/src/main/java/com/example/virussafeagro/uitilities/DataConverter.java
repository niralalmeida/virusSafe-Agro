package com.example.virussafeagro.uitilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class DataConverter {

    public static Bitmap stringToBitmapConverter(String s){
        Bitmap bitmap = null;
        if ((s != null) && (!s.isEmpty())){
            byte[] byteArray= Base64.decode(s, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        }
        return bitmap;
    }

    public static String bitmapToStringConverter(Bitmap b){
        String bitmapStr = "";
        if (b != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bitmapStr = android.util.Base64.encodeToString(bos.toByteArray(), android.util.Base64.DEFAULT);
        }
        return bitmapStr;
    }

    public static Bitmap drawableImageToBitmap(FragmentActivity fragmentActivity, int drawableId) {
        InputStream is = fragmentActivity.getResources().openRawResource(drawableId);
        return BitmapFactory.decodeStream(is);
    }

    public static boolean isSameImage(BitmapDrawable imageViewDrawable, FragmentActivity fragmentActivity, int drawableId){
        Drawable.ConstantState drawableCs = fragmentActivity.getResources().getDrawable(drawableId).getConstantState();
        return imageViewDrawable.getConstantState().equals(drawableCs);
    }

    public static String checkResultVirusRawNameToUpperCaseWithSpace(String resultVirusRawFullName) {
        String processedNameWithNoUnderline = resultVirusRawFullName.trim().replace('_', ' ');
        return processedNameWithNoUnderline.toUpperCase();
    }
}
