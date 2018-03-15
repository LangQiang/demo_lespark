package com.example.lq.myapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.lq.myapplication.App;

/**
 * Created by lq on 2018/3/12.
 */

public class UIHelper {
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;
    private static int statusBarHeight = 0;
    public static int dip2px(double dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static DisplayMetrics getDisplayMetricsInternal(Context activity) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metric);
        return metric;
    }

    public static int getScreenWidth() {
        if (sScreenWidth == 0) {
            DisplayMetrics metric = getDisplayMetricsInternal(App.getInstance());
            sScreenWidth = metric.widthPixels;
        }
        return sScreenWidth;
    }

    public static int getScreenHeight() {
        if (sScreenHeight == 0) {
            DisplayMetrics metric = getDisplayMetricsInternal(App.getInstance());
            sScreenHeight = metric.heightPixels;
        }
        return sScreenHeight;
    }
}
