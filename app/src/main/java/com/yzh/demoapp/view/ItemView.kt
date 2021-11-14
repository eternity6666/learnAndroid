package com.yzh.demoapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.yzh.demoapp.R
import com.yzh.demoapp.util.Utils

class ItemView : ConstraintLayout {
    private val TAG: String = "ItemView"

    private lateinit var mTitleView: TextView
    private lateinit var mDescriptionView: TextView
    private lateinit var mShowBtn: FrameLayout

    private var isShow: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0,
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initViews()
        mShowBtn.setOnClickListener {
            if(isShow) {
                isShow = false
                doHideAnimation();
            } else {
                isShow = true
                doShowAnimation();
            }
        }
    }

    private fun initViews() {
        inflate(context, R.layout.view_item, this)
        mTitleView = findViewById(R.id.item_view_title)
        mDescriptionView = findViewById(R.id.item_view_description)
        mShowBtn = findViewById(R.id.item_view_show_btn)
    }

    private fun doShowAnimation() {
        mDescriptionView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mDescriptionView.layoutParams.height = 0
        mDescriptionView.visibility = View.VISIBLE
        val startHeight = 0
        val endHeight = mDescriptionView.measuredHeight
        val descriptionAnimator = buildDescriptionAnimator(
            startHeight, endHeight
        )
        descriptionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mDescriptionView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        })
        val showAnimator = buildShowAnimator(startRotation = 0f, endRotation = 180f)
        doAnimation(descriptionAnimator, showAnimator)
    }

    private fun doHideAnimation() {
        mDescriptionView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val descriptionAnimator = buildDescriptionAnimator(
            startHeight = mDescriptionView.measuredHeight,
            endHeight = 0
        )
        descriptionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mDescriptionView.visibility = View.GONE
            }
        })
        val showAnimator = buildShowAnimator(startRotation = 180f, endRotation = 0f)
        doAnimation(descriptionAnimator, showAnimator)
    }

    private fun doAnimation(descriptionAnimator: Animator, showHideBtnAnimator: Animator) {
        AnimatorSet().apply {
            interpolator = FastOutLinearInInterpolator()
            duration = 500
            play(descriptionAnimator).with(showHideBtnAnimator)
            start()
        }
    }

    private fun buildDescriptionAnimator(startHeight: Int, endHeight: Int): Animator {
        Log.d(
            TAG,
            " \nshow startHeight = " + startHeight
                    + "\nshow endHeight = " + endHeight
        )
        return ValueAnimator.ofInt(startHeight, endHeight).apply {
            addUpdateListener {
                Log.d(
                    TAG,
                    " \nshow mDescriptionView.layoutParams.height" + mDescriptionView.layoutParams.height
                            + "\nshow it.animatedValue" + it.animatedValue
                )
                mDescriptionView.layoutParams.height = it.animatedValue as Int
                mDescriptionView.requestLayout()
            }
        }
    }

    private fun buildShowAnimator(startRotation: Float, endRotation: Float): Animator {
        return ValueAnimator.ofFloat(startRotation, endRotation).apply {
            addUpdateListener {
                mShowBtn.rotation = it.animatedValue as Float
            }
        }
    }

    fun setTitle(title: String) {
        mTitleView.text = title
    }

    fun setDescriptionText(description: String) {
        if (Utils.isEmpty(description)) {
            mShowBtn.visibility = View.GONE
            return
        }
        mDescriptionView.text = description
        mShowBtn.visibility = View.VISIBLE
    }

}