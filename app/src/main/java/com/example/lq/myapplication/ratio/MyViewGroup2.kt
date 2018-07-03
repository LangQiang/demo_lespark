package com.example.lq.myapplication.ratio

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by lq on 2018/4/23.
 */
class MyViewGroup2 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private var mTAG = RatioGroupView::class.java.simpleName + "_parent"

    init {
    }



    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.e("VIEWprocess", "$mTAG ---onLayout")
    }

}