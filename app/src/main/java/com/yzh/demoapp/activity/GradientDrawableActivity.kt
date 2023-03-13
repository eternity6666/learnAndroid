package com.yzh.demoapp.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.yzh.demoapp.R

class GradientDrawableActivity : ComponentActivity() {

    private var index = 0
    private lateinit var mBgView: View
    private lateinit var mButtonView: Button
    private lateinit var mTextView: TextView
    private val colorList = listOf(
        ColorData(
            "超长的内容超长的内容超长的内容超长的内容超长的内容超长的内容超长的内容超长的内容超长的内容",
            0xffff6022u.toInt(),
            0x1eff6022u.toInt()
        ),
        ColorData("红色", Color.RED, 0x1eff0000u.toInt()),
        ColorData("绿色", Color.GREEN),
        ColorData("蓝色", Color.BLUE),
        ColorData("黄色", Color.YELLOW),
        ColorData("企业认证", 0xff0098ffu.toInt(), 0x1e0098ffu.toInt()),
        ColorData("机构认证", 0xff38c000u.toInt(), 0x1e38c000u.toInt()),
        ColorData("个人认证", 0xffff6022u.toInt(), 0x1eff6022u.toInt())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_drawable)
        mBgView = findViewById(R.id.bg_view)
        mButtonView = findViewById(R.id.button)
        mTextView = findViewById(R.id.text)
        changeColor()
        mButtonView.setOnClickListener {
            changeColor()
        }
    }

    private fun changeColor() {
        if (mBgView.background is GradientDrawable) {
            val shape = mBgView.background as GradientDrawable
            shape.setColor(colorList[index].backgroundColor)
        }
        mTextView.setTextColor(colorList[index].color)
        mTextView.text = colorList[index].colorName
        index = (index + 1) % colorList.size
        mButtonView.text = colorList[index].colorName
    }

    data class ColorData @JvmOverloads constructor(
        val colorName: String,
        val color: Int,
        val backgroundColor: Int = 0
    )

}
