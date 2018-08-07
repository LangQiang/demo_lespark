package com.example.lq.myapplication.stickylist;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by lq on 2018/3/2.
 */

public class StickyListView extends ListView {
    View header;
    private ViewGroup.LayoutParams layoutParams;
    private int sHeight;
    private int currentHeight;

    public StickyListView(Context context) {
        super(context);
    }

    public StickyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    float sY;

    float totalY;

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        Log.e("list",ev.getRawX() + "  " + ev.getRawY());
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                sY = ev.getRawY();
//                totalY = 0;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("list",canScrollVertically(-1) + "");
//                float mY = ev.getRawY();
//                float dY = mY - sY;
//
//                if (dY > 0 && !canScrollVertically(-1)) {
//                    totalY += dY;
//                    ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
//                    layoutParams.height = sHeight + (int)((totalY) / (1 + totalY / 1920));
//                    currentHeight = layoutParams.height;
//                    header.setLayoutParams(layoutParams);
//                    sY = mY;
//                    return true;
//                }
//                if (dY < 0 && currentHeight != sHeight && totalY > 0) {
//                    totalY += dY;
//                    if (totalY < 0 ) {
//                        totalY = 0;
//                    }
//                    ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
//                    layoutParams.height = sHeight + (int)((totalY) / (1 + totalY / 1920));
//                    currentHeight = layoutParams.height;
//                    header.setLayoutParams(layoutParams);
//                    sY = mY;
//                    return true;
//                }
//
//                sY = mY;
//                break;
//            case MotionEvent.ACTION_UP:
//                ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight,sHeight);
//                valueAnimator.setDuration(200);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int value = (int) animation.getAnimatedValue();
//                        ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
//                        layoutParams.height = value;
//                        header.setLayoutParams(layoutParams);
//                    }
//                });
//                valueAnimator.start();
//                currentHeight = sHeight;
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }
//
//    @Override
//    public void addHeaderView(View v) {
//        super.addHeaderView(v);
//        header = v;
//        layoutParams = header.getLayoutParams();
//        sHeight = layoutParams.height;
//        currentHeight = sHeight;
//    }
}
