package com.example.lq.myapplication.swipeback

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_swipe_back.*

class SwipeBackActivity : AppCompatActivity() {
    open val leftFinishMode : Int = 0
    open val allFinishMode = 1
    open val middleFinishMode = 2
    open val banFinishMode = 3
    var finishMode : Int = 0
    var rootView : View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_back)
        val swipeBackLayout = SwipeBackLayout(this)
        val layoutParams : ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        swipeBackLayout.layoutParams = layoutParams
        swipeBackLayout.attachToActivity(this)
        start_activity.setOnClickListener {
            startActivity(Intent(this,SwipeBackActivity::class.java))
        }
    }

    override fun setContentView(layoutResID: Int) {
        rootView = View.inflate(this,layoutResID,null)
        setContentView(rootView)
    }
}
