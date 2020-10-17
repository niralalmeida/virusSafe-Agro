package com.example.virussafeagro.uitilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.utils.Utils;
import com.example.virussafeagro.LaunchActivity;
import com.example.virussafeagro.R;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.utils.bitmap.SaveBitmapCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Date;
import java.util.Objects;

public class ImageStorage {
    private static String IMAGES_FOLDER_NAME = "/MyTomatoImages";
    public static int imageCount;

    public static void saveImageOld(Context context, Bitmap bitmap, String folderName){
        String RELATIVE_PATH = Environment.DIRECTORY_PICTURES + File.separator + folderName;
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, generateImageName());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, RELATIVE_PATH);

        // save the image
        try {
            writePictureToFile(context, contentValues, DataConverter.bitmapToBytes(bitmap));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String generateImageName() {
        imageCount++;
        return "new_image_" + imageCount;
    }

    @WorkerThread
    private static Uri writePictureToFile(Context context, ContentValues contentValues, byte[] bitmapBytes) throws IOException {
        final ContentResolver resolver = context.getContentResolver();

        Uri uri = null;
        final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        try {
            uri = resolver.insert(contentUri, contentValues);

            if (uri == null) {
                throw new IOException("Failed to create new MediaStore record.");
            }

            OutputStream stream = resolver.openOutputStream(uri);

            if (stream == null) {
                throw new IOException("Failed to get output stream.");
            }

            stream.write(bitmapBytes);

            Toast.makeText(context, "Image Stored", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            // Delete the content from the media store
            if (uri != null) {
                resolver.delete(uri, null, null);
            }
            throw e;
        }
        return uri;
    }

    // save the image
    public static void saveImage(Activity activity, Bitmap bitmap, String folderName, String fileNamePrefix) {
        EasyPhotos.saveBitmapToDir(
                activity,
                Environment.DIRECTORY_PICTURES + File.separator + folderName,
                fileNamePrefix, bitmap,
                true,
                new SaveBitmapCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        Toast.makeText(activity, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIOFailed(IOException exception) {
                        Toast.makeText(activity, "Saved Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCreateDirFailed() {
                        Toast.makeText(activity, "Folder Created Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // normal
    public static Bitmap screenshotGetBitmapByView(View view) {
        Bitmap bitmap = EasyPhotos.createBitmapFromView(view);
        return bitmap;
    }

    // get the long screenshot
    public static Bitmap longScreenshotGetBitmapByView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // get the actual height of the listView
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }

        // create bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    public static Bitmap getBitmapFromView(Activity activity, View view) {
        if (view == null) return null;
        Bitmap bitmap;
        // request convert
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // get the location of the layout
            final int[] location = new int[2];
            view.getLocationInWindow(location);
            // prepare a bitmap object for draw the copied zone into it
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, true);
            PixelCopy.request(activity.getWindow(),
                    new Rect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight()),
                    bitmap, copyResult -> {
                        // if successful
                        if (copyResult == PixelCopy.SUCCESS) {
                            // save the image
//                            saveImageOld(activity, bitmap, "virus");
                        }
                    }, new Handler(Looper.getMainLooper()));
        } else {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
            view.layout((int) view.getX(), (int) view.getY(),
                    (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredWidth());
            bitmap = Bitmap.createBitmap(view.getDrawingCache(),
                    0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
        }
        return bitmap;
    }


}
