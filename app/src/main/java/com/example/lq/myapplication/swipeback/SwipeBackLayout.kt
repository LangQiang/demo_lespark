package com.example.lq.myapplication.swipeback

import android.app.Activity
import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.app.ActivityOptions
import android.os.Build
import android.support.v4.view.ViewCompat


/**
 * Created by lq on 2018/4/4.
 */
class SwipeBackLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mActivity: Activity
    private var viewDragHelper : ViewDragHelper? = null
    private var contentView : View? = null
    private var mContentLeft : Int = 0
    private var isLayout : Boolean = false

    init {
        init()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        isLayout = true
        contentView?.let {
            it.layout(mContentLeft, 0,
                    mContentLeft + it.measuredWidth,
                    it.measuredHeight)
        }
        isLayout = false
    }

    private fun init() {
        postDelayed({
            invalidate()
        },5000)
        viewDragHelper = ViewDragHelper.create(this, 1.0.toFloat(),object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
                Log.e("view","tryCaptureView")
                return true
            }

            override fun getViewHorizontalDragRange(child: View?): Int {

                var i = 0
                contentView?.let {
                    i = it.width
                }
                Log.e("view","getViewHorizontalDragRange $i")

                return i
            }
            override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
                var i = 0
                contentView?.let {
                    i = it.width
                }
                return Math.min(i, Math.max(left, 0))
            }

            override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
                Log.e("view","onViewPositionChanged $left")
                if (isLayout) {
                    Log.e("view","islayout")

                }
                if (mContentLeft != left) {
                    mContentLeft = left
                    requestLayout()
                }
            }

            override fun onViewReleased(releasedChild: View?, xVel: Float, yVel: Float) {
                Log.e("view","onViewPositionChanged $xVel")
                if (xVel > 0) {
                    mActivity.finish()
                } else if (xVel < 0){
                    viewDragHelper?.smoothSlideViewTo(contentView,0,0)
                    invalidate()
                } else {
                    contentView?.let {
                        if (mContentLeft < it.width * 0.3) {
                            viewDragHelper?.smoothSlideViewTo(contentView,0,0)
                            invalidate()
                        } else {
                            mActivity.finish()
                        }
                    }
                }
            }
        })
    }

    override fun computeScroll() {

        viewDragHelper?.let {
            val flag = it.continueSettling(true)
            Log.e("view","computeScroll $flag")

            if (it.continueSettling(true)) {
                ViewCompat.postInvalidateOnAnimation(this)
            }
        }
    }


    open fun attachToActivity(activity: Activity) {
        if (parent != null) {
            return
        }
        mActivity = activity
        val a = activity.theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
        val background = a.getResourceId(0, 0)
        a.recycle()

        val decor = activity.window.decorView as ViewGroup
        var decorChild = decor.findViewById<View>(android.R.id.content)
        while (decorChild.parent !== decor) {
            decorChild = decorChild.parent as View
        }
        decorChild.setBackgroundColor(0xffffffff.toInt())
        decor.removeView(decorChild)
        addView(decorChild)
        contentView = decorChild
        decor.addView(this)
        convertActivityToTranslucent(activity)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("view","onTouchEvent")
        viewDragHelper?.processTouchEvent(event)
        return true
    }

    private var downX: Float = 0.toFloat()
    private var downY: Float = 0.toFloat()
    private var totalMove: Float = 0.toFloat()
    private var isIntercept: Boolean = false


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("view","dispatchTouchEvent ${viewDragHelper?.shouldInterceptTouchEvent(ev)}")
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                isIntercept = true
                downX = ev.rawX
                downY = ev.rawY
                totalMove = 0f
                totalMove = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                if (isIntercept) {
                    val moveX = ev.rawX
                    val moveY = ev.rawY
                    val dX = moveX - downX
                    val dY = moveY - downY
                    totalMove += dX
                    downX = moveX
                    downY = moveY
                    if (Math.abs(dX) != 0f && Math.abs(dY) / Math.abs(dX) < 0.5) {
                        if (mContentLeft == 0 && dX > 0 || mContentLeft > 0 && dX < 0) {
                            return true
                        }
                    }
                }
                isIntercept = false
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun convertActivityToTranslucent(activity: Activity) {
        try {
            val t = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            val len = t.size

            for (i in 0 until len) {
                val clazz = t[i]
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                    break
                }
                Log.e("invokea", "5以上")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val var8 = Activity::class.java.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions::class.java)
                var8.isAccessible = true
                var8.invoke(activity,null,null)
                Log.e("invokea", "5以上")
            } else {
                Log.e("invokea", "4.x")
                val var8 = Activity::class.java.getDeclaredMethod("convertToTranslucent",translucentConversionListenerClazz)
                var8.isAccessible = true
                var8.invoke(activity,null)
            }
        } catch (e: Throwable) {
            Log.e("invokea", e.toString())
        }

    }

}