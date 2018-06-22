package com.example.lq.myapplication.anims

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.lq.myapplication.R
import android.graphics.Canvas.ALL_SAVE_FLAG



/**
 * Created by wx on 2018/6/21.
 */
class HeartImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    var paint = Paint()
    var rect = Rect()
    val xFerMode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    init {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.isFilterBitmap = true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val sc = canvas.saveLayer(0f, 0f, totalW.toFloat(), totalH.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
            super.onDraw(canvas)
            val heart = BitmapFactory.decodeResource(resources, R.drawable.heart)
            paint.xfermode = xFerMode
            canvas.drawBitmap(heart,rect,rect,paint)
            paint.xfermode = null
            canvas.restoreToCount(sc)
            canvas.drawBitmap(BitmapFactory.decodeResource(resources,R.drawable.heartbound),rect,rect,paint)
        }

    }

    private var totalW: Int = 0

    private var totalH: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        totalW = w
        totalH = h
        rect.set(0,0,totalW,totalH)
    }
}