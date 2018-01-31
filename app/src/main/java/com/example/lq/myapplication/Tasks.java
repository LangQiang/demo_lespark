package com.example.lq.myapplication;

import android.os.Handler;
import android.os.HandlerThread;


/**
 * @author huz
 * @since 2015/12/31 11:48
 */
public class Tasks {

    private static final Object sLock = new Object();

    private static Handler sThreadHandler;
    private static Handler sMainHandler;

    public static boolean post2Thread(Runnable r) {
        ensureThreadHandler();
        return sThreadHandler.post(r);
    }

    public static boolean post2MainThread(Runnable r) {
        ensureMainHandler();
        return sMainHandler.post(r);
    }

    public static boolean postDelayed2Thread(Runnable r, long delayMillis) {
        ensureThreadHandler();
        return sThreadHandler.postDelayed(r, delayMillis);
    }


    public static boolean postDelayed2MainThread(Runnable r, long delayMillis) {
        ensureMainHandler();
        return sMainHandler.postDelayed(r, delayMillis);
    }

    public static void cancelThreadTask(Runnable r) {
        ensureThreadHandler();
        sThreadHandler.removeCallbacks(r);
    }

    private static void ensureMainHandler() {
        if (sMainHandler != null) {
            return;
        }
        synchronized (sLock) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(App.getInstance().getMainLooper());
            }
        }
    }
    private static void ensureThreadHandler() {
        if (sThreadHandler != null) {
            return;
        }
        synchronized (sLock) {
            if (sThreadHandler == null) {
                HandlerThread t = new HandlerThread("worker-handler-thread");
                t.start();
                sThreadHandler = new Handler(t.getLooper());
            }
        }
    }
}
