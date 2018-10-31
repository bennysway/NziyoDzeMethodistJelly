package com.seven.clip.nziyodzemethodist.util;

/**
 * Created by bennysway on 07.06.17.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public final class BitmapUtils
{
    private static final int DEFAULT_SAMPLE_SIZE = 1;
    private static int DESAMPLING_SIZE = 2;
    private static final String TAG = "BitmapUtils";

    public static Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight,int desample)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        DESAMPLING_SIZE = desample;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId,opts);
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, opts);
    }

    public static Bitmap decodeSampledBitmapFromFile(String pathName,int reqWidth,int reqHeight,int desample)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        DESAMPLING_SIZE = desample;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opts);
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        opts.inJustDecodeBounds = false;
        Log.i(TAG,"OPTS = "+opts.inSampleSize);
        return BitmapFactory.decodeFile(pathName, opts);
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data,int reqWidth,int reqHeight,int desample)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        DESAMPLING_SIZE = desample;
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream in,int reqWidth,int reqHeight,int desample)
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DESAMPLING_SIZE = desample;
        byte[] data = null;
        try
        {
            int len = 0;
            byte[] buf = new byte[1024];
            while((len = in.read(buf)) != -1)
            {
                bout.write(buf, 0, len);
            }
            data = bout.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return decodeSampledBitmapFromByteArray(data, reqWidth, reqHeight,DESAMPLING_SIZE);
    }


    private static int calculateInSampleSize(BitmapFactory.Options opts, int reqWidth, int reqHeight)
    {
        if(opts == null)
            return DEFAULT_SAMPLE_SIZE;
        int width = opts.outWidth;
        int height = opts.outHeight;
        int sampleSize = DEFAULT_SAMPLE_SIZE;
        if(width > reqWidth || height > reqHeight)
        {
            int widthRatio = (int) (width/(float)reqWidth);
            int heightRatio = (int) (height/(float)reqHeight);

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / sampleSize) >= reqHeight
                    && (halfWidth / sampleSize) >= reqWidth) {
                sampleSize *= DESAMPLING_SIZE;
            }
        }
        return sampleSize;
    }
}