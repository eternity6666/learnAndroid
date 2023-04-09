package com.yzh.learnAndroid.recycler_view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialCardView(context, attrs, defStyleAttr) {


    private var mTitleView: TextView
    private var mDescriptionView: TextView

    init {
        inflate(context, R.layout.recycler_view_item, this)
        if (layoutParams == null) {
            val lp = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(32, 10, 32, 10)
            layoutParams = lp
        }
        mTitleView = findViewById(R.id.title)
        mDescriptionView = findViewById(R.id.sub_title)
        radius = 30f
        cardElevation = 30f
    }

    fun setTitle(title: String) {
        mTitleView.text = title
    }

    fun setDescriptionText(description: String) {
        mDescriptionView.text = description
    }

}