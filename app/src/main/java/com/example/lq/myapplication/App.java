package com.example.lq.myapplication;

import android.app.Application;

/**
 * Created by wx on 2018/1/20.
 */

public class App extends Application {
    static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    public static App getInstance() {
        return app;
    }
}
