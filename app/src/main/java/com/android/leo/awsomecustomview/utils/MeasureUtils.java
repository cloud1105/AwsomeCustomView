package com.android.leo.awsomecustomview.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by leo on 16/1/1.
 */
public class MeasureUtils {
    public static int getScreenWidth(Activity act){
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity act){
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
