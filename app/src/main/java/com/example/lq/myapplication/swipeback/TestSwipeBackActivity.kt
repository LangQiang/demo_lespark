package com.example.lq.myapplication.swipeback

import android.content.Intent
import android.os.Bundle
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_swipe_back.*

class TestSwipeBackActivity : SwipeBackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_back)
        start_activity.setOnClickListener {
            startActivity(Intent(this,SwipeBackActivity::class.java))
        }
    }
}
