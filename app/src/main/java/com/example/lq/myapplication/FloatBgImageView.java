package com.example.lq.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import com.example.lq.myapplication.utils.UIHelper;


/**
 * Created by wx on 2017/8/8.
 */

public class FloatBgImageView extends android.support.v7.widget.AppCompatImageView {
    public int dy;
    public int currentY;
    private Paint p;
    private RectF rectF;
    private int radius;

    public FloatBgImageView(Context context) {
        super(context);
        init();
    }

    public FloatBgImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public FloatBgImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }

    private void init() {
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setDither(true);
        p.setAntiAlias(true);
        radius = UIHelper.dip2px(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.WHITE);
        p.setXfermode(null);
        int sc=canvas.saveLayer(0,0,getWidth(),getHeight(),p, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0,0,getWidth(),getHeight(),p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        p.setColor(0x01000000);
        if (rectF == null) {
            rectF = new RectF(0, 0, getWidth(), getHeight());
        }
        canvas.drawRoundRect(rectF, radius,radius,p);
        p.setXfermode(null);
        canvas.restoreToCount(sc);
    }


}
