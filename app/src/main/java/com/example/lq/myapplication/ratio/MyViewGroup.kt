package com.example.lq.myapplication.ratio

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by lq on 2018/4/23.
 */
class MyViewGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mTAG = RatioGroupView::class.java.simpleName

    init {
    }

    override fun measureChild(child: View?, parentWidthMeasureSpec: Int, parentHeightMeasureSpec: Int) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec)
        Log.e("VIEWprocess","$mTAG ---measureChild")
    }

    override fun measureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec)
        Log.e("VIEWprocess","$mTAG ---measureChildren")
    }

    override fun measureChildWithMargins(child: View?, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
        Log.e("VIEWprocess","$mTAG ---measureChildWithMargins")
    }

    override fun getMeasureAllChildren(): Boolean {
        return super.getMeasureAllChildren()
        Log.e("VIEWprocess","$mTAG ---getMeasureAllChildren")
    }

    override fun setMeasureAllChildren(measureAll: Boolean) {
        super.setMeasureAllChildren(measureAll)
        Log.e("VIEWprocess","$mTAG ---setMeasureAllChildren")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("VIEWprocess","$mTAG ---onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("VIEWprocess","$mTAG ---onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e("VIEWprocess","$mTAG ---onDraw")
    }
}