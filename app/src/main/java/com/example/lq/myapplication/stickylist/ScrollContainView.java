package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by lq on 2018/3/8.
 */

public class ScrollContainView extends RelativeLayout {
    Scroller scroller;
    public ScrollContainView(Context context) {
        super(context);
        scroller = new Scroller(getContext());
    }

    public ScrollContainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(getContext());

    }

    public ScrollContainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(getContext());

    }

    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - scroller.getFinalX();
        int dy = fy - scroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    public void smoothScrollBy(int dx, int dy) {
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
