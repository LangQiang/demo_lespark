package com.example.lq.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lq on 2018/2/11.
 */

public class MyIV extends ImageView {
    Paint imagePaint;
    float left;
    public MyIV(Context context) {
        super(context);
    }

    public MyIV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyIV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //clipBitmap(canvas);

        LinearGradient linearGradient = new LinearGradient(-100, 0.0f, 0, 0.0f, new int[]{Color.BLACK,0,0, Color.BLACK}, null, Shader.TileMode.CLAMP);
        Matrix mGradientMatrix = new Matrix();
        mGradientMatrix.setTranslate(left, 0);

        linearGradient.setLocalMatrix(mGradientMatrix);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
//
        imagePaint = new Paint();
        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), imagePaint, Canvas.ALL_SAVE_FLAG);
        RectF rect = new RectF(0,0,2 * getWidth() - getWidth(),getHeight());
        super.onDraw(canvas);
        imagePaint.setXfermode(porterDuffXfermode);
        imagePaint.setShader(linearGradient);
        canvas.drawRect(rect, imagePaint);
//
        imagePaint.setXfermode(null);
        canvas.restoreToCount(i);

    }

    private void bigMask(Canvas canvas) {

    }

    private void clipBitmap(Canvas canvas) {
        LinearGradient linearGradient = new LinearGradient(left, 0.0f, left + 100, 0.0f, new int[]{Color.BLACK,0,0, Color.BLACK}, null, Shader.TileMode.CLAMP);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        imagePaint = new Paint();
        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), imagePaint, Canvas.ALL_SAVE_FLAG);

        RectF rect = new RectF(left,0,left+100,getHeight());
        Rect r = new Rect((int)left,0,(int)left + 100 , getHeight());
//        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        canvas.drawBitmap(((BitmapDrawable)drawable).getBitmap(),r,r,imagePaint);

        imagePaint.setXfermode(porterDuffXfermode);
        imagePaint.setShader(linearGradient);
        canvas.drawRect(r, imagePaint);

        imagePaint.setXfermode(null);
        canvas.restoreToCount(i);
    }

    public void start() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,getWidth() + 100);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                left = (Float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(-1);
        valueAnimator.start();
    }

}
