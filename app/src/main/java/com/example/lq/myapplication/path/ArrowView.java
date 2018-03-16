package com.example.lq.myapplication.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lq on 2018/3/13.
 */

public class ArrowView extends View {
    public int state = 0; //down
    private int width;
    private int height;
    private int startW;
    private int startH;
    private int sW;
    private int sH;
    private int mH;
    private int arrowWidth;
    private int arrowHeight;
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimator1;

    public ArrowView(Context context) {
        super(context);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("view","onSizeChanged");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();
        arrowWidth = (int) (width * 0.35f);
        arrowHeight = (int) (arrowWidth / 2.0f + 0.5f);
        startW = (width - arrowWidth) / 2;
        startH = (height - arrowHeight) / 2;
        sW = startW;
        sH = height - startH;
        mH = startH;
        Log.e("view","onLayout " + sH);
        state = 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(15);
        paint.setColor(0xffffffff);
        Path path = new Path();
        path.moveTo(sW, sH);
        Log.e("view","onDraw " + sH);

        path.lineTo(sW + arrowWidth / 2, mH);
        path.lineTo(sW + arrowWidth, sH);
        canvas.drawPath(path,paint);
    }

    public void up() {
        state = 1;
        if (valueAnimator1 == null) {
            valueAnimator1 = ValueAnimator.ofInt(startH, height - startH);
            valueAnimator1.setDuration(500);
            valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    sH = value;
                    mH = height - value;
                    invalidate();
                }
            });
        }
        valueAnimator1.start();
    }

    public void down() {
        state = 0;
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(startH, height - startH);
            valueAnimator.setDuration(500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    sH = height - value;
                    mH = value;
                    invalidate();
                }
            });
        }
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("view","onDetachedFromWindow");
        if (valueAnimator1 != null) {
            valueAnimator1.cancel();
        }
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }
}
