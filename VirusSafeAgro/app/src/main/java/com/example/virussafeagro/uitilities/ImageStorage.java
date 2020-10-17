package com.example.virussafeagro.uitilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.utils.Utils;
import com.example.virussafeagro.LaunchActivity;
import com.example.virussafeagro.R;

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

    public static void saveImage(Context context, Bitmap bitmap, String folderName) throws IOException {
        String RELATIVE_PATH = Environment.DIRECTORY_PICTURES + File.separator + folderName;
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, generateImageName());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, RELATIVE_PATH);

        // save the image
        writePictureToFile(context, contentValues, DataConverter.bitmapToBytes(bitmap));
    }

    private static String generateImageName() {
        imageCount++;
        return "image_" + imageCount;
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
}
