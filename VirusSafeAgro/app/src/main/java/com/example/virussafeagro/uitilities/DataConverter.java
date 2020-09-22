package com.example.virussafeagro.uitilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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

    public static String checkResultVirusRawNameToDisplayFormat(String resultVirusRawFullName) {
        String processedNameWithNoUnderline = resultVirusRawFullName.trim().replace('_', ' ');
        return processedNameWithNoUnderline;
    }

    public static String checkResultVirusRawNameToUpperCaseWithSpace(String resultVirusRawFullName) {
        String processedNameWithNoUnderline = resultVirusRawFullName.trim().replace('_', ' ');
        return processedNameWithNoUnderline.toUpperCase();
    }

    public static String newsTimeToStandardFormat(String originalTimeString, String originalTimePattern, String targetTimePattern) {
        DateFormat df = new SimpleDateFormat(originalTimePattern);  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date date;
        String time = "";
        if (originalTimeString != null) {
            try {
                date = df.parse(originalTimeString);
                SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                Date date1 = df1.parse(date.toString());
                DateFormat df2 = new SimpleDateFormat(targetTimePattern);
                time = df2.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return time;
    }
}
