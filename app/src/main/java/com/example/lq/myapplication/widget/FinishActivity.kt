package com.example.lq.myapplication.widget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.lq.myapplication.R

open class FinishActivity : AppCompatActivity() {
    open val leftFinishMode : Int = 0
    open val allFinishMode = 1
    open val middleFinishMode = 2
    open val banFinishMode = 3
    var finishMode : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("touch","dispatchTouchEvent")
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> actionDown(ev)
            MotionEvent.ACTION_MOVE -> actionMove(ev)
            MotionEvent.ACTION_UP -> actionUp(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun actionUp(ev: MotionEvent) {

    }

    private fun actionMove(ev: MotionEvent) {

    }

    private fun actionDown(ev: MotionEvent) {
        
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}
