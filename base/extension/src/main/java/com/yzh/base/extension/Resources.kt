package com.yzh.base.extension

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

fun Resources.color(colorResId: Int): Int {
    return ResourcesCompat.getColor(this, colorResId, null)
}

fun Resources.drawable(drawableResId: Int): Drawable? {
    return ResourcesCompat.getDrawable(this, drawableResId, null)
}