package com.example.lq.myapplication.utils;

import android.util.Log;

import java.util.Map;

/**
 * Created by lq on 2018/5/2.
 */

public class ThreadCountUtil {
    public static void printThreadInfo() {
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        Log.e("albertThreadDebug"," ");
        Log.e("albertThreadDebug"," ");
        Log.e("albertThreadDebug"," ");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","==================all start===================");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","==============================================");
        for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackElements = entry.getValue();
            Log.e("albertThreadDebug"," ");
            Log.e("albertThreadDebug","name:"+thread.getName()+" id:"+thread.getId()+" thread:"+thread.getPriority()+" begin==========");
            for (int i = 0; i < stackElements.length; i++) {
                StringBuilder stringBuilder = new StringBuilder("    ");
                stringBuilder.append(stackElements[i].getClassName()+".")
                        .append(stackElements[i].getMethodName()+"(")
                        .append(stackElements[i].getFileName()+":")
                        .append(stackElements[i].getLineNumber()+")");
                Log.e("albertThreadDebug",stringBuilder.toString());
            }
            Log.e("albertThreadDebug","name:"+thread.getName()+" id:"+thread.getId()+" thread:"+thread.getPriority()+" end==========");
            Log.e("albertThreadDebug"," ");
        }
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","===================all end====================");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug","==============================================");
        Log.e("albertThreadDebug"," ");
        Log.e("albertThreadDebug"," ");
        Log.e("albertThreadDebug"," ");
    }
}
