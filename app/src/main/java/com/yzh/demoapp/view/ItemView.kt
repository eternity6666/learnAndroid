package com.yzh.demoapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.google.android.material.card.MaterialCardView
import com.yzh.demoapp.R
import com.yzh.demoapp.util.AnimUtils
import com.yzh.demoapp.util.StringUtils

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private lateinit var mTitleView: TextView
    private lateinit var mDescriptionView: TextView
    private lateinit var mShowBtn: FrameLayout

    private var isShow: Boolean = false

    init {
        initViews()
        mShowBtn.setOnClickListener {
            if (isShow) {
                isShow = false
                doHideAnimation()
            } else {
                isShow = true
                doShowAnimation()
            }
        }
    }

    private fun initViews() {
        inflate(context, R.layout.view_item, this)
        if (layoutParams == null) {
            val lp = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(32, 10, 32, 15)
            layoutParams = lp
        }
        mTitleView = findViewById(R.id.item_view_title)
        mDescriptionView = findViewById(R.id.item_view_description)
        mShowBtn = findViewById(R.id.item_view_show_btn)
        radius = 30f
        cardElevation = 20f
    }

    private fun doShowAnimation() {
        mDescriptionView.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mDescriptionView.layoutParams.height = 0
        mDescriptionView.visibility = View.VISIBLE
        val startHeight = 0
        val endHeight = mDescriptionView.measuredHeight
        val descriptionAnimator = AnimUtils.buildHeightAnimator(
            mDescriptionView,
            startHeight,
            endHeight
        )
        descriptionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mDescriptionView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        })
        val showAnimator = AnimUtils.buildRotationAnimator(
            mShowBtn,
            startRotation = 0f,
            endRotation = 180f
        )
        doAnimation(descriptionAnimator, showAnimator)
    }

    private fun doHideAnimation() {
        mDescriptionView.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val descriptionAnimator = AnimUtils.buildHeightAnimator(
            mDescriptionView,
            startHeight = mDescriptionView.measuredHeight,
            endHeight = 0
        )
        descriptionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mDescriptionView.visibility = View.GONE
            }
        })
        val showAnimator = AnimUtils.buildRotationAnimator(
            mShowBtn,
            startRotation = 180f,
            endRotation = 0f
        )
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

    fun setTitle(title: String) {
        mTitleView.text = title
    }

    fun setDescriptionText(description: String) {
        if (StringUtils.isEmpty(description)) {
            mShowBtn.visibility = View.GONE
            return
        }
        mDescriptionView.text = description
        mShowBtn.visibility = View.VISIBLE
    }

    companion object {
        private const val TAG: String = "ItemView"
    }

}