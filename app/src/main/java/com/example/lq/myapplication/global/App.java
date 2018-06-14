package com.example.lq.myapplication.global;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.lq.myapplication.utils.CrashHelper;
import com.example.lq.myapplication.utils.Tasks;
import com.example.lq.myapplication.utils.ToastUtil2;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by lq on 2018/1/20.
 */

public class App extends Application {
    public static App app;
    public ReferenceQueue referenceQueue;
    public static final Stack<Activity> STACK = new Stack<>();
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CrashHelper.getInstance().init();
        referenceQueue = new ReferenceQueue();

        registerActivityLifecycleCallbacks(new GlobalActivityLifeCycleCallback());
    }
    public static App getInstance() {
        return app;
    }

    private class GlobalActivityLifeCycleCallback implements ActivityLifecycleCallbacks{

        private WeakReference weakReference;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.e("activity",activity.getLocalClassName() + "  --onActivityCreated");
            //STACK.push(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ToastUtil2.showToast(activity.getLocalClassName() + "  Destroyed");
            Log.e("activity",activity.getLocalClassName() + "  --onActivityDestroyed");
            final String name = activity.getLocalClassName();
            //Activity activity1 = STACK.pop();
            weakReference = new WeakReference(activity, referenceQueue);
            Tasks.postDelayed2Thread(new Runnable() {
                @Override
                public void run() {
                    Reference poll = referenceQueue.poll();
                    Log.e("activity",(poll == null) + "  --gc--  " + (poll == weakReference));
                    if (poll == null) {
                        System.gc();
                        System.runFinalization();
                        poll = referenceQueue.poll();
                        Log.e("activity",(poll == null) + "  --gc--  " + (poll == weakReference));
                        if (poll == null) {
                            ToastUtil2.showToast(name + " 存在内存泄露");
                        }
                    }
                }
            },5000);
//            System.gc();
//            System.runFinalization();
//            Reference poll = referenceQueue.poll();
//            Log.e("activity",(poll == null) + "  --gc--  " + (poll == weakReference));
        }
    }

    public void invokeTestFun() {
        iTest.test();
    }

    public void setITest(ITest iTest) {
        this.iTest = iTest;
    }

    public interface ITest{
        void test();
    }

    private ITest iTest;
}
