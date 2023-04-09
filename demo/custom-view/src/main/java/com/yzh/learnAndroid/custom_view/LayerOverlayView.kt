package com.yzh.learnAndroid.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.yzh.base.extension.color
import com.yzh.base.extension.drawable
import kotlin.math.pow

/**
 * @author eternity6666@qq.com
 * @since 2022/12/29 16:16
 */
class LayerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint1 by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }
    private val textPaint1 by lazy {
        TextPaint().apply {
            textSize = resources.getDimension(R.dimen.d14)
            color = Color.WHITE
            bgColor = Color.RED
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            } else {
                Typeface.create(Typeface.SANS_SERIF, 500, false)
            }
        }
    }
    private val clearMode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private var targetArea: Triple<Float, Float, Float>? = null
    private val text: String = getNeedShownText()
    private var targetAreaClickCallback: () -> Unit = {}
    private var notTargetAreaClickCallback: () -> Unit = {}

    fun updateTargetArea(targetArea: Triple<Float, Float, Float>) {
        Log.i(TAG, targetArea.toString())
        this.targetArea = targetArea
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            targetArea?.let { (circleCenterX, circleCenterY, circleRadius) ->
                drawCircleAndMaskBackground(it, circleCenterX, circleCenterY, circleRadius)
                drawFoundPageImage(it, circleCenterX, circleCenterY, circleRadius)
                drawText(it, circleCenterX, circleCenterY, circleRadius)
                drawArrowImage(it, circleCenterX, circleCenterY, circleRadius)
            }
        }
    }

    private fun drawCircleAndMaskBackground(
        canvas: Canvas,
        circleCenterX: Float,
        circleCenterY: Float,
        circleRadius: Float,
    ) {
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        paint1.xfermode = clearMode
        canvas.drawColor(resources.color(R.color.black_80))
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint1)
        paint1.xfermode = null
        canvas.restoreToCount(layerId)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun drawFoundPageImage(
        canvas: Canvas,
        circleCenterX: Float,
        circleCenterY: Float,
        circleRadius: Float,
    ) {
        val foundPageImageWidth = resources.getDimensionPixelSize(R.dimen.d162)
        val foundPageImageHeight = resources.getDimensionPixelSize(R.dimen.d44)
        val paddingBetweenCircleAndFoundPageImage = resources.getDimension(R.dimen.d114)
        val foundPageImageX = circleCenterX - foundPageImageWidth / 2
        val foundPageImageY = circleCenterY - circleRadius - paddingBetweenCircleAndFoundPageImage
        val bitmap = resources.drawable(R.drawable.guide_found)?.toBitmap(
            foundPageImageWidth,
            foundPageImageHeight,
        ) ?: return
        canvas.drawBitmap(bitmap, foundPageImageX, foundPageImageY, paint1)
    }

    private fun drawText(
        canvas: Canvas,
        circleCenterX: Float,
        circleCenterY: Float,
        circleRadius: Float,
    ) {
        val paddingBetweenCircleAndText = resources.getDimension(R.dimen.d50)
        val textY = circleCenterY - circleRadius - paddingBetweenCircleAndText
        canvas.drawText(text, circleCenterX, textY, textPaint1)
    }

    private fun drawArrowImage(
        canvas: Canvas,
        circleCenterX: Float,
        circleCenterY: Float,
        circleRadius: Float,
    ) {
        val arrowWidth = resources.getDimensionPixelSize(R.dimen.d06)
        val arrowHeight = resources.getDimensionPixelSize(R.dimen.d36)
        val paddingBetweenArrowAndCircle = resources.getDimensionPixelSize(R.dimen.d05)
        val arrowX = circleCenterX - arrowWidth / 2
        val arrowY = circleCenterY - circleRadius - paddingBetweenArrowAndCircle - arrowHeight
        val bitmap = ResourcesCompat.getDrawable(resources, R.drawable.guide_arrow_down, null)?.toBitmap(
            arrowWidth, arrowHeight
        ) ?: return
        canvas.drawBitmap(bitmap, arrowX, arrowY, paint1)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (isTargetAreaTouch(event.x, event.y)) {
                targetAreaClickCallback.invoke()
            } else {
                notTargetAreaClickCallback.invoke()
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
        private fun getNeedShownText(): String {
            return "“发现”移到这里了"
        }

        private const val TAG = "eternity6666"
    }
}