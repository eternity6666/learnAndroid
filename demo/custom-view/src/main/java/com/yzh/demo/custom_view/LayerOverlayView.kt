/*
 * CopyRight (C) 2022 Tencent. All rights reserved.
 */
package com.yzh.demo.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.pow

/**
 * @author baronyang@tencent.com
 * @since 2022/12/29 16:16
 */
class LayerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint1 by lazy {
        Paint()
    }
    private val textPaint1 by lazy {
        TextPaint().apply {
            textSize = resources.getDimension(R.dimen.d14)
            color = Color.WHITE
            bgColor = Color.RED
            textAlign = Paint.Align.CENTER
        }
    }
    private val drawable1 by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ColorDrawable(Color.argb(0.8f, 0f, 0f, 0f))
        } else {
            ColorDrawable(Color.rgb(0, 0, 0))
        }
    }
    private val clearMode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private var targetArea: Triple<Float, Float, Float>? = null

    fun updateTargetArea(targetArea: Triple<Float, Float, Float>) {
        Log.i(TAG, targetArea.toString())
        this.targetArea = targetArea
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val width = width
        val height = height
        canvas?.let {
            val layerId = it.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
            it.drawBitmap(drawable1.toBitmap(width, height), 0f, 0f, paint1)
            paint1.xfermode = clearMode
            targetArea?.let { area ->
                it.drawCircle(area.first, area.second, area.third, paint1)
            }
            paint1.xfermode = null
            it.restoreToCount(layerId)
            targetArea?.let { area ->
                it.drawBitmap(
                    resources.getDrawable(R.drawable.guide_found).toBitmap(
                        resources.getDimensionPixelSize(R.dimen.d162),
                        resources.getDimensionPixelSize(R.dimen.d44),
                    ),
                    area.first - resources.getDimension(R.dimen.d81),
                    area.second - area.third - resources.getDimension(R.dimen.d114),
                    paint1
                )
                val textX = area.first
                val textY = area.second - area.third - resources.getDimension(R.dimen.d52)
                it.drawText("“发现”移到这里了", textX, textY, textPaint1)
                it.drawBitmap(
                    resources.getDrawable(R.drawable.guide_arrow_down).toBitmap(
                        resources.getDimensionPixelSize(R.dimen.d04),
                        resources.getDimensionPixelSize(R.dimen.d34)
                    ),
                    area.first,
                    area.second - area.third - resources.getDimension(R.dimen.d38),
                    paint1
                )
            }
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (isTargetAreaTouch(event.x, event.y)) {
                Toast.makeText(context, "你点击了目标区域\nx=${event.x} y=${event.y}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "你点击了目标区域之外\nx=${event.x} y=${event.y}", Toast.LENGTH_SHORT).show()
            }
            performClick()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun isTargetAreaTouch(x: Float, y: Float): Boolean {
        return targetArea?.let {
            (x - it.first).pow(2) + (y - it.second).pow(2) <= (it.third).pow(2)
        } ?: false
    }

    companion object {
        private const val TAG = "baronyang"
    }
}