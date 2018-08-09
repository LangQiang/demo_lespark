package com.example.lq.myapplication.anims

import android.animation.*
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.*
import com.example.lq.myapplication.R
import com.example.lq.myapplication.diffusion.DiffusionAnimator
import com.example.lq.myapplication.utils.ToastUtil2
import com.example.lq.myapplication.utils.UIHelper
import kotlinx.android.synthetic.main.anim_travel_with_you_layout.*
import java.util.*

class TravelWithYouActivity : AppCompatActivity() {
    var rl: RelativeLayout? = null
    var slow = 1f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_with_you)
        rl = findViewById(R.id.gift_container)
        val play = findViewById<Button>(R.id.play)
        play.setOnClickListener({
            show(rl)
        })
        val tv = findViewById<TextView>(R.id.tv)
        val seek = findViewById<SeekBar>(R.id.seek)
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
        seek.progress = 30
    }

    private fun show(rl: RelativeLayout?) {
        rl?.removeAllViews()
        val view = View.inflate(this, R.layout.anim_travel_with_you_layout, null)
        rl?.addView(view, RelativeLayout.LayoutParams(-1, -1))
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)

                val diffusionSrc = intArrayOf(R.drawable.travel_flower1, R.drawable.travel_flower2)

                val travelCloudLeftView = view.findViewById<View>(R.id.travel_cloud_left)
                val travelCloudRightView = view.findViewById<View>(R.id.travel_cloud_right)
                val travelCamelView = view.findViewById<View>(R.id.travel_camel)
                val travelDesertIv = view.findViewById<ImageView>(R.id.travel_desert)
                val travelCarContainerView = view.findViewById<View>(R.id.travel_car_container)
                val travelBowknot = view.findViewById<View>(R.id.travel_bowknot)
                val travelAvatarLeft = view.findViewById<ImageView>(R.id.travel_avatar_left)
                val travelAvatarRight = view.findViewById<ImageView>(R.id.travel_avatar_right)
                val travelBalloonView1 = view.findViewById<View>(R.id.travel_balloon1)
                val travelBalloonView2 = view.findViewById<View>(R.id.travel_balloon2)
                val travelBalloonView3 = view.findViewById<View>(R.id.travel_balloon3)
                val travelBalloonView4 = view.findViewById<View>(R.id.travel_balloon4)
                val travelBalloonView5 = view.findViewById<View>(R.id.travel_balloon5)
                val travelBalloonView6 = view.findViewById<View>(R.id.travel_balloon6)
                val travelBalloonView7 = view.findViewById<View>(R.id.travel_balloon7)
                val travelFlowerContainer = view.findViewById<FrameLayout>(R.id.travel_flower_container)
                val travelGrassLeftView = view.findViewById<View>(R.id.travel_grass_left)
                val travelGrassRightView = view.findViewById<View>(R.id.travel_grass_right)
                val travelSeaMewBigView = view.findViewById<View>(R.id.travel_sea_mew_big)
                val travelSeaMewSmallView = view.findViewById<View>(R.id.travel_sea_mew_small)
                val travelYeZiShuLeftView = view.findViewById<View>(R.id.travel_yezishu_left)
                val travelYeZiShuRightView = view.findViewById<View>(R.id.travel_yezishu_right)
                val travelOtherSeaMewBigView = view.findViewById<View>(R.id.travel_othor_sea_mew_big)
                val travelOtherSeaMewSmallView = view.findViewById<View>(R.id.travel_othor_sea_mew_small)
                val travelMapleLeftView = view.findViewById<View>(R.id.travel_maple_left)
                val travelMapleRightView = view.findViewById<View>(R.id.travel_maple_right)
                val travelPlaneView = view.findViewById<View>(R.id.travel_plane)

                val balloonViews = arrayOf(travelBalloonView1,
                        travelBalloonView2,
                        travelBalloonView3,
                        travelBalloonView4,
                        travelBalloonView5,
                        travelBalloonView6,
                        travelBalloonView7)
                for (balloonView in balloonViews) {
                    balloonView.scaleX = 0f
                    balloonView.scaleY = 0f
                }

                val rootWidth = travel_desert.width.toFloat()
                val rootHeight = travel_desert.height.toFloat()

                val animSet = AnimatorSet()
                //18s
                val camelDuration = setDuration(2500)
                val carRound1Duration = setDuration(800)
                val avatarTransRound2Duration = setDuration(1300)
                val transitionDuration = setDuration(750)

                //樱花飘飘
                val random = Random()
                val diffusionAnim = DiffusionAnimator.ofCustomPosition(travelFlowerContainer, 20, diffusionSrc, object : DiffusionAnimator.CustomInitViewPosition {
                    override fun initViewPosition(list: ArrayList<DiffusionAnimator.ParticleAnimBean>?, width: Int, height: Int, density: Int, duration: Int) {
                        if (list != null) {
                            for (particleAnimBean in list) {
                                val top: Int
                                val left: Int
                                top = random.nextInt(height)
                                left = random.nextInt(width)
                                val start = Point(left,
                                        top)
                                particleAnimBean.startPoint = start
                                particleAnimBean.imageView.setX(start.x.toFloat())
                                particleAnimBean.imageView.setY(start.y.toFloat())
                                val end: Point
                                if (left > width / 2) {
                                    end = Point(start.x - random.nextInt(width), start.y + random.nextInt(height))
                                } else {
                                    end = Point(start.x + random.nextInt(width), start.y + random.nextInt(height))
                                }
                                particleAnimBean.endPoint = end
                                val controllPoint = Point((start.x + end.x) / 2, (start.y + end.y) / 2)
                                val bezierEvaluator = DiffusionAnimator.BezierEvaluator(controllPoint, DiffusionAnimator.BEHAVIOR_CUSTOM)
                                particleAnimBean.bezierEvaluator = bezierEvaluator
                                val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                                particleAnimBean.valueAnimator = valueAnimator
                                valueAnimator.duration = (2000 + random.nextInt(600) - 300).toLong()
                                valueAnimator.startDelay = random.nextInt((3000 * 1.5).toInt()).toLong()
                                valueAnimator.addUpdateListener { animation ->
                                    val pointF = particleAnimBean.bezierEvaluator.evaluate(animation.animatedValue as Float, particleAnimBean.startPoint, particleAnimBean.endPoint)
                                    particleAnimBean.imageView.setX(pointF.x.toFloat())
                                    particleAnimBean.imageView.setY(pointF.y.toFloat())
                                    particleAnimBean.imageView.alpha = 1 - animation.animatedValue as Float
                                }
                                valueAnimator.addListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationStart(animation: Animator) {
                                        particleAnimBean.imageView.setVisibility(View.VISIBLE)
                                    }

                                    override fun onAnimationEnd(animation: Animator?) {
                                        particleAnimBean.imageView.setVisibility(View.INVISIBLE)
                                    }
                                })
                            }
                        }
                    }
                })


                //左云 全程
                val cloudLeftTransX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, UIHelper.getScreenWidth() + UIHelper.dip2px(100.toDouble()).toFloat())
                val cloudLeftAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f)
                val cloudLeftAnim = ObjectAnimator.ofPropertyValuesHolder(travelCloudLeftView, cloudLeftTransX, cloudLeftAlpha)
                cloudLeftAnim.duration = (18000 * slow).toLong()
                cloudLeftAnim.interpolator = LinearInterpolator()

                //右云 全程
                val cloudRightTransX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -(UIHelper.getScreenWidth() + UIHelper.dip2px(100.toDouble()).toFloat()))
                val cloudRightAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f)
                val cloudRightAnim = ObjectAnimator.ofPropertyValuesHolder(travelCloudRightView, cloudRightTransX, cloudRightAlpha)
                cloudRightAnim.duration = (18000 * slow).toLong()

                //骆驼动画 平移 旋转 放大
                val camelTranslateX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -(UIHelper.getScreenWidth() + UIHelper.dip2px(170.toDouble())).toFloat())
                val camelRotate = PropertyValuesHolder.ofFloat(View.ROTATION, -5f, 5f, -5f, 5f, -5f, 5f)
                val camelScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f)
                val camelScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f)
                val camelAnim = ObjectAnimator.ofPropertyValuesHolder(travelCamelView,
                        camelTranslateX,
                        camelRotate,
                        camelScaleX,
                        camelScaleY)
                camelAnim.duration = camelDuration

                //灰机来了
                val planeTransX1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,UIHelper.dip2px(360.toDouble()).toFloat())
                val planeTransY1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,UIHelper.dip2px(100.toDouble()).toFloat())
                val planeAnim1 = ObjectAnimator.ofPropertyValuesHolder(travelPlaneView,planeTransX1,planeTransY1)
                planeAnim1.duration = setDuration(1500)


                //枫叶动
                travelMapleLeftView.alpha = 0f
                travelMapleRightView.alpha = 0f
                val mapleLeftAlphaAnim1 = ObjectAnimator.ofFloat(travelMapleLeftView,View.ALPHA,1f)
                mapleLeftAlphaAnim1.duration = transitionDuration / 2
                val mapleRightAlphaAnim1 = ObjectAnimator.ofFloat(travelMapleRightView,View.ALPHA,1f)
                mapleRightAlphaAnim1.duration = transitionDuration / 2
                val mapleLeftAnim = ObjectAnimator.ofFloat(travelMapleLeftView, View.ROTATION, -10f, 0f, -10f, 0f, -10f, 0f, -10f, 0f, -10f, 0f, -10f, 0f, -10f, 0f, -10f, 0f)
                mapleLeftAnim.duration = setDuration(3000)
                val mapleRightAnim = ObjectAnimator.ofFloat(travelMapleRightView, View.ROTATION, 10f, 0f, 10f, 0f, 10f, 0f, 10f, 0f, 10f, 0f, 10f, 0f, 10f, 0f, 10f, 0f)
                mapleRightAnim.duration = setDuration(3000)

                //薰衣草
                travelGrassLeftView.alpha = 0f
                travelGrassRightView.alpha = 0f

                val grassLeftAlphaAnim = ObjectAnimator.ofFloat(travelGrassLeftView, View.ALPHA, 1f)
                val grassRightAlphaAnim = ObjectAnimator.ofFloat(travelGrassRightView, View.ALPHA, 1f)
                grassLeftAlphaAnim.duration = setDuration(150)
                grassRightAlphaAnim.duration = setDuration(150)
                val grassTransX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, UIHelper.dip2px(-80.toDouble()).toFloat())
                val grassTransX2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, UIHelper.dip2px(80.toDouble()).toFloat())
                val grassTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, UIHelper.dip2px(147.toDouble()).toFloat())
                val grassRotate = PropertyValuesHolder.ofFloat(View.ROTATION, -5f, 0f, -5f, 0f, -5f, 0f, -5f)
                val grassLeftAnim = ObjectAnimator.ofPropertyValuesHolder(travelGrassLeftView, grassTransX, grassTransY, grassRotate)
                grassLeftAnim.duration = setDuration(3500)
                val grassRightAnim = ObjectAnimator.ofPropertyValuesHolder(travelGrassRightView, grassTransX2, grassTransY, grassRotate)
                grassRightAnim.duration = setDuration(3500)

                //椰子树
                travelYeZiShuLeftView.alpha = 0f
                travelYeZiShuRightView.alpha = 0f
                val yezishuLeftAlphaAnim = ObjectAnimator.ofFloat(travelYeZiShuLeftView, View.ALPHA, 1f)
                yezishuLeftAlphaAnim.duration = setDuration(500)
                val yezishuRightAlphaAnim = ObjectAnimator.ofFloat(travelYeZiShuRightView, View.ALPHA, 1f)
                yezishuRightAlphaAnim.duration = setDuration(500)
                val yezishuLeftRotate = PropertyValuesHolder.ofFloat(View.ROTATION, -5f, 0f, -5f, 0f, -5f, 0f, -5f, 0f)
                val yezishuRightRotate = PropertyValuesHolder.ofFloat(View.ROTATION, 5f, 0f, 5f, 0f, 5f, 0f, 5f, 0f)
                val yezishuLeftScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.3f)
                val yezishuLeftScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.3f)
                val yezishuLeftTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100f)
                val yezishuLeftAnim = ObjectAnimator.ofPropertyValuesHolder(travelYeZiShuLeftView,
                        yezishuLeftRotate,
                        yezishuLeftScaleX,
                        yezishuLeftScaleY,
                        yezishuLeftTransY)
                yezishuLeftAnim.duration = setDuration(3500)
                val yezishuRightAnim = ObjectAnimator.ofPropertyValuesHolder(travelYeZiShuRightView,
                        yezishuRightRotate,
                        yezishuLeftScaleX,
                        yezishuLeftScaleY,
                        yezishuLeftTransY)
                yezishuRightAnim.duration = setDuration(3500)

                val yeziHideLeftAnim = ObjectAnimator.ofFloat(travelYeZiShuLeftView, View.ALPHA, 0f)
                yeziHideLeftAnim.duration = setDuration(375)
                val yeziHideRightAnim = ObjectAnimator.ofFloat(travelYeZiShuRightView, View.ALPHA, 0f)
                yeziHideRightAnim.duration = setDuration(375)

                //沙漠背景放大  3500
                travelDesertIv.pivotX = travel_desert.width / 2f
                travelDesertIv.pivotY = travel_desert.height / 4f * 3f
                val desertScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.3f)
                val desertScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.3f)
                val desertAnim = ObjectAnimator.ofPropertyValuesHolder(travelDesertIv, desertScaleX, desertScaleY)
                desertAnim.duration = setDuration(3500)

                //草原放大 位移
                val desertAnim2 = ObjectAnimator.ofPropertyValuesHolder(travelDesertIv, desertScaleX, desertScaleY)
                desertAnim2.duration = setDuration(3500)
                desertAnim2.interpolator = AccelerateInterpolator()
                val desertAnim2_1 = ObjectAnimator.ofFloat(travelDesertIv, View.TRANSLATION_Y, UIHelper.dip2px(50.toDouble()).toFloat())
                desertAnim2_1.duration = setDuration(3500)
                desertAnim2_1.interpolator = AccelerateInterpolator()


                //场景转换 透明度
                val desertToGrasslandAnim = ObjectAnimator.ofFloat(travelDesertIv, View.ALPHA, 1f, 0.3f, 1f)
                desertToGrasslandAnim.duration = setDuration(300)
                desertToGrasslandAnim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        travelDesertIv.postDelayed({
                            desertAnim.end()
                            travelDesertIv.scaleX = 1f
                            travelDesertIv.scaleY = 1f
                            travelDesertIv.setImageResource(R.drawable.travel_grassland_bg)
                            //飘落的薰衣草
                            diffusionAnim.start()
                            grassRightAlphaAnim.start()
                            grassLeftAlphaAnim.start()
                            desertAnim2.start()
                            desertAnim2_1.start()
                        }, setDuration(150))
                    }
                })
                //转到海滩
                val grasslandToBeachAnim = ObjectAnimator.ofFloat(travelDesertIv, View.ALPHA, 1f, 0.3f, 1f)
                grasslandToBeachAnim.duration = setDuration(300)
                grasslandToBeachAnim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        travelDesertIv.postDelayed({
                            travelDesertIv.scaleX = 1f
                            travelDesertIv.scaleY = 1f
                            travelDesertIv.translationY = 0f
                            travelDesertIv.setImageResource(R.drawable.travel_beach_bg)
                            yezishuLeftAlphaAnim.start()
                            yezishuRightAlphaAnim.start()
                            yezishuRightAnim.start()
                            yezishuLeftAnim.start()
                            desertAnim.start()
                        }, setDuration(150))
                    }
                })

                val beachToMapleAnim = ObjectAnimator.ofFloat(travelDesertIv, View.ALPHA, 1f, 0.3f, 1f)
                beachToMapleAnim.duration = setDuration(300)
                beachToMapleAnim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        travelDesertIv.postDelayed({
                            travelDesertIv.scaleX = 1f
                            travelDesertIv.scaleY = 1f
                            travelDesertIv.translationY = 0f
                            travelDesertIv.setImageResource(R.drawable.travel_maple_bg)
                            mapleLeftAnim.start()
                            mapleRightAnim.start()
                            mapleLeftAlphaAnim1.start()
                            mapleRightAlphaAnim1.start()
                            desertAnim.start()
                            planeAnim1.start()
                        }, setDuration(150))
                    }
                })


                //车 800
                travelCarContainerView.alpha = 0.1f
                travelCarContainerView.scaleX = 1.2f
                travelCarContainerView.scaleY = 1.2f

                val transY = -UIHelper.dip2px(69.toDouble()).toFloat()
                val carContainerTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, transY)
                val carContainerAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f)
                val carContainerScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
                val carContainerScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
                val carAnimRound1 = ObjectAnimator.ofPropertyValuesHolder(
                        travelCarContainerView,
                        carContainerTransY,
                        carContainerAlpha,
                        carContainerScaleX,
                        carContainerScaleY)
                carAnimRound1.duration = carRound1Duration
                carAnimRound1.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        travelCarContainerView.visibility = View.VISIBLE
                    }
                })

                val transY2 = transY - UIHelper.dip2px(8.toDouble())
                val carContainerTransY2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, transY, transY2, transY, transY2, transY, transY2, transY, transY2, transY, transY2, transY, transY2, transY, transY2, transY)
                val carContainerScaleX2 = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f)
                val carContainerScaleY2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f)
                val carAnimRound2 = ObjectAnimator.ofPropertyValuesHolder(travelCarContainerView,
                        carContainerTransY2,
                        carContainerScaleX2,
                        carContainerScaleY2)
                //18000 - 2500 - 800
                carAnimRound2.duration = setDuration(14700)

                //头像
                travelAvatarLeft.alpha = 0f
                travelAvatarRight.alpha = 0f

                val avatarLeftAlphaAnimRound1 = ObjectAnimator.ofFloat(travelAvatarLeft, View.ALPHA, 1f)
                avatarLeftAlphaAnimRound1.duration = setDuration(800)
                val avatarRightAlphaAnimRound1 = ObjectAnimator.ofFloat(travelAvatarRight, View.ALPHA, 1f)
                avatarRightAlphaAnimRound1.duration = setDuration(800)
                val avatarTransY1 = UIHelper.dip2px(-30.toDouble()).toFloat()
                val avatarLeftTransYAnimRound2 = ObjectAnimator.ofFloat(travelAvatarLeft, View.TRANSLATION_Y, avatarTransY1)
                avatarLeftTransYAnimRound2.duration = avatarTransRound2Duration
                val avatarRightTransYAnimRound2 = ObjectAnimator.ofFloat(travelAvatarRight, View.TRANSLATION_Y, avatarTransY1)
                avatarRightTransYAnimRound2.duration = avatarTransRound2Duration

                val avatarLeftScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f)
                val avatarLeftScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f)
                val avatarLeftAnimRound3 = ObjectAnimator.ofPropertyValuesHolder(travelAvatarLeft, avatarLeftScaleX, avatarLeftScaleY)
                avatarLeftAnimRound3.duration = setDuration(13000)

                val avatarRightScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f)
                val avatarRightScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f, 1f, 1.05f)
                val avatarRightAnimRound3 = ObjectAnimator.ofPropertyValuesHolder(travelAvatarRight, avatarRightScaleX, avatarRightScaleY)
                avatarRightAnimRound3.duration = setDuration(13000)


                //热气球
                val balloonTransYValue = -UIHelper.dip2px(200.toDouble()).toFloat()
                val balloonTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, balloonTransYValue)
                val balloonScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2f)
                val balloonScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2f)

                val balloonAnims = arrayOfNulls<ObjectAnimator>(7)
                val balloonStartAnims = arrayOfNulls<ObjectAnimator>(7)
                val balloonEndAnims = arrayOfNulls<ObjectAnimator>(7)

                for (i in 0..6) {
                    balloonViews[i].visibility = View.VISIBLE
                    balloonAnims[i] = ObjectAnimator.ofPropertyValuesHolder(balloonViews[i], balloonTransY, balloonScaleX, balloonScaleY)
                    balloonAnims[i]?.duration = setDuration(2000)

                    balloonStartAnims[i] = ObjectAnimator.ofFloat(balloonViews[i], View.ALPHA, 1f)
                    balloonStartAnims[i]?.duration = setDuration(500)

                    balloonEndAnims[i] = ObjectAnimator.ofFloat(balloonViews[i], View.ALPHA, 0f)
                    balloonEndAnims[i]?.duration = setDuration(500)

                    balloonAnims[i]?.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            balloonStartAnims[i]?.start()
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            balloonEndAnims[i]?.start()
                        }
                    })
                }
                balloonAnims[2]?.startDelay = setDuration(500)
                balloonAnims[3]?.startDelay = setDuration(1700)
                balloonAnims[4]?.startDelay = setDuration(1800)
                balloonAnims[5]?.startDelay = setDuration(3800)
                balloonAnims[6]?.startDelay = setDuration(4300)


                //海鸥灰过
                val bigTransX = UIHelper.dip2px(336.toDouble()) + rootWidth
                val bigTransY = UIHelper.dip2px(60.toDouble())
                val bigPoint = Point((bigTransX / 2f).toInt(), bigTransY)
                val startPoint = Point(0, 0)
                val bigEndPoint = Point(bigTransX.toInt(), bigTransY)
                val seaMewBigBezier = DiffusionAnimator.BezierEvaluator(bigPoint, 0)

                val smallTransX = UIHelper.dip2px(330.toDouble()) + rootWidth
                val smallTransY = UIHelper.dip2px(70.toDouble())
                val smallPoint = Point((smallTransX / 3f * 2f).toInt(), smallTransY)
                val smallEndPoint = Point(smallTransX.toInt(), smallTransY)
                val seaMewSmallBezier = DiffusionAnimator.BezierEvaluator(smallPoint, 0)

                val seaMewAnim = ValueAnimator.ofFloat(0f, 1f)
                seaMewAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        val pointF = seaMewBigBezier.evaluate(animation?.animatedValue as Float, startPoint, bigEndPoint)
                        travelSeaMewBigView.translationX = pointF.x.toFloat()
                        travelSeaMewBigView.translationY = pointF.y.toFloat()
                        val pointF2 = seaMewSmallBezier.evaluate(animation?.animatedValue as Float, startPoint, smallEndPoint)
                        travelSeaMewSmallView.translationX = pointF2.x.toFloat()
                        travelSeaMewSmallView.translationY = pointF2.y.toFloat()
                    }
                })
                seaMewAnim.duration = setDuration(1000)


                val seaMewSmallTransX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, smallTransX + UIHelper.dip2px(31.toDouble()))
                val seaMewSmallTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, smallTransY + 20f)
                val seaMewSmallSlowAnim = ObjectAnimator.ofPropertyValuesHolder(travelSeaMewSmallView, seaMewSmallTransX, seaMewSmallTransY)
                seaMewSmallSlowAnim.duration = setDuration(2000)

                //另外的两只海鸥
                val otherBigTransX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, rootWidth)
                val otherBigTransY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, UIHelper.dip2px(20.toDouble()).toFloat(), UIHelper.dip2px(10.toDouble()).toFloat())
                val otherBigAnim = ObjectAnimator.ofPropertyValuesHolder(travelOtherSeaMewBigView, otherBigTransX, otherBigTransY)
                otherBigAnim.duration = setDuration(1500)
                val otherSmallAnim = ObjectAnimator.ofPropertyValuesHolder(travelOtherSeaMewSmallView, otherBigTransX, otherBigTransY)
                otherSmallAnim.duration = setDuration(1500)
                val otherSeaMewAlphaAnim1 = ObjectAnimator.ofFloat(travelOtherSeaMewBigView, View.ALPHA, 0f)
                otherSeaMewAlphaAnim1.duration = setDuration(375)
                val otherSeaMewAlphaAnim2 = ObjectAnimator.ofFloat(travelOtherSeaMewSmallView, View.ALPHA, 0f)
                otherSeaMewAlphaAnim2.duration = setDuration(375)


                seaMewSmallSlowAnim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        travelSeaMewSmallView.postDelayed({
                            otherBigAnim.start()
                            otherSmallAnim.start()
                        }, 800)
                    }
                })


                //骆驼 with 云
                animSet.play(camelAnim)
                animSet.play(cloudRightAnim).with(camelAnim)
                animSet.play(cloudLeftAnim).with(camelAnim)

                //在骆驼结束 沙漠背景放大 车平移出现 头像透明
                animSet.play(desertAnim).after(camelAnim)
                animSet.play(carAnimRound1).after(camelAnim)
                animSet.play(avatarLeftAlphaAnimRound1).after(camelAnim)
                animSet.play(avatarRightAlphaAnimRound1).after(camelAnim)

                //车平移出现之后 持续模拟开动效果 直到结束
                animSet.play(carAnimRound2).after(carAnimRound1)

                //车平移出现之后 头像开始向上平移
                animSet.play(avatarLeftTransYAnimRound2).after(carAnimRound1)
                animSet.play(avatarRightTransYAnimRound2).after(carAnimRound1)

                //头像向上平移之后 开始放大缩小直到飞机来
                animSet.play(avatarLeftAnimRound3).after(avatarRightTransYAnimRound2)
                animSet.play(avatarRightAnimRound3).after(avatarRightTransYAnimRound2)
                //头像向上平移之后 热气球出现
                for (i in 0..6) {
                    animSet.play(balloonAnims[i]).after(avatarRightTransYAnimRound2)
                }
                animSet.play(desertToGrasslandAnim).after(camelDuration + carRound1Duration + avatarTransRound2Duration + 800)

                //场景转换完成
                animSet.play(grassLeftAnim).after(desertToGrasslandAnim)
                animSet.play(grassRightAnim).after(desertToGrasslandAnim)

                //转到海滩 海鸥灰过
                animSet.play(grasslandToBeachAnim).after(grassLeftAnim)
                animSet.play(seaMewAnim).after(grassLeftAnim)

                //海鸥之后 慢飞
                animSet.play(seaMewSmallSlowAnim).after(seaMewAnim)

                //慢飞后 枫叶林背景  隐藏海滩背景
                animSet.play(yeziHideRightAnim).after(seaMewSmallSlowAnim)
                animSet.play(yeziHideLeftAnim).after(seaMewSmallSlowAnim)
                animSet.play(otherSeaMewAlphaAnim2).after(seaMewSmallSlowAnim)
                animSet.play(otherSeaMewAlphaAnim1).after(seaMewSmallSlowAnim)
                animSet.play(beachToMapleAnim).after(seaMewSmallSlowAnim)

                animSet.interpolator = LinearInterpolator()
                animSet.start()

                val testSet = AnimatorSet()
                testSet.play(seaMewAnim)
                testSet.play(seaMewSmallSlowAnim).after(seaMewAnim)
                val test = view.findViewById<TextView>(R.id.test)
                test.setOnClickListener {
                    seaMewAnim.duration = setDuration(1000)
                    seaMewSmallSlowAnim.duration = setDuration(2000)
                    testSet.start()
                }
            }
        })
    }

    private fun setDuration(duration: Int): Long {
        return (duration * slow).toLong()
    }

}

