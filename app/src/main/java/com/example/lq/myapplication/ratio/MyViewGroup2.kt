package com.example.lq.myapplication.ratio

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout

/**
 * Created by lq on 2018/4/23.
 */
class MyViewGroup2 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var mTAG = RatioGroupView::class.java.simpleName + "_parent"

    init {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var myWidth = -1
        var myHeight = -1

        var width = 0
        var height = 0

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        // Record our dimensions if they are known;
        if (widthMode != View.MeasureSpec.UNSPECIFIED) {
            myWidth = widthSize
        }

        if (heightMode != View.MeasureSpec.UNSPECIFIED) {
            myHeight = heightSize
        }

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = myWidth
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = myHeight
        }
        Log.e("relative","$heightSize")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.e("VIEWprocess", "$mTAG ---onLayout")
    }

}