package com.example.lq.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.lq.myapplication.global.App;

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

    public static void showKeyboard(Activity activity, EditText mEditTextContent) {
        if (mEditTextContent == null || activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(mEditTextContent, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity, EditText mEditTextContent) {
        if (mEditTextContent == null || activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
