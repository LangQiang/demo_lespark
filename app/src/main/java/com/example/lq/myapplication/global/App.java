package com.example.lq.myapplication.global;

import android.app.Application;

import com.example.lq.myapplication.utils.CrashHelper;

/**
 * Created by lq on 2018/1/20.
 */

public class App extends Application {
    static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CrashHelper.getInstance().init();
    }
    public static App getInstance() {
        return app;
    }
}
