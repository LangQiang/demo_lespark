package com.example.lq.myapplication.picpreview

import android.animation.TypeEvaluator
import android.graphics.PointF

class FlingTypeEvaluator(flingDuration: Long, vX: Float, vY: Float) : TypeEvaluator<PointF> {
    private var flingDuration : Long = flingDuration
    private var vX : Float = vX
    private var vY : Float = vY
    private var preX : Float = 0f
    private var preY : Float = 0f
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val x = (vX * fraction + vX) * fraction * flingDuration * 0.5f
        val y = (vY * fraction + vY) * fraction * flingDuration * 0.5f
        val dx = x - preX
        val dy = y - preY
        preX = x
        preY = y
        return PointF(dx,dy)
    }

}