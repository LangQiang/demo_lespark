package com.example.lq.myapplication.swipeback

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_swipe_back.*

open class SwipeBackActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        val swipeBackLayout = SwipeBackLayout(this)
        val layoutParams : ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        swipeBackLayout.layoutParams = layoutParams
        swipeBackLayout.attachToActivity(this)
    }
}
