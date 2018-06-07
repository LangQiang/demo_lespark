package com.example.lq.myapplication.likeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_like_view.*

class LikeViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_view)
        sv.setOnClickListener {
            sv.addLikeBean(R.drawable.live_show_like_1)
        }
        sv.viewTreeObserver.addOnGlobalLayoutListener {
            for (i in 0.. 1000) {
                sv.setMinGapTime(200)
                sv.addLikeBean(R.drawable.live_show_like_1)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        sv.destroy()
    }
}
