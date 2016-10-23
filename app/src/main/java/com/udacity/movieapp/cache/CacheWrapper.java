package com.udacity.movieapp.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.udacity.movieapp.constants.C;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by sha on 22/10/16.
 */

public class CacheWrapper {

    public static void addPosterToDiskCache(String fileName, Bitmap imageStream) {
        try {
            File imgFile = new File(getCacheDir(), fileName + C.IMG_EXTENSION);
            imgFile.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(imgFile);
            imageStream.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getPosterFromDiskCache(String fileName) {
        try {
            File cacheDir = getCacheDir();
            if (cacheDir == null) return null;

            File imageFile = new File(cacheDir, fileName + C.IMG_EXTENSION);
            if (!imageFile.exists()) return null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deletePosterFromDiskCache(String fileName) {
       return new File(getCacheDir(), fileName + C.IMG_EXTENSION).delete();
    }

    private static File getCacheDir() {
        if (!isExternalStorageWritable()) return null;
        File dir = new File(Environment.getExternalStorageDirectory() + C.DISK_CACHE_DIR);
        if (!dir.exists()) dir.mkdir();
        return dir;
    }

    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState());
    }
}
