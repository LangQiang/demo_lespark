package com.example.lq.myapplication.anims

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.lq.myapplication.R
import com.example.lq.myapplication.utils.ToastUtil2
import com.example.lq.myapplication.utils.UIHelper
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import kotlinx.android.synthetic.main.activity_anims.*
import java.io.InputStream
import java.net.URL

class AnimsActivity : AppCompatActivity() {
    var ivW : Int = 0
    var ivH : Int = 0
    var frame : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anims)
        val heart = HeartImageView(container.context)
        heart.setVisibility(View.INVISIBLE)
        val layoutParams = RelativeLayout.LayoutParams(UIHelper.dip2px(53.0), UIHelper.dip2px(46.0))
        heart.setLayoutParams(layoutParams)
        container.addView(heart)
//        heart.setImageResource(R.drawable.timg)
        Glide.with(this).load("http://img1.lespark.cn/86nLgSBXDZTPGmfWDxvk").into(heart)
        heart.setOnClickListener({
            ToastUtil2.showToast(frame.toString())
        })
        mSvgImage.post({
            ivW = mSvgImage.width
            ivH = mSvgImage.height
        })
        mSvgImage.callback = object : SVGACallback {
            override fun onPause() {

            }

            override fun onFinished() {
                mSvgImage.visibility = View.GONE
                Log.e("svga","onfinished")
            }

            override fun onRepeat() {

            }

            override fun onStep(i: Int, v: Double) {
                Log.e("svga","${mSvgImage.width}  ${mSvgImage.height}  $i  $v  ${mSvgImage.drawable.intrinsicWidth} ${mSvgImage.drawable.intrinsicHeight}  ")
                if (i == 98) {
                    ToastUtil2.showToast("visible")
                }
                frame = i
            }

        }
        var parser = SVGAParser(this)

        //http://img1.lespark.cn/86nLgSBXDZTPGmfWDxvk
        cake.setOnClickListener {
            heart.visibility = View.VISIBLE

            try {
                parser.parse("5.svga",object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        val drawable = SVGADrawable(videoItem)
                        mSvgImage.setImageDrawable(drawable)
                        mSvgImage.loops = 1
                        mSvgImage.startAnimation()
                        mSvgImage.visibility = View.VISIBLE
                        if (ivH.toFloat() / ivW > 2.1653f) {
                            val avatarY = ivH * 0.6515f //这个比例是取得top
                            val avatarX = ivW * 0.5f
                            heart.x = avatarX - heart.width / 2.0f
                            heart.y = avatarY
                            Log.e("svga","缩放宽")
                        } else {
                            val realH = ivW.toFloat() / 750f * 1624f
                            val avatarY = realH * 0.6515f - (realH - ivH) / 2.0f
                            val avatarX = ivW * 0.5f
                            heart.x = avatarX - heart.width / 2.0f
                            heart.y = avatarY
                            Log.e("svga","缩放高")
                        }
//                        heart.x = 0f
//                        heart.y = 0f
                    }

                    override fun onError() {

                    }
                })

            } catch (e: Exception) {
            }
        }
    }
}

