package com.example.lq.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class PreViewContainer extends ViewGroup {
    public PreViewContainer(Context context) {
        super(context);
    }

    public PreViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec,widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(200,0,view.getMeasuredWidth(),view.getMeasuredHeight());
    }
}
