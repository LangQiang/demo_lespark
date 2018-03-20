package com.example.lq.myapplication.levelup

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import com.example.lq.myapplication.R
import com.example.lq.myapplication.ToastUtil2
import com.example.lq.myapplication.utils.UIHelper

class LevelUpActivity : AppCompatActivity() {
    var rl: RelativeLayout? = null
    var slow = 1f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_up)
        rl = findViewById(R.id.gift_container)
        var play = findViewById<Button>(R.id.play)
        play.setOnClickListener({
            show()
        })
        var tv = findViewById<TextView>(R.id.tv)
        var seek = findViewById<SeekBar>(R.id.seek)
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    slow = seekBar.progress / 30f
                    ToastUtil2.showToast(slow.toString())
                    tv.text = slow.toString()
                }
            }

        })
        seek.progress = 10
    }

    fun show() {
        rl?.removeAllViews()
        var view = View.inflate(this, R.layout.anim_level_up_layout, null)
        rl?.addView(view)

        var animSet = AnimatorSet()
        var container = view.findViewById<RelativeLayout>(R.id.level_up_container)
        var tipView = view.findViewById<View>(R.id.level_up_tip)
        var wingLeft = view.findViewById<View>(R.id.wing_left)
        var wingRight = view.findViewById<View>(R.id.wing_right)
        var flashLight = view.findViewById<View>(R.id.level_up_flash_light)
        var pointView = view.findViewById<View>(R.id.level_up_point)
        var levelNum = view.findViewById<TextView>(R.id.level_num);
        levelNum.text = 13.toString()

        //整体放大
        var containerAnimX = ObjectAnimator.ofFloat(container, "scaleX", 0.4f, 1f)
        containerAnimX.duration = (1000 * slow).toLong()
        var containerAnimY = ObjectAnimator.ofFloat(container, "scaleY", 0.4f, 1f)
        containerAnimY.duration = (1000 * slow).toLong()

        //主播升级啦 文字变换
        var tipAnimY = ObjectAnimator.ofFloat(tipView, "scaleX", 1f, 0.0f)
        tipAnimY.duration = (1000 * slow).toLong()

        var tipAnimY2 = ObjectAnimator.ofFloat(tipView, "scaleX", 0.0f, 1f)
        tipAnimY2.duration = (500 * slow).toLong()

        //翅膀左 动画1阶段
        var wingLeftAnimRotate = ObjectAnimator.ofFloat(wingLeft, "rotation", 0f)
        wingLeftAnimRotate.interpolator = LinearInterpolator()
        wingLeftAnimRotate.duration = (1000 * slow).toLong()
        var wingLeftAnimTranslationX = ObjectAnimator.ofFloat(wingLeft, View.TRANSLATION_X, UIHelper.dip2px(42.0).toFloat())
        wingLeftAnimTranslationX.interpolator = LinearInterpolator()
        wingLeftAnimTranslationX.duration = (1000 * slow).toLong()
        var wingLeftAnimTranslationY = ObjectAnimator.ofFloat(wingLeft, "translationY", UIHelper.dip2px(-90.0).toFloat())
        wingLeftAnimTranslationY.interpolator = LinearInterpolator()
        wingLeftAnimTranslationY.duration = (1000 * slow).toLong()

        //翅膀左 动画2阶段
        var wingLeftRotate2 = ObjectAnimator.ofFloat(wingLeft, "rotation", -20f)
        wingLeftRotate2.interpolator = LinearInterpolator()
        wingLeftRotate2.duration = (1000 * slow).toLong()

        //翅膀左 动画3阶段
        var wingLeftRotate3 = ObjectAnimator.ofFloat(wingLeft, "rotation", -20f,0f)
        wingLeftRotate3.interpolator = LinearInterpolator()
        wingLeftRotate3.duration = (1000 * slow).toLong()


        //翅膀右 动画1阶段
        var wingRightAnimRotate = ObjectAnimator.ofFloat(wingRight, "rotation", 0f)
        wingRightAnimRotate.interpolator = LinearInterpolator()
        wingRightAnimRotate.duration = (1000 * slow).toLong()
        var wingRightAnimTranslationX = ObjectAnimator.ofFloat(wingRight, View.TRANSLATION_X, UIHelper.dip2px(-42.0).toFloat())
        wingRightAnimTranslationX.interpolator = LinearInterpolator()
        wingRightAnimTranslationX.duration = (1000 * slow).toLong()
        var wingRightAnimTranslationY = ObjectAnimator.ofFloat(wingRight, "translationY", UIHelper.dip2px(-90.0).toFloat())
        wingRightAnimTranslationY.interpolator = LinearInterpolator()
        wingRightAnimTranslationY.duration = (1000 * slow).toLong()

        //翅膀右 动画2阶段
        var wingRightRotate2 = ObjectAnimator.ofFloat(wingRight, "rotation", 20f)
        wingRightRotate2.interpolator = LinearInterpolator()
        wingRightRotate2.duration = (1000 * slow).toLong()

        //翅膀右 动画3阶段
        var wingRightRotate3 = ObjectAnimator.ofFloat(wingRight, "rotation", 20f, 0f)
        wingRightRotate3.interpolator = LinearInterpolator()
        wingRightRotate3.duration = (1000 * slow).toLong()

        //旋转的光
        var flashLightAnim = ObjectAnimator.ofFloat(flashLight, "rotation", 90f)
        flashLightAnim.interpolator = LinearInterpolator()
        flashLightAnim.duration = (5000 * slow).toLong()

        //小点点
        var pointViewAnimTranslationY = ObjectAnimator.ofFloat(pointView, "TranslationY",0f, -UIHelper.dip2px(100.0).toFloat())
        pointViewAnimTranslationY.interpolator = LinearInterpolator()
        pointViewAnimTranslationY.duration = (1000 * slow).toLong()
        var pointViewAnimScaleX = ObjectAnimator.ofFloat(pointView, "scaleX", 1f, 0.6f)
        pointViewAnimScaleX.interpolator = LinearInterpolator()
        pointViewAnimScaleX.duration = (1000 * slow).toLong()
        var pointViewAnimAlpha1 = ObjectAnimator.ofFloat(pointView, "alpha", 1f, 0f)
        pointViewAnimAlpha1.interpolator = LinearInterpolator()
        pointViewAnimAlpha1.duration = (1000 * slow).toLong()
        var pointViewAnimAlpha2 = ObjectAnimator.ofFloat(pointView, "alpha", 0f,1f, 0f,1f)
        pointViewAnimAlpha2.interpolator = LinearInterpolator()
        pointViewAnimAlpha2.duration = (3000 * slow).toLong()

        var pointViewAnimScaleX2 = ObjectAnimator.ofFloat(pointView, "scaleX", 0.6f, 1f,0.6f,1f)
        pointViewAnimScaleX2.interpolator = LinearInterpolator()
        pointViewAnimScaleX2.duration = (3000 * slow).toLong()

        var pointViewAnimScaleY = ObjectAnimator.ofFloat(pointView, "scaleY", 1f, 0.6f)
        pointViewAnimScaleY.interpolator = LinearInterpolator()
        pointViewAnimScaleY.duration = (1000 * slow).toLong()
        var pointViewAnimScaleY2 = ObjectAnimator.ofFloat(pointView, "scaleY", 0.6f, 1f, 0.6f,1f)
        pointViewAnimScaleY2.interpolator = LinearInterpolator()
        pointViewAnimScaleY2.duration = (3000 * slow).toLong()

        //整体上下晃动
        var containerAnimTranslationY = ObjectAnimator.ofFloat(container, "TranslationY", 0f, -UIHelper.dip2px(20.0).toFloat(),0f,-UIHelper.dip2px(20.0).toFloat(),0f)
        containerAnimTranslationY.interpolator = LinearInterpolator()
        containerAnimTranslationY.duration = (4000 * slow).toLong()

        //数字变换
        var levelNumAnimScaleX = ObjectAnimator.ofFloat(levelNum, "scaleX", 1f, 0.1f)
        levelNumAnimScaleX.interpolator = DecelerateInterpolator()
        levelNumAnimScaleX.duration = (1000 * slow).toLong()
        var levelNumAnimScaleY = ObjectAnimator.ofFloat(levelNum, "scaleY", 1f, 0.1f)
        levelNumAnimScaleY.interpolator = DecelerateInterpolator()
        levelNumAnimScaleY.duration = (1000 * slow).toLong()

        var levelNumAnimScaleX2 = ObjectAnimator.ofFloat(levelNum, "scaleX", 0.1f, 1f)
        levelNumAnimScaleX2.interpolator = AccelerateInterpolator()
        levelNumAnimScaleX2.duration = (1000 * slow).toLong()
        var levelNumAnimScaleY2 = ObjectAnimator.ofFloat(levelNum, "scaleY", 0.1f, 1f)
        levelNumAnimScaleY2.interpolator = AccelerateInterpolator()
        levelNumAnimScaleY2.duration = (1000 * slow).toLong()



        wingRightRotate2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                wingRight.pivotX = UIHelper.dip2px(0.0).toFloat()
                wingRight.pivotY = UIHelper.dip2px(74.0).toFloat()
                wingLeft.pivotX = UIHelper.dip2px(79.0).toFloat()
                wingLeft.pivotY = UIHelper.dip2px(74.0).toFloat()
            }
        })

        levelNumAnimScaleX2.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                levelNum.text = 14.toString()
            }
        })

        animSet.play(containerAnimX)
        animSet.play(containerAnimY).with(containerAnimX)
        animSet.play(pointViewAnimTranslationY).after(containerAnimY)
        animSet.play(flashLightAnim).after(containerAnimY)
        animSet.play(tipAnimY).after(containerAnimY)
        animSet.play(tipAnimY2).after(tipAnimY)
        animSet.play(containerAnimTranslationY).after(tipAnimY)
        animSet.play(levelNumAnimScaleX).after(tipAnimY)
        animSet.play(levelNumAnimScaleY).after(tipAnimY)
        animSet.play(levelNumAnimScaleX2).after(levelNumAnimScaleX)
        animSet.play(levelNumAnimScaleY2).after(levelNumAnimScaleX)
        animSet.play(wingLeftAnimRotate).after(containerAnimY)
        animSet.play(wingLeftAnimTranslationX).with(wingLeftAnimRotate)
        animSet.play(wingLeftAnimTranslationY).with(wingLeftAnimRotate)
        animSet.play(wingRightAnimRotate).with(wingLeftAnimRotate)
        animSet.play(wingRightAnimTranslationX).with(wingLeftAnimRotate)
        animSet.play(wingRightAnimTranslationY).with(wingLeftAnimRotate)
        animSet.play(wingLeftRotate2).after(wingLeftAnimRotate)
        animSet.play(wingRightRotate2).after(wingLeftAnimRotate)
        animSet.play(pointViewAnimScaleX).after(wingLeftAnimRotate)
        animSet.play(pointViewAnimAlpha1).after(wingLeftAnimRotate)
        animSet.play(pointViewAnimScaleY).after(wingLeftAnimRotate)
        animSet.play(wingRightRotate3).after(wingRightRotate2)
        animSet.play(wingLeftRotate3).after(wingRightRotate2)
        animSet.play(pointViewAnimScaleX2).after(wingRightRotate2)
        animSet.play(pointViewAnimScaleY2).after(wingRightRotate2)
        animSet.play(pointViewAnimAlpha2).after(wingRightRotate2)
        animSet.start()

        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                rl?.removeAllViews();
            }
        })

    }
}

