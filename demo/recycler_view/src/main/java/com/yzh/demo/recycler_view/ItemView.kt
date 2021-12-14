package com.yzh.demo.recycler_view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {


    private var mTitleView: TextView
    private var mDescriptionView: TextView

    init {
        inflate(context, R.layout.recycler_view_item, this)
        mTitleView = findViewById(R.id.title)
        mDescriptionView = findViewById(R.id.sub_title)
    }

    fun setTitle(title: String) {
        mTitleView.text = title
    }

    fun setDescriptionText(description: String) {
        mDescriptionView.text = description
    }

}