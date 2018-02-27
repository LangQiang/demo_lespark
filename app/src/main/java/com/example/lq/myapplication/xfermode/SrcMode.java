package com.example.lq.myapplication.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wx on 2018/2/12.
 */

public class SrcMode extends ImageView{

    public SrcMode(Context context) {
        super(context);
    }

    public SrcMode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SrcMode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        Rect rect = new Rect(0,0,300,getHeight());
        Paint paint = new Paint();
        paint.setColor(0xffff3e54);

        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);

        paint.setXfermode(xfermode);
        canvas.drawRect(rect,paint);

        paint.setXfermode(null);


        canvas.restoreToCount(i);
    }
}
