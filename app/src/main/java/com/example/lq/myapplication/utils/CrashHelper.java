package com.example.lq.myapplication.utils;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by lq on 2018/3/1.
 */

public class CrashHelper implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private static CrashHelper crashHelper;
    public static CrashHelper getInstance() {
        if (crashHelper == null) {
            synchronized (CrashHelper.class) {
                if (crashHelper == null) {
                    crashHelper = new CrashHelper();
                }
            }
        }
        return crashHelper;
    }
    public void init() {
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("aaa","crash");
        handleExceptions(t, e);
    }

    private void handleExceptions(final Thread t, final Throwable e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                Log.e("aaa","success");
                uncaughtExceptionHandler.uncaughtException(t, e);
            }
        }).start();
//        try {
//            int a = 1;
//            int b = a / 0;
//        } catch (Exception ea) {
//
//        }

    }
}
