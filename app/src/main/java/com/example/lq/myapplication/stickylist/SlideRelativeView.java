package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by lq on 2018/3/7.
 */

public class SlideRelativeView extends RelativeLayout {
    private GestureDetector mGestureDetector;

    public SlideRelativeView(Context context) {
        this(context,null);
    }

    public SlideRelativeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideRelativeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent event, float distanceX, float distanceY) {
                Log.e("event", "  " + event.getRawX());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        downY = event.getRawY();
                        totalMove = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getRawX();
                        float moveY = event.getRawY();
                        float dX = moveX - downX;
                        float dY = moveY - downY;
                        totalMove += dX;
                        downX = moveX;
                        downY = moveY;
                        if (view.scroller.getFinalX() - dX > 0) {
                            dX = view.scroller.getFinalX();
                        }
                        if (view.scroller.getFinalX() - dX < -1080) {
                            dX = view.scroller.getFinalX() + 1080;
                        }
                        view.smoothScrollBy(-(int) dX, 0);
                        view2.smoothScrollBy(-(int) dX / 3, 0);
                        return true;
                }
                return super.onScroll(e1, event, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("event", "fling  " + e2.getRawX() + "  " + velocityX);
                isFling = true;
                flingE1 = e1;
                flingE2 = e2;
                flingVX = velocityX;
                flingVY = velocityY;
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    boolean isFling;
    MotionEvent flingE1;
    MotionEvent flingE2;
    float flingVX;
    float flingVY;

    float downX;
    float downY;
    float totalMove;
    ScrollContainView view;
    ScrollContainView view2;
    boolean isClose;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    boolean isIntercept;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = (ScrollContainView)getChildAt(getChildCount() - 1);
        view2 = (ScrollContainView)getChildAt(0);
        view.scrollTo(-1080,0);
        view.scroller.setFinalX(-1080);
        isClose = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isFling = false;
                isIntercept = true;
                downX = event.getRawX();
                downY = event.getRawY();
                totalMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isIntercept) {
                    float moveX = event.getRawX();
                    float moveY = event.getRawY();
                    float dX = moveX - downX;
                    float dY = moveY - downY;
                    totalMove += dX;
                    downX = moveX;
                    downY = moveY;
                    if (Math.abs(dX) != 0 &&  Math.abs(dY) / Math.abs(dX) < 0.5) {
                        if (isClose && dX < 0 || !isClose && dX > 0) {
                            return true;
                        }
                    }
                }
                isIntercept = false;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.e("event","action up");
            finalForm();
        }
        if (flag) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void finalForm() {
        if (isFling) {
            if (flingVX < 0) { // 左滑
                view.smoothScrollTo(0,0);
                view2.smoothScrollTo(1080 / 3, 0);

                isClose = false;
            } else {
                view.smoothScrollTo(-1080,0);
                view2.smoothScrollTo(0, 0);

                isClose = true;

            }
        } else {
            Log.e("event",view.scroller.getFinalX() + " ");
            if (isClose) {
                if (view.scroller.getFinalX() > -1080 / 3 * 2) {
                    view.smoothScrollTo(0,0);
                    view2.smoothScrollTo(1080 / 3, 0);

                    isClose = false;
                } else {
                    view.smoothScrollTo(-1080,0);
                    view2.smoothScrollTo(0, 0);

                    isClose = true;
                }
            } else {
                if (view.scroller.getFinalX() > -1080 / 3) {
                    view.smoothScrollTo(0, 0);
                    view2.smoothScrollTo(1080 / 3, 0);

                    isClose = false;
                } else {
                    view.smoothScrollTo(-1080, 0);
                    view2.smoothScrollTo(0, 0);

                    isClose = true;
                }
            }
        }
    }
}
