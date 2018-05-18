package com.example.lq.myapplication.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.lq.myapplication.R;

/**
 * Created by wx on 2018/1/16.
 */

public class RatioGroupView extends FrameLayout {
    private static final String TAG = RatioGroupView.class.getSimpleName();
    private float ratio;

    public RatioGroupView(@NonNull Context context) {
        this(context,null);
    }

    public RatioGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioGroupView);
            ratio = ta.getFloat(R.styleable.RatioGroupView_ratio, 1);
            ta.recycle();
            Log.e("attrs",ratio + "");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.makeMeasureSpec((int) (width * ratio),MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);
    }
}
