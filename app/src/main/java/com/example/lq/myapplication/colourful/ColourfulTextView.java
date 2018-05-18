package com.example.lq.myapplication.colourful;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wx on 2017/12/6.
 */

public class ColourfulTextView extends android.support.v7.widget.AppCompatTextView {

    private int width;
    private int translateWidth;
    private Paint paint;
    private Matrix matrix;
    private LinearGradient linearGradient;
    private boolean animing;
    private String lastText;
    private Timer timer;
    private int currentMode = 0;

    public ColourfulTextView(Context context) {
        super(context);
    }

    public ColourfulTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColourfulTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void start() {
        if (matrix == null || currentMode == 0) {
            return;
        }
        if (timer != null) {
            return;
        }
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                translateWidth += width / 20;
                if (translateWidth > width * 2) {
                    translateWidth -= width * 2;
                }
                matrix.setTranslate(translateWidth, 0);
                if (linearGradient != null && matrix != null) {
                    linearGradient.setLocalMatrix(matrix);
                    postInvalidate();
                }
//                Log.e("colour","run...." + translateWidth);
            }
        };
        timer.schedule(timerTask,0,50);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setMode(int mode) {
        if (currentMode == mode) {
            return;
        }
        currentMode = mode;
        if (mode == 1) {
            width = getMeasuredWidth();
            paint = getPaint();
            linearGradient = new LinearGradient(-width, 0, 0, 0,
                    new int[]{0xffff3355, 0xffff3355, 0xfffaa3e6, 0xffff3355, 0xffff3355},
                    new float[]{0, 0.25f, 0.5f, 0.75f, 1f},
                    Shader.TileMode.REPEAT);
            paint.setShader(linearGradient);
            paint.setColor(Color.BLACK);
            matrix = new Matrix();
            invalidate();
        } else if (mode == 0){
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            paint = getPaint();
            paint.setShader(null);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("colour","onDetachedFromWindow");
        Log.e("colour", (this == null) + "");

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("colour","onAttachedToWindow");

    }
}

