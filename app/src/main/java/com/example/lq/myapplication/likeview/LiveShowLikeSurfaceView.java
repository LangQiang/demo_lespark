package com.example.lq.myapplication.likeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Debug;
import android.os.SystemClock;
import android.support.v4.BuildConfig;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lq
 * @since 2017/5/27 10:04
 */

public class LiveShowLikeSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private int count;
    private final Paint p;
    private int minGapTime = 200;
    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean isDestroyed;
    private ArrayList<LikeBean> likeBeens = new ArrayList<>();
    private ArrayList<LikeBean> bufferedBeans = new ArrayList<>();
    private LruCache<Integer, Bitmap> bitmapLruCache = new LruCache<>(5 * 1024 * 1024);
//    private HashMap<Integer, Bitmap> bitmapLruCache = new HashMap<>();
    private Bitmap tempBitmap;

    public LiveShowLikeSurfaceView(Context context) {
        this(context, null);
    }

    public LiveShowLikeSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveShowLikeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        p = new Paint();
        p.setAntiAlias(true);
        addLikeBeanByLooping();
    }

    public void setMinGapTime(int minGapTime) {
        this.minGapTime = minGapTime;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (this) {
            canGetCanvas = true;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (this) {
            canGetCanvas = false;
        }
    }

    public void addLikeBean(int id) {
        if ((tempBitmap = bitmapLruCache.get(id)) == null) {
            tempBitmap = BitmapFactory.decodeResource(getResources(), id);
            bitmapLruCache.put(id, tempBitmap);
        }
        LikeBean likeBean = new LikeBean(tempBitmap, this);
        bufferedBeans.add(likeBean);
        start();
    }

    private void addLikeBeanByLooping() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestroyed) {
                    if (bufferedBeans.size() > 0) {
                        final LikeBean remove = bufferedBeans.remove(0);
                        LiveShowLikeSurfaceView.this.post(new Runnable() {
                            @Override
                            public void run() {
                                remove.startValueAnimator();
                            }
                        });
                        likeBeens.add(remove);
                    }

                    SystemClock.sleep(minGapTime);
                }
            }
        }).start();
    }

    private void start() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
        }
//        thread = new Thread(this);
//        thread.start();
    }
    private boolean canGetCanvas = true;
    @Override
    public void run() {
        Canvas canvas = null;
        do {
            long start = SystemClock.currentThreadTimeMillis();
//            synchronized (this) {
                if (canGetCanvas) {
                    try {
                        canvas = surfaceHolder.lockCanvas();
                        /**清除画面*/
                        if (canvas != null) {
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        }
                        for (int i = 0; i < likeBeens.size(); i++) {
                            if (likeBeens.get(i).isEnd()) {
                                likeBeens.remove(i);
                                i--;
                            } else {
                                if (canvas != null) {
                                    likeBeens.get(i).draw(canvas, p);
                                }
                            }
                        }

                    } catch (Exception e) {

                    } finally {
                        if (canvas != null && surfaceHolder != null) {
                            try {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            } catch (Exception e) {
                            }
                        }
                    }
                } else {
                    likeBeens.clear();
                    bufferedBeans.clear();
                }
//            }
            long dTime = 17 - (SystemClock.currentThreadTimeMillis() - start);
            SystemClock.sleep(dTime < 0 ? 0 : dTime);
        } while (likeBeens.size() > 0 || bufferedBeans.size() > 0);
    }

    public void destroy() {
        isDestroyed = true;
        canGetCanvas = false;
    }
}
