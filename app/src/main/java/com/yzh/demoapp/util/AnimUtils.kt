package com.yzh.demoapp.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop

object AnimUtils {

    fun buildHeightAnimator(targetView: View, startHeight: Int, endHeight: Int): Animator {
        return ValueAnimator.ofInt(startHeight, endHeight).apply {
            addUpdateListener {
                targetView.layoutParams.height = it.animatedValue as Int
                targetView.requestLayout()
            }
        }
    }

    fun buildAlphaAnimator(targetView: View, startAlpha: Float, endAlpha: Float): Animator {
        return ValueAnimator.ofFloat(startAlpha, endAlpha).apply {
            addUpdateListener {
                targetView.alpha = it.animatedValue as Float
            }
        }
    }

    fun buildRotationAnimator(targetView: View, startRotation: Float, endRotation: Float): Animator {
        return ValueAnimator.ofFloat(startRotation, endRotation).apply {
            addUpdateListener {
                targetView.rotation = it.animatedValue as Float
            }
        }
    }

    fun buildMarginTopAnimator(targetView: View, startMargin: Int, endMargin: Int): Animator {
        return ValueAnimator.ofInt(startMargin, endMargin).apply {
            addUpdateListener {
                var params = targetView.layoutParams
                if (targetView.layoutParams is ViewGroup.MarginLayoutParams) {
                    params = targetView.layoutParams as ViewGroup.MarginLayoutParams
                    params.topMargin = it.animatedValue as Int
                }
            }
        }
    }

    fun doAnimation(animatorList: ArrayList<Animator>) {
        if (animatorList.size <= 0) {
            return
        }
        AnimatorSet().apply {
            duration = 1000
            val x = play(animatorList[0])
            for (i in 1 until animatorList.size) {
                x.with(animatorList[i])
            }
            start()
        }
    }

}