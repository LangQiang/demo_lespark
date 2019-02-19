package com.example.lq.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lq.myapplication.utils.UIHelper;

public class VarietyView extends FrameLayout {
    public VarietyView(Context context) {
        this(context,null);
    }

    public VarietyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VarietyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int width = getWidth();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            Log.e("customview","childWidth:" + childWidth + "  i: " + i);
            int childHeight = childView.getMeasuredHeight();
            final int leftWidth = width - left;
            if (leftWidth < UIHelper.dip2px(30)) {
                childView.setVisibility(GONE);
            } else {
                if (childWidth < leftWidth) {
                    childView.layout(left,top,left + childWidth,top + childHeight);
                    left += (childWidth + UIHelper.dip2px(3));
                } else {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)childView).setMaxWidth(leftWidth);
                        }
                    });
                    childView.layout(left,top,left + leftWidth,top + childHeight);
                    left += (leftWidth + UIHelper.dip2px(3));
                }
            }

        }
    }


}
