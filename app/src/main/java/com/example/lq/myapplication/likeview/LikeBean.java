package com.example.lq.myapplication.likeview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * @author lq
 * @since 2017/5/27 10:48
 */

public class LikeBean {
    private static final int IMAGE_WIDTH = 53;
    private static final int IMAGE_HEIGHT = 50;
    private Random random;
    private Bitmap bitmap;
    private SurfaceView parent;
    private BezierEvaluator bezierEvaluator;
    private PointF pointF;
    private Matrix matrix = new Matrix();
    private PointF start;
    private PointF end;
    private ValueAnimator scaleAnim;
    private float scaleF;
    private ValueAnimator alphaAnim;
    private float alphaF;

    public boolean isEnd() {
        return isEnd;
    }

    private boolean isEnd;
    private ValueAnimator valueAnimator;


    public LikeBean(Bitmap bitmap, SurfaceView parent) {
        this.bitmap = bitmap;
        this.parent = parent;
        init();
    }

    private void init() {
        random = new Random();
        bezierEvaluator = new BezierEvaluator();
        start = new PointF(ensurePositiveInteger(parent.getWidth() - IMAGE_WIDTH * 2), ensurePositiveInteger(parent.getHeight() - IMAGE_HEIGHT));
        end = new PointF(random.nextInt(ensurePositiveInteger(parent.getWidth() - IMAGE_WIDTH)), random.nextInt(IMAGE_HEIGHT));
        valueAnimator = ValueAnimator.ofObject(bezierEvaluator,
                start,
                end);
        valueAnimator.setDuration(3500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointF = (PointF) animation.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isEnd = true;
                //bitmap.recycle();
            }
        });
        scaleAnim = ValueAnimator.ofFloat(0.5f,1.5f);
        scaleAnim.setDuration(200);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleF = (float) animation.getAnimatedValue();
            }
        });

        alphaAnim = ValueAnimator.ofFloat(0f,1f);
        alphaAnim.setDuration(3000);
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alphaF = 1 - (float) animation.getAnimatedValue();
            }
        });

    }

    public void startValueAnimator() {
        valueAnimator.start();
        scaleAnim.start();
        alphaAnim.start();
    }

    public void draw(Canvas canvas, Paint paint) {
        try {
            if (bitmap != null) {
                paint.setAntiAlias(true);
                matrix.setScale(scaleF, scaleF, bitmap.getWidth(), bitmap.getHeight());
                matrix.postTranslate(pointF.x - bitmap.getWidth() / 2, pointF.y - bitmap.getHeight() / 2);
                paint.setAlpha((int)(alphaF * 255));
                canvas.drawBitmap(bitmap, matrix, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF point1 = new PointF();
        private PointF point2 = new PointF();

        public BezierEvaluator() {
            // 这里由于需要每一个新创建的ImageView按照不同的曲线来运动，所以通过random随机生成，这里的范围可以自己定义
            point1.set(random.nextInt(ensurePositiveInteger(parent.getWidth() - IMAGE_WIDTH)), random.nextInt(IMAGE_HEIGHT));
            point2.set(random.nextInt(parent.getWidth()), random.nextInt(ensurePositiveInteger(parent.getHeight() - IMAGE_HEIGHT)));
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            final float t = fraction;
            float oneMinusT = 1.0f - t;
            PointF point = new PointF();

            // p0表示起始点
            PointF point0 = startValue;
            // p3表示终止点
            PointF point3 = endValue;

            point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x)
                    + 3 * oneMinusT * oneMinusT * t * (point1.x)
                    + 3 * oneMinusT * t * t * (point2.x)
                    + t * t * t * (point3.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
                    + 3 * oneMinusT * oneMinusT * t * (point1.y)
                    + 3 * oneMinusT * t * t * (point2.y)
                    + t * t * t * (point3.y);
            return point;
        }
    }
    static int ensurePositiveInteger(int number) {
        while (number <= 0) {
            number += 10;
        }
        return number;
    }
}
