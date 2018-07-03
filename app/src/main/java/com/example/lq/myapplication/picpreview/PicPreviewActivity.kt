package com.example.lq.myapplication.picpreview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_pic_preview.*
import java.util.*
import android.animation.PropertyValuesHolder
import android.view.*
import android.view.animation.LinearInterpolator
import kotlin.collections.ArrayList


class PicPreviewActivity : AppCompatActivity() {
    //测试用
    private var translationX = 0f
    private var translationY = 0f
    private var finishTranslationX = 0f
    private var finishTranslationY = 0f
    private var scaleX = 0f
    private var scaleY = 0f
    private var finishScaleX = 0f
    private var finishScaleY = 0f
    //测试用

    private var picInfos : ArrayList<PicViewInfo>?=null

    private var lastState = -1
    private var lastPos: Int = 0
    private var currentPosition = -1

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_pic_preview)
        picInfos = intent.getParcelableArrayListExtra<PicViewInfo>("pic_info")
        val currentPos = intent.getIntExtra("current_pos", 0)
        Log.e("picinfo", picInfos.toString())
        picInfos?.let {
            val mAdapter = PicRvAdapter(this, it)
            pic_container.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(pic_container)
            pic_container.adapter = mAdapter
            pic_container.layoutManager.scrollToPosition(currentPos)
        }
        root_view.post({
            picInfos?.let {
                val picViewInfo = it[currentPos]
                val height = root_view.height
                val width = root_view.width
                Log.e("drawable","$width $height")
                //要计算
                val locationArr = IntArray(2)
                root_view.getLocationOnScreen(locationArr)
                pic_container.pivotX = width.toFloat() / 2
                pic_container.pivotY = height.toFloat() / 2
                scaleX = picViewInfo.mWidth.toFloat() / width
                scaleY = picViewInfo.mWidth.toFloat() / width
                pic_container.scaleX = picViewInfo.mWidth.toFloat() / width
                pic_container.scaleY = picViewInfo.mWidth.toFloat() / width
                bg_view.alpha = 0.toFloat()
                val bigCenterX = width / 2 + locationArr[0]
                val bigCenterY = height / 2 + locationArr[1]
                val smallCenterX = picViewInfo.mRawX + picViewInfo.mWidth / 2
                val smallCenterY = picViewInfo.mRawY + picViewInfo.mHeight / 2
                translationX = smallCenterX - bigCenterX
                translationY = smallCenterY - bigCenterY
                //动画
                val pvhPicTranslationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, translationX,0f)
                val pvhPicTranslationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, translationY,0f)
                val pvhPicScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
                val pvhPicScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
                val oa = ObjectAnimator.ofPropertyValuesHolder(pic_container, pvhPicTranslationX, pvhPicTranslationY, pvhPicScaleX, pvhPicScaleY)
                oa.duration = 250
                oa.interpolator = LinearInterpolator()
                oa.start()
                val oaBgAlpha = ObjectAnimator.ofFloat(bg_view, View.ALPHA, 0f,1f)
                oaBgAlpha.duration = 180
                oaBgAlpha.interpolator = LinearInterpolator()
                oaBgAlpha.start()
            }
        })
    }

    private class PicRvAdapter(context: Context, data: ArrayList<PicViewInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var mContext: Context = context
        var mData: ArrayList<PicViewInfo> = data
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            (holder as PicHolder).mPicIv.setImageResource(mData.get(position).mUrl)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val View = LayoutInflater.from(mContext).inflate(R.layout.pic_item, parent, false)
            View.viewTreeObserver.addOnGlobalLayoutListener {
                Log.e("adapter","LayoutInflater oncreateViewHolder")
            }
            Log.e("adapter","oncreateViewHolder")
            return PicHolder(View)
        }

        override fun getItemCount(): Int {
            return mData.size
        }
    }

    private class PicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPicIv: ImageView = itemView.findViewById(R.id.pic_iv)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun setFinishAnimValueByPos(pos: Int) {
        picInfos?.let {
            val picViewInfo = it[pos]
            val height = root_view.height
            val width = root_view.width
            //要计算
            val locationArr = IntArray(2)
            root_view.getLocationOnScreen(locationArr)
            pic_container.pivotX = width.toFloat() / 2
            pic_container.pivotY = height.toFloat() / 2
            finishScaleX = picViewInfo.mWidth.toFloat() / width
            finishScaleY = picViewInfo.mWidth.toFloat() / width
            pic_container.scaleX = picViewInfo.mWidth.toFloat() / width
            pic_container.scaleY = picViewInfo.mWidth.toFloat() / width
            bg_view.alpha = 0.toFloat()
            val bigCenterX = width / 2 + locationArr[0]
            val bigCenterY = height / 2 + locationArr[1]
            val smallCenterX = picViewInfo.mRawX + picViewInfo.mWidth / 2
            val smallCenterY = picViewInfo.mRawY + picViewInfo.mHeight / 2
            finishTranslationX = smallCenterX - bigCenterX
            finishTranslationY = smallCenterY - bigCenterY
        }
    }

    fun finishWithAnim() {
        var pos = (pic_container.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        setFinishAnimValueByPos(pos)
        //退出动画
        val pvhPicTranslationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, pic_container.translationX,finishTranslationX)
        val pvhPicTranslationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, pic_container.translationY,finishTranslationY)
        val pvhPicScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, finishScaleX)
        val pvhPicScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, finishScaleY)
        val oa = ObjectAnimator.ofPropertyValuesHolder(pic_container, pvhPicTranslationX, pvhPicTranslationY, pvhPicScaleX, pvhPicScaleY)
        oa.duration = 250
        oa.interpolator = LinearInterpolator()
        oa.start()
        bg_view.alpha = 0f
        oa.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                finish()
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.action == KeyEvent.ACTION_DOWN) {
                finishWithAnim()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
