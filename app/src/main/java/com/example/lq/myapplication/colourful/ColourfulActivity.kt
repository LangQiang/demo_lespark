package com.example.lq.myapplication.colourful

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_colourful.*

class ColourfulActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("colour", "onCreate")

        setContentView(R.layout.activity_colourful)
        Log.e("colour", "setContentView")

        start.setOnClickListener({
            colourful_tv.setMode(1)
            colourful_tv.start()
        })
        stop.setOnClickListener({
            colourful_tv.setMode(0)
            colourful_tv.stop()
        })

    }

    override fun onDestroy() {
        colourful_tv.stop()
        Log.e("colour", "onDestroypresuper")
        Log.e("colour", (colourful_tv == null).toString())

        super.onDestroy()
        Log.e("colour", "onDestroyed")
        Log.e("colour", (colourful_tv == null).toString())


    }

    override fun onStart() {
        super.onStart()
        Log.e("colour", "onStart")

    }

    override fun onStop() {
        super.onStop()
        Log.e("colour", "onStop")

    }

    override fun onResume() {
        super.onResume()
        Log.e("colour", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.e("colour", "onPause")

    }
}
