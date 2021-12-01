package com.yzh.demoapp.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.yzh.demoapp.R
import com.yzh.demoapp.util.AnimUtils

class ValueAnimatorActivity : AppCompatActivity() {

    private val TAG = "ValueAnimatorActivity"

    private var DEFAULT_HEIGHT_100: Int = 0
    private var DEFAULT_HEIGHT_200: Int = 0
    private var DEFAULT_HEIGHT_300: Int = 0

    private lateinit var mTitleView: TextView
    private lateinit var mButton1: TextView
    private lateinit var mButton2: TextView
    private lateinit var mContainerA: LinearLayout
    private lateinit var mContainerB: LinearLayout

    private var isLongTextStatus = false
    private var status = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_animator)
        DEFAULT_HEIGHT_100 = resources.getDimensionPixelSize(R.dimen.d100)
        DEFAULT_HEIGHT_200 = resources.getDimensionPixelSize(R.dimen.d200)
        DEFAULT_HEIGHT_300 = resources.getDimensionPixelSize(R.dimen.d300)
        mTitleView = findViewById(R.id.title)
        mButton1 = findViewById(R.id.button_1)
        mButton2 = findViewById(R.id.button_2)
        mContainerA = findViewById(R.id.container_a)
        mContainerB = findViewById(R.id.container_b)
        initClick()
    }

    private fun initClick() {
        mButton1.setOnClickListener {
            if (!isLongTextStatus) {
                isLongTextStatus = true
                init(isLongTextStatus)
            }
            if (status) {
                doShowAnimation()
            } else {
                doHideAnimation()
            }
            status = !status
        }
        mButton2.setOnClickListener {
            if (isLongTextStatus) {
                isLongTextStatus = false
                init(isLongTextStatus)
            }
            if (status) {
                doShowAnimation()
            } else {
                doHideAnimation()
            }
            status = !status
        }
    }

    private fun doShowAnimation() {
        val containerBTopMarginAnimator =
            ValueAnimator.ofInt(mContainerB.marginTop, DEFAULT_HEIGHT_200).apply {
                addUpdateListener {
                    if (mContainerB.layoutParams is ViewGroup.MarginLayoutParams) {
                        val params = mContainerB.layoutParams as ViewGroup.MarginLayoutParams
                        params.topMargin = it.animatedValue as Int
                        if (params.topMargin + mContainerB.height > DEFAULT_HEIGHT_200) {
                            mContainerA.visibility = View.VISIBLE
                        }
                    }
                }
            }
        val containerAHeightAnimator = AnimUtils.buildHeightAnimator(
            mContainerA,
            startHeight = mContainerA.layoutParams.height,
            endHeight = DEFAULT_HEIGHT_200
        )
        val containerAAlphaAnimator = AnimUtils.buildAlphaAnimator(
            mContainerA,
            startAlpha = mContainerA.alpha,
            endAlpha = 1f
        )
        val button1RotateAnimation = AnimUtils.buildRotationAnimator(
            mButton1,
            startRotation = mButton1.rotation,
            endRotation = 180.0f
        )
        val button2RotateAnimation = AnimUtils.buildRotationAnimator(
            mButton2,
            startRotation = mButton2.rotation,
            endRotation = 180.0f
        )
        containerAHeightAnimator.interpolator = DecelerateInterpolator()
        containerBTopMarginAnimator.interpolator = DecelerateInterpolator()
        containerAAlphaAnimator.interpolator = AccelerateInterpolator()
        val animatorList = ArrayList<Animator>()
        animatorList.add(containerAAlphaAnimator)
        animatorList.add(containerAHeightAnimator)
        animatorList.add(containerBTopMarginAnimator)
        animatorList.add(button1RotateAnimation)
        animatorList.add(button2RotateAnimation)
        AnimUtils.doAnimation(animatorList)
    }

    private fun doHideAnimation() {
        val containerBTopMarginAnimator = AnimUtils.buildMarginTopAnimator(
            mContainerB,
            startMargin = mContainerB.marginTop,
            endMargin = 0
        )
        val containerAHeightAnimator = AnimUtils.buildHeightAnimator(
            mContainerA,
            startHeight = mContainerA.layoutParams.height,
            endHeight = 0
        )
        containerAHeightAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (mContainerA.alpha == 0f) {
                    mContainerA.visibility = View.GONE
                }
            }
        })
        val containerAAlphaAnimator = AnimUtils.buildAlphaAnimator(
            mContainerA,
            startAlpha = mContainerA.alpha,
            endAlpha = 0f
        )
        val button1RotateAnimation = AnimUtils.buildRotationAnimator(
            mButton1,
            startRotation = mButton1.rotation,
            endRotation = 0f
        )
        val button2RotateAnimation = AnimUtils.buildRotationAnimator(
            mButton2,
            startRotation = mButton2.rotation,
            endRotation = 0f
        )
        containerAHeightAnimator.interpolator = DecelerateInterpolator()
        containerBTopMarginAnimator.interpolator = DecelerateInterpolator()
        containerAAlphaAnimator.interpolator = AccelerateInterpolator()
        val animatorList = ArrayList<Animator>()
        animatorList.add(containerAAlphaAnimator)
        animatorList.add(containerAHeightAnimator)
        animatorList.add(containerBTopMarginAnimator)
        animatorList.add(button1RotateAnimation)
        animatorList.add(button2RotateAnimation)
        AnimUtils.doAnimation(animatorList)
    }

    private fun init(isLongText: Boolean) {
        mContainerA.visibility = View.GONE
        if (isLongText) {
            mContainerB.layoutParams.height = DEFAULT_HEIGHT_300
        } else {
            mContainerB.layoutParams.height = DEFAULT_HEIGHT_100
        }

    }
}