package com.example.lq.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil2 {
    private static String oldMsg ;
    private static Toast toast = null ;
    private static long oneTime = 0 ;
    private static long twoTime = 0 ;

    public static void showToast(String message) {
        showToast(App.getInstance().getApplicationContext(), message, false);
    }

    public static void showToast(final Context context, final String message, final boolean isLong){
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Tasks.post2MainThread(new Runnable() {
            @Override
            public void run() {
                if (context != null) {
                    if(toast == null){
                        toast = Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                        toast.show();
                        oneTime = System.currentTimeMillis() ;
                    }else{
                        twoTime = System.currentTimeMillis() ;
                        if(message.equals(oldMsg)){
                            if(twoTime - oneTime > Toast.LENGTH_SHORT){
                                toast.show() ;
                            }
                        }else{
                            oldMsg = message ;
                            toast.setText(message) ;
                            toast.show() ;
                        }
                    }
                    oneTime = twoTime ;
                }
            }
        });
    }

    public static void showToast(Context context, String message){
        showToast(context, message, false);
    }
}
