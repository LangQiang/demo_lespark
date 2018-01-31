package com.example.lq.myapplication.choice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by wx on 2018/1/18.
 */

public class ProgressBarWithText extends ProgressBar {
    public static final int TEXT_GRAVITY_LEFT = 0;
    public static final int TEXT_GRAVITY_CENTER = 1;
    public static final int TEXT_GRAVITY_RIGHT = 2;
    private int progress;
    private int max;
    private String text = "";
    private int bgColor = 0xffff3e54;
    private int foreColor = 0xff000000;
    private int textGravity = 0;
    private int marginLeft;
    private int marginRight;
    private Paint paint;
    private int textSize = 10;
    private Rect textRect;

    private final Rect normalTextRect;
    private int normalTextGravity = 0;
    private int normalMarginLeft;
    private int normalMarginRight;
    private int normalTextColor = 0xff000000;
    private String normalText = "";
    private int normalTextSize = 10;

    public ProgressBarWithText(Context context) {
        this(context,null);
    }

    public ProgressBarWithText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBarWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        max = getMax();
        progress = getProgress();
        paint = new Paint();
        paint.setAntiAlias(true);
        textRect = new Rect();
        normalTextRect = new Rect();

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawNormalText(canvas);

        drawXfermodeText(canvas);

    }

    private void drawXfermodeText(Canvas canvas) {
        //背景层

        paint.setTextSize(textSize);
        paint.getTextBounds(this.text, 0, this.text.length(), textRect);
        float textX;
        if (textGravity == TEXT_GRAVITY_CENTER) {
            textX = (getWidth() / 2) - textRect.centerX() + marginLeft - marginRight;
        } else if (textGravity == TEXT_GRAVITY_RIGHT) {
            textX = getWidth() - textRect.width() - marginRight;
        }else {
            textX = marginLeft;
        }
        float textY = (getHeight() / 2) - textRect.centerY();

        paint.setColor(bgColor);
        canvas.drawText(this.text, textX, textY, paint);

        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        paint.setColor(foreColor);
        Rect rect = new Rect(0,0,(int)(getWidth() * progress * 1.0 / max),getHeight());
        canvas.drawText(this.text, textX, textY, paint);

        //遮罩层
        paint.setColor(0x00000000);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
        paint.setXfermode(xfermode);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(i);
    }

    private void drawNormalText(Canvas canvas) {
        paint.setTextSize(normalTextSize);
        paint.getTextBounds(this.normalText, 0, this.normalText.length(), normalTextRect);
        float textX;
        if (normalTextGravity == TEXT_GRAVITY_CENTER) {
            textX = (getWidth() / 2) - normalTextRect.centerX() + normalMarginLeft - normalMarginRight;
        } else if (normalTextGravity == TEXT_GRAVITY_RIGHT) {
            textX = getWidth() - normalTextRect.width() - normalMarginRight;
        }else {
            textX = normalMarginLeft;
        }
        float textY = (getHeight() / 2) - normalTextRect.centerY();

        paint.setColor(normalTextColor);
        canvas.drawText(this.normalText, textX, textY, paint);
    }

    @Override
    public synchronized void setProgress(int progress) {
        this.progress = progress;
        super.setProgress(progress);
    }

    @Override
    public synchronized void setMax(int max) {
        this.max = max;
        super.setMax(max);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setTextSize(@Px int textSize) {
        this.textSize = textSize;
        if (paint != null) {
            paint.setTextSize(this.textSize);
        }
        invalidate();
    }

    public void setTextBgColor(int color) {
        this.bgColor = color;
        invalidate();
    }

    public void setTextForeColor(int color) {
        this.foreColor = color;
        invalidate();
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
        invalidate();
    }

    public void setTextMarginLeft(@Px int marginLeft) {
        this.marginLeft = marginLeft;
        invalidate();
    }

    public void setTextMarginRight(@Px int marginRight) {
        this.marginRight = marginRight;
        invalidate();
    }

    public void setNormalText(String normalText) {
        this.normalText = normalText;
        invalidate();
    }

    public void setNormalTextSize(@Px int normalTextSize) {
        this.normalTextSize = normalTextSize;
        invalidate();
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        invalidate();
    }

    public void setNormalTextGravity(int normalTextGravity) {
        this.normalTextGravity = normalTextGravity;
        invalidate();
    }

    public void setNormalTextMarginLeft(@Px int normalMarginLeft) {
        this.normalMarginLeft = normalMarginLeft;
        invalidate();
    }

    public void setNormalTextMarginRight(@Px int normalMarginRight) {
        this.normalMarginRight = normalMarginRight;
        invalidate();
    }

    public String getNormalText() {
        return normalText == null ? "" : normalText;
    }

    public void setTextAttrs(String text,
                             @Px int textSize,
                             int textBgColor,
                             int textForeColor,
                             int textGravity,
                             @Px int textMarginLeft,
                             @Px int textMarginRight) {
        this.text = text;
        this.textSize = textSize;
        this.bgColor = textBgColor;
        this.foreColor = textForeColor;
        this.textGravity = textGravity;
        this.marginLeft = textMarginLeft;
        this.marginRight = textMarginRight;
        invalidate();
    }

    public void setNormalTextAttrs(String normalText,
                             @Px int normalTextSize,
                             int normalTextColor,
                             int normalTextGravity,
                             @Px int normalTextMarginLeft,
                             @Px int normalTextMarginRight) {
        this.normalText = normalText;
        this.normalTextSize = normalTextSize;
        this.normalTextColor = normalTextColor;
        this.normalTextGravity = normalTextGravity;
        this.normalMarginLeft = normalTextMarginLeft;
        this.normalMarginRight = normalTextMarginRight;
        invalidate();
    }
}
