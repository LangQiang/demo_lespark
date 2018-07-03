package com.example.lq.myapplication.global;

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;

/**
 * Created by lq on 2018/2/2.
 */

public class Constant {
    public static final ArrayList<Context> CONTEXTS = new ArrayList<>();

    public static void fun(ArrayList<Integer> a) {
        for (int o : a) {
            SystemClock.sleep(1);
        }
    }
}
