package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author lq
 * @since 2016/12/18 19:58
 */

public class MyView extends FrameLayout {

    public static final int EXPAND = 0;
    public static final int COLLAPSE = 1;
    public static final int DRAGGING = 2;
    private View mTopView;
    private View mContentView;
    private ViewDragHelper mDragger;
    private int mTopViewHeight;

    private float parallax = 0.25f;

    //回弹
    private boolean isSpringBack;
    private int currentState = COLLAPSE;

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    private boolean canDrag = true;

    private OnMyDragListener mOnMyDragListener;
    private ShouldIntercept mShouldIntercept;


    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mDragger = ViewDragHelper.create(this, 1.0f, mCallback);
    }

    public void setParallax(float parallax) {
        this.parallax = parallax;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setSpringBack(boolean springBack) {
        isSpringBack = springBack;
    }

    //判断布局是否符合要求
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("Content view must contains only two child views.");
        }
        mTopView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    //子view摆放位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mTopViewHeight = mTopView.getHeight();
        resetTopViewHeight();
        //resetContentHeight();
        //mTopView.layout(l, t, r, mTopViewHeight);
        mTopView.layout(l, (int) ((mContentTop - mTopViewHeight) * parallax),
                r, (int) ((mContentTop - mTopViewHeight) * parallax) + mTopViewHeight);
        mContentView.layout(l, mContentTop, r, mContentView.getHeight() + mContentTop);
    }


    private void resetTopViewHeight() {

    }

    private void resetContentHeight() {
        ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
        layoutParams.height = getHeight() - mContentTop;
        mContentView.setLayoutParams(layoutParams);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDragger.processTouchEvent(event);
        return true;
    }

    private int mContentTop;
    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (! canDrag) {
                return false;
            }
            if (mShouldIntercept != null) {
                if (mShouldIntercept.intercept()) {
                    return child == mContentView;
                } else {
                    return false;
                }
            }
            return child == mContentView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy)
        {
            return Math.max(Math.min(mTopViewHeight, top),0);
        }
        @Override
        public int getViewVerticalDragRange(View child) {
            return mTopViewHeight;
        }
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (mOnMyDragListener != null) {
                //collapse 0.0  --  expand 1.0
                mOnMyDragListener.onMyDragPercent((float) top / mTopViewHeight);
                if (top == 0) {
                    mOnMyDragListener.onMyDragState(MyView.COLLAPSE);
                    currentState = COLLAPSE;
                } else if (top == mTopViewHeight) {
                    mOnMyDragListener.onMyDragState(MyView.EXPAND);
                    currentState = EXPAND;
                } else {
                    mOnMyDragListener.onMyDragState(MyView.DRAGGING);
                    currentState = DRAGGING;
                }
            }

            //提高性能
            if (top == mContentTop) {
                return;
            }
            Log.e("myview",top +"");
            mContentTop = top;
            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (yvel > 0) {
                toExpand();
            } else {
                toCollapse();
            }
            Log.e("hhh",releasedChild.getTop() + "");
        }
    };

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //滑动监听
    public interface OnMyDragListener {
        void onMyDragPercent(float percent);
        void onMyDragState(int state);
    }

    public void setOnMyDragListener(OnMyDragListener mOnMyDragListener) {
        this.mOnMyDragListener = mOnMyDragListener;
    }

    //设置拦截规则
    public interface ShouldIntercept {
        boolean intercept();
    }

    public void setShouldIntercept(ShouldIntercept mShouldIntercept) {
        this.mShouldIntercept = mShouldIntercept;
    }

    public void toExpand() {
        currentState = EXPAND;
        mDragger.smoothSlideViewTo(mContentView,0, mTopViewHeight);
        postInvalidate();
    }

    public void toCollapse() {
        currentState = COLLAPSE;
        mDragger.smoothSlideViewTo(mContentView,0, 0);
        postInvalidate();
    }
}

