package com.example.virussafeagro.uitilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.virussafeagro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class ImageStorage {
    private static String IMAGES_FOLDER_NAME = "/MyTomatoImages";

    public static void storeImageMethod3(Activity activity, Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = activity.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + IMAGES_FOLDER_NAME);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + IMAGES_FOLDER_NAME;

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name + ".png");
            fos = new FileOutputStream(image);

        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    public static void storeImageMethod2(Activity activity, Bitmap bitmap){
        FileOutputStream outputStream = null;
        File dir = new File(getFilesPath(activity) + "/MyTomatoImages");
        dir.mkdirs();

        String filename = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir, filename);
        try {
            outputStream = new FileOutputStream(outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        try {
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getFilesPath( Context context ){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //external storage is available
            filePath = Objects.requireNonNull(context.getExternalFilesDir(null)).getPath();
        }else {
            //external storage is unavailable
            filePath = context.getFilesDir().getPath() ;
        }
        return filePath ;
    }

    public static void storeImageMethod1(Activity activity) {
        // get image bitmap
        Bitmap bitmap1 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.one);
        Bitmap bitmap2 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.two);
        Bitmap bitmap3 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.three);
        Bitmap bitmap4 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.four);
        Bitmap bitmap5 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.five);
        Bitmap bitmap6 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.six);
        Bitmap bitmap7 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.seven);
        Bitmap bitmap8 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.eight);
        Bitmap bitmap9 = DataConverter.drawableImageToBitmap((FragmentActivity)activity, R.drawable.nine);

        // store the image
//        FileOutputStream outputStream = null;
//        File file = Environment.getExternalStorageDirectory();

        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap1, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap2, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap3, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap4, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap5, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap6, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap7, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap8, "leaf1" , "tomato leaf");
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap9, "leaf1" , "tomato leaf");
    }
}
