package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.lq.myapplication.utils.UIHelper;

public class StickyRootView extends RelativeLayout {
    private RecyclerView lv;
    private View view;
    private float dx = 0;
    private float dy = 0;
    private float totaly = 0;

    public StickyRootView(Context context) {
        super(context);
    }

    public StickyRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyRootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = getChildAt(0);
        view.setPivotY(0);
        view.setClickable(true);
        view.setPivotX(UIHelper.getScreenWidth()/2);
        lv = (RecyclerView) getChildAt(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("event","dispatchTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = ev.getX();
                dy = ev.getY();
            case MotionEvent.ACTION_MOVE:
                float mx = ev.getX();
                float my = ev.getY();
                float offx = mx - dx;
                float offy = my - dy;

                if (offy > 0 && isTop()) {
                    totaly += offy;
                    if (totaly > UIHelper.dip2px(15360)) {
                        totaly = 15360;
                    }
                    view.setScaleY((getDampSize(totaly) + view.getHeight()) / view.getHeight());
                    view.setScaleX((getDampSize(totaly) + view.getHeight()) / view.getHeight());
                    lv.setTranslationY(getDampSize(totaly));
                    dx = mx;
                    dy = my;
                }
                if (offy < 0 && totaly > 0) {
                    totaly += offy;
                    if (totaly < 0) {
                        totaly = 0;
                    }
                    view.setScaleY((getDampSize(totaly) + view.getHeight()) / view.getHeight());
                    view.setScaleX((getDampSize(totaly) + view.getHeight()) / view.getHeight());
                    lv.setTranslationY(getDampSize(totaly));
                    dx = mx;
                    dy = my;
                }
                dx = mx;
                dy = my;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("event","onInterceptTouchEvent");

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean isTop() {
        LinearLayoutManager linearLayoutManager = ((LinearLayoutManager)lv.getLayoutManager());
        int pos = linearLayoutManager.findFirstVisibleItemPosition();
        View view = linearLayoutManager.findViewByPosition(pos);
        if (pos == 0 && view != null && view.getTop() == 0) {
            return true;
        }
        return false;
    }

    private float getDampSize(float x) {
        float ret = (UIHelper.dip2px(15360) - x) * x / UIHelper.dip2px(30720);
        Log.e("damp","x:" + x + "return:" + ret);
        return ret;
    }
}
