package com.yzh.demoapp.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import java.lang.Float.min

class CustomView : View {

    private lateinit var mPaint: Paint
    private lateinit var mCustomState: CustomState

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        updateStateFromTypedArray(a)
        a.recycle()
    }

    private fun init() {
        mCustomState = CustomState()
        mPaint = Paint()
        mPaint.strokeWidth = 20f
        mPaint.color = Color.BLUE
    }


    private fun updateStateFromTypedArray(ta: TypedArray) {
        val state = mCustomState
        state.mText = ta.getString(R.styleable.CustomView_text) ?: ""
        state.mShape = ta.getInt(R.styleable.CustomView_shape, state.mShape)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val width = width
        val height = height
        canvas?.let {
            drawOutLine(canvas, width.toFloat(), height.toFloat())
            drawCircle(canvas, width.toFloat(), height.toFloat())
        }
    }

    private fun drawCircle(canvas: Canvas, width: Float, height: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mPaint.color = Color.CYAN
            mPaint.isAntiAlias = true
            mPaint.alpha = 100
            mPaint.style = Paint.Style.STROKE
            canvas.drawCircle(width / 2, height / 2, min(width, height) / 2, mPaint)
        }
    }

    private fun drawOutLine(canvas: Canvas, width: Float, height: Float) {
        mPaint.color = Color.BLUE
        canvas.drawLine(0f, height / 2, width, height / 2, mPaint)
        mPaint.color = Color.RED
        canvas.drawLine(0f, 0f, width, 0f, mPaint)
        mPaint.color = Color.GRAY
        canvas.drawLine(width, 0f, width, height, mPaint)
        mPaint.color = Color.GREEN
        canvas.drawLine(width, height, 0f, height, mPaint)
        mPaint.color = Color.YELLOW
        canvas.drawLine(0f, height, 0f, mPaint.strokeWidth / 2, mPaint)
    }

    class CustomState {
        var mShape: Int = RECTANGLE
        var mText: String = ""
    }

    companion object {
        const val RECTANGLE: Int = 0
        const val CIRCLE: Int = 1
        const val FIVE_STAR: Int = 2
    }

}