package com.example.lq.myapplication.picpreview

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Scroller
import kotlinx.android.synthetic.main.activity_pic_preview.*
import java.util.*


/**
 * Created by lq on 2018/5/18.
 */
class ScaleImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        val TOUCH_MODE_SLIDE_DOWN_2_FINISH: Int = 2
        val TOUCH_MODE_SLIDE_DOUBLE_FINGER: Int = 3
        val MAX_ZOOM_RATIO = 4F
        val MIN_ZOOM_RATIO = 0.75F
    }

    var mDx: Float = 0F
    var mDy: Float = 0F
    var mDistance: Double = 0.0
    private lateinit var rv: RecyclerView
    private lateinit var view: View
    private var mScreenHeight: Int = 0
    private var mCurrentTouchMode = 0
    private var mMatrix: Matrix = Matrix()
    private var mScaleMatrix: Matrix = Matrix()
    private var mSrcMatrix: Matrix = Matrix()
    private var mCurrentDistance: Double = 0.0
    private var mLastCenter: PointF = PointF()
    private var mLastScale: Float = 1f
    private var mMoved = false
    private var mNeedFixXY = false
    private var mNeedFixTranslateX = 0f
    private var mNeedFixTranslateY = 0f
    private var mOriginalRatio: Float = 1f
    private var mAnimIsPlaying = false
    private lateinit var gestureDetector : GestureDetector
    private lateinit var scroller : Scroller
    private var preFlingX : Float = 0f
    private var preFlingY : Float = 0f
    // 2018-11-13 加惯性滑动
//    private var mVelocityTracker : VelocityTracker? = null

    init {
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e("adapter","onAttachedToWindow $this")
        var mParent = parent
        while (mParent != null && mParent !is RecyclerView) {
            mParent = mParent.parent
        }
        if (mParent != null && mParent is RecyclerView) {
            rv = mParent
            view = (rv.parent as ViewGroup).getChildAt(0)
        }
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                e2?.let {
                    Log.e("findx","velocityX:$velocityX  velocityY:${velocityY} duration:${scroller.duration}")

                    scroller.fling(e2?.rawX.toInt(),e2?.rawY.toInt(),velocityX.toInt(),velocityY.toInt(),-10000,10000,-10000,10000)
                    preFlingX = e2?.rawX
                    preFlingY = e2?.rawY
                    startFlingAnim()
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
        scroller = Scroller(context)
        mScreenHeight = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.height
        frameCallback = Choreographer.FrameCallback {
            if (scroller.computeScrollOffset()) {
                if (mDistance != 0.0) {
                    Log.e("findx","preX:$preFlingX  currX:${scroller.currX} duration:${scroller.duration}")
                    checkBoundAndTranslate(scroller.currX.toFloat() - preFlingX, scroller.currY.toFloat() - preFlingY)
                    preFlingX = scroller.currX.toFloat()
                    preFlingY = scroller.currY.toFloat()
                }
                Choreographer.getInstance().postFrameCallback(frameCallback)
            } else {
                Choreographer.getInstance().removeFrameCallback(frameCallback)
            }
        }
        //viewTreeObserver.addOnGlobalLayoutListener(this)
//        this.post({
//            val mTempSrc = RectF(0.0f, 0.0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
//            val mTempDst = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
//            mMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER)
//            imageMatrix = mMatrix
//            mSrcMatrix.set(mMatrix)
//            val value = FloatArray(9)
//            imageMatrix.getValues(value)
//            mOriginalRatio = value[0]
//            Log.e("matrix", Arrays.toString(value))
//        })
//        rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                Log.e("scroll","newState:$newState")
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                Log.e("scroll","onScrolled: dx: $dx")
//                val layoutManager = (recyclerView?.layoutManager as LinearLayoutManager);
//                val pos = layoutManager.findFirstVisibleItemPosition()
//                    Log.e("rv_scroll", "findFirstVisibleItemPosition: $pos")
//                    val view = layoutManager.findViewByPosition(pos)
//                    val x = view.left
//                    Log.e("scroll","$pos viewX: $x")
//
//
//            }
//        })
    }

    private lateinit var frameCallback: Choreographer.FrameCallback



    private fun startFlingAnim() {
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    override fun onDraw(canvas: Canvas?) {
        if (isInit && drawable != null && drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
            isInit = false
            val mTempSrc = RectF(0.0f, 0.0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
            val mTempDst = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
            mMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER)
            imageMatrix = mMatrix
            mSrcMatrix.set(mMatrix)
            val value = FloatArray(9)
            imageMatrix.getValues(value)
            mOriginalRatio = value[0]
            Log.e("matrix", Arrays.toString(value))
        } else {
            super.onDraw(canvas)
        }
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scroller.abortAnimation()
        gestureDetector.onTouchEvent(event)
        if (mAnimIsPlaying) {
            parent.requestDisallowInterceptTouchEvent(true)
            return true
        }
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mMoved = false
                if (true) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                mCurrentTouchMode = 0
                Log.e("touch", "ACTION_DOWN ${event.pointerCount}")
                mDx = event.rawX
                mDy = event.rawY

                // 2018-11-13 加惯性滑动
//                if(mVelocityTracker == null) {
//                    mVelocityTracker = VelocityTracker.obtain()
//                } else {
//                    mVelocityTracker?.clear()
//                }
//                mVelocityTracker?.addMovement(event)

                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.e("touch", "ACTION_POINTER_DOWN")
                if (event.pointerCount == 2) {
                    val x0 = event.getX(0)
                    val y0 = event.getY(0)
                    val x1 = event.getX(1)
                    val y1 = event.getY(1)
                    mDistance = getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
                    mLastCenter.x = (x0 + x1) / 2
                    mLastCenter.y = (y0 + y1) / 2
                }
                mNeedFixXY = false
                mNeedFixTranslateX = 0f
                mNeedFixTranslateY = 0f
                mMatrix.set(imageMatrix)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (!mMoved) {
                    if (judgeMove(mDx,mDy,event.rawX,event.rawY) || event.pointerCount == 2) {
                        mMoved = true
                    } else {
                        return super.onTouchEvent(event)
                    }
                }
                if (event.pointerCount == 2) {
                    //缩放
                    scaleByFinger(event)
                }
                if (event.pointerCount == 1 && mCurrentTouchMode != TOUCH_MODE_SLIDE_DOUBLE_FINGER) {
//                    if (mSrcMatrix)
                    val mX = event.rawX
                    val mY = event.rawY
                    val dX = mX - mDx
                    val dY = mY - mDy
                    mDx = mX
                    mDy = mY
                    if ((dY > 0 && Math.abs(dX) * 2 < Math.abs(dY) && mDistance == 0.0) || mCurrentTouchMode == TOUCH_MODE_SLIDE_DOWN_2_FINISH) {
                        //竖滑
                        mCurrentTouchMode = TOUCH_MODE_SLIDE_DOWN_2_FINISH
                        rv.translationX += dX
                        rv.translationY += dY
                        rv.scaleX -= dY / mScreenHeight
                        rv.scaleY -= dY / mScreenHeight
                        if (rv.translationY > 0) {
                            view.alpha = 1 - rv.translationY / mScreenHeight
                        }
                    } else if ((Math.abs(dY) * 2 < Math.abs(dX) && mDistance == 0.0)) {  //左右切换图片
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        //位移
                        if (mDistance != 0.0) {
                            checkBoundAndTranslate(dX, dY)
                        }
                    }
//                    mVelocityTracker?.addMovement(event);
                }
                Log.e("touch", "ACTION_MOVE  ${event.pointerCount} ")
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (!mMoved) {
                    zoomAnim()
                }
                Log.e("touch", "ACTION_UP ")
                if (mCurrentTouchMode == TOUCH_MODE_SLIDE_DOWN_2_FINISH) {
                    if (rv.translationY > mScreenHeight / 6) {
                        zoomAnim(50)
                        (context as PicPreviewActivity).finishWithAnim()
                    } else {
                        releaseRvWithAnim()
                    }
                }

                if (mCurrentTouchMode == TOUCH_MODE_SLIDE_DOUBLE_FINGER) {
                    if (rv.translationY > mScreenHeight / 6) {
                        zoomAnim(50)
                        (context as PicPreviewActivity).finishWithAnim()
                    } else {
                        releaseRvWithAnim()
                    }
                }
//                mVelocityTracker?.computeCurrentVelocity(1000)
//                var initialVelocity_Y = mVelocityTracker?.yVelocity as Float
//                var initialVelocity_X = mVelocityTracker?.xVelocity as Float
//                //flingSlide(initialVelocity_X,initialVelocity_Y, PointF(mDx,mDy))
//                Log.e("touch","$initialVelocity_X    $initialVelocity_Y")
//                if (mVelocityTracker != null) {
//                    mVelocityTracker?.recycle()
//                    mVelocityTracker = null
//                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mLastScale = (1 + mCurrentDistance / 400).toFloat()
                releaseWithAnim()
            }
            else -> {

            }
        }
        return super.onTouchEvent(event)
    }

    private fun flingSlide(vX : Float, vY : Float, pointF: PointF) {
        var vVector = Math.sqrt((vX * vX + vY * vY).toDouble())
        var deceleration = 200
        var flingDuration : Long = (vVector / deceleration).toLong()
        Log.e("fling","${vX}   ${vY}   $flingDuration")

        var endX = pointF.x + 0.5 * vX * flingDuration
        var endY = pointF.y + 0.5 * vY * flingDuration
        var flingTypeEvaluator = FlingTypeEvaluator(flingDuration,vX,vY)
        var distance = 0.5 * vVector * vVector / deceleration
        var flingSlideAnim = ValueAnimator.ofObject(flingTypeEvaluator,pointF, PointF(endX.toFloat(),endY.toFloat()))
        flingSlideAnim.addUpdateListener {
            var pointF = it.animatedValue as PointF
            //位移
            if (mDistance != 0.0) {
                checkBoundAndTranslate(pointF.x, pointF.y)
            }
            Log.e("fling","${pointF.x}   ${pointF.y}")
        }
        flingSlideAnim.duration = flingDuration
        flingSlideAnim.interpolator = LinearInterpolator()
        flingSlideAnim.start()
    }

    private fun releaseRvWithAnim() {
        val pvhPicTranslationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, rv.translationX, 0f)
        val pvhPicTranslationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, rv.translationY, 0f)
        val pvhPicScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, rv.scaleX, 1f)
        val pvhPicScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, rv.scaleY, 1f)
        val oa = ObjectAnimator.ofPropertyValuesHolder(rv, pvhPicTranslationX, pvhPicTranslationY, pvhPicScaleX, pvhPicScaleY)
        oa.duration = 200
        oa.interpolator = LinearInterpolator()
        oa.start()
        val objAnim = ObjectAnimator.ofFloat(view, View.ALPHA, 1f)
        objAnim.duration = 200
        objAnim.start()
    }

    private fun releaseWithAnim() {
        val currentValue = FloatArray(9)
        imageMatrix.getValues(currentValue)
        val endValue = FloatArray(9)
        mSrcMatrix.getValues(endValue)
        if (mOriginalRatio > currentValue[0]) {
            val valueAnim = ValueAnimator.ofObject(ValueEvaluator(), currentValue, endValue)
            valueAnim.addUpdateListener(ValueAnimator.AnimatorUpdateListener() {
                mScaleMatrix.setValues(it.animatedValue as FloatArray)
                imageMatrix = mScaleMatrix
            })
            valueAnim.duration = 200
            valueAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mAnimIsPlaying = false
                }

            })
            valueAnim.start()
            mDistance = 0.0
            mLastScale = 1f
            mCurrentDistance = 0.0
        }
    }

    private fun zoomAnim() {
        zoomAnim(200)
    }

    open fun zoomAnim(duration : Long) {
        mAnimIsPlaying = true
        val startValue = FloatArray(9)
        val endValue = FloatArray(9)
        imageMatrix.getValues(startValue)
        mSrcMatrix.getValues(endValue)
        mScaleMatrix.reset()
        val valueAnim = ValueAnimator.ofObject(ValueEvaluator(), startValue, endValue)
        valueAnim.addUpdateListener(ValueAnimator.AnimatorUpdateListener() {
            mScaleMatrix.setValues(it.animatedValue as FloatArray)
            imageMatrix = mScaleMatrix
        })
        valueAnim.duration = duration
        valueAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mAnimIsPlaying = false
            }

        })
        valueAnim.start()
        mDistance = 0.0
        mLastScale = 1f
        mCurrentDistance = 0.0
    }

    private var isInit : Boolean = true



    private fun getDistance(x0: Float, y0: Float, x1: Float, y1: Float): Double {
        return Math.sqrt((x1 - x0) * (x1 - x0).toDouble() + (y1 - y0) * (y1 - y0))
    }


    inner class ValueEvaluator : TypeEvaluator<Any> {
        val value = FloatArray(9)
        override fun evaluate(fraction: Float, startValue: Any, endValue: Any): Any {
            val startValue = startValue as FloatArray
            val endValue = endValue as FloatArray
            for (i in 0..8) {
                value[i] = (endValue[i] - startValue[i]) * fraction + startValue[i]
            }
            return value
        }
    }

    private fun scaleByFinger(event: MotionEvent) {
        mCurrentTouchMode = TOUCH_MODE_SLIDE_DOUBLE_FINGER
        //parent.requestDisallowInterceptTouchEvent(false)
        val x0 = event.getX(0)
        val y0 = event.getY(0)
        val x1 = event.getX(1)
        val y1 = event.getY(1)
        val distance = getDistance(x0, y0, x1, y1)
        val preScaleValue = FloatArray(9)
        imageMatrix.getValues(preScaleValue)
        if (distance > mDistance && mOriginalRatio * MAX_ZOOM_RATIO < preScaleValue[0]) { //放大 判断缩放的边界 达到最大值 不再放大
            return
        } else if (distance < mDistance && mOriginalRatio * MIN_ZOOM_RATIO >= preScaleValue[0]) {   //缩小 达到最小值 不做处理
            return
        } else {
            if (distance == mDistance) { //距离一致 不缩放 减少计算
                return
            }
        }
        mScaleMatrix.set(mMatrix)
        mCurrentDistance += distance - mDistance
        var scale = (1 + mCurrentDistance / 400).toFloat() / mLastScale
        mScaleMatrix.postScale(scale, scale, mLastCenter.x, mLastCenter.y)
        imageMatrix = mScaleMatrix


        mDistance = distance
        val value = FloatArray(9)
        imageMatrix.getValues(value)
        Log.e("touch", "scale ${Arrays.toString(value)}")
        val x = value[2]
        val y = value[5]
        val scaleX = value[0]
        val scaleY = value[4]
        val drawableScaleWidth = drawable.intrinsicWidth * scaleX
        val drawableScaleHeight = drawable.intrinsicHeight * scaleY
        if (drawableScaleHeight < height) {
            mNeedFixTranslateY = (height - drawableScaleHeight) / 2 - y
            mNeedFixXY = true

        } else {
            if (y + mNeedFixTranslateY >= 0) {
                mNeedFixXY = true
                mNeedFixTranslateY = -y
            }
            if (y + mNeedFixTranslateY + drawableScaleHeight < height) {
                mNeedFixXY = true
                mNeedFixTranslateY += height - y - mNeedFixTranslateY - drawableScaleHeight
            }
        }
        if (drawableScaleWidth < width) {
            mNeedFixTranslateX = (width - drawableScaleWidth) / 2 - x
            mNeedFixXY = true
        } else {
            if (x + mNeedFixTranslateX >= 0) {
                mNeedFixXY = true
                mNeedFixTranslateX = -x
            }
            if (x + mNeedFixTranslateX + drawableScaleWidth < width) {
                mNeedFixXY = true
                mNeedFixTranslateX += width - x - mNeedFixTranslateX - drawableScaleWidth
            }
        }
        if (mNeedFixXY) {
            mScaleMatrix.postTranslate(mNeedFixTranslateX, mNeedFixTranslateY)
            imageMatrix = mScaleMatrix
        }
    }

    private fun checkBoundAndTranslate(dx: Float, dy: Float) {
        var dX = dx
        var dY = dy
        val value = FloatArray(9)
        imageMatrix.getValues(value)
        Log.e("touch", "scale ${Arrays.toString(value)}")
        val x = value[2]
        val y = value[5]
        val scaleX = value[0]
        val scaleY = value[4]
        val drawableScaleWidth = drawable.intrinsicWidth * scaleX
        val drawableScaleHeight = drawable.intrinsicHeight * scaleY
        if (drawableScaleWidth > width) {
            if (dX >= 0 && dX >= -x) {
                dX = -x
            }
            if (dX < 0 && dX <= width - drawable.intrinsicWidth * scaleX - x) {
                dX = width - drawable.intrinsicWidth * scaleX - x
            }
        } else {
            dX = 0f
        }
        if (drawableScaleHeight > height) {
            if (dY >= 0 && dY >= -y) {
                dY = -y
            }
            if (dY < 0 && dY <= height - drawable.intrinsicHeight * scaleY - y) {
                dY = height - drawable.intrinsicHeight * scaleY - y
            }
        } else {
            dY = 0f

        }
//
//        //横向滑动至边缘
        if (Math.abs(dy) * 2 < Math.abs(dx) && dX == 0f) {
            parent.requestDisallowInterceptTouchEvent(false)
            dY = 0f
        }
        mScaleMatrix.postTranslate(dX, dY)
        imageMatrix = mScaleMatrix
        imageMatrix.getValues(value)
        Log.e("touch", "scale ${Arrays.toString(value)}")
    }

    private fun judgeMove(downX: Float, downY: Float, moveX: Float, moveY: Float) : Boolean {
        var distance = Math.sqrt((downX - moveX).toDouble() * (downX - moveX) + (downY - moveY) * (downY - moveY))
        return distance > ViewConfiguration.get(context).scaledTouchSlop
    }

}