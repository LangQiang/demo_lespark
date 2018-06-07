package com.example.lq.myapplication;

import com.example.lq.myapplication.utils.Tasks;

/**
 * Created by lq on 2018/6/1.
 */

public class C {
    MainActivity.IA ia;
    public void setCallBack(MainActivity.IA callBack) {
        this.ia = callBack;
    }

    public void run() {
        Tasks.postDelayed2MainThread(new Runnable() {
            @Override
            public void run() {
                if (ia != null) {
                    ia.f();
                }
            }
        },2000);
    }
}
