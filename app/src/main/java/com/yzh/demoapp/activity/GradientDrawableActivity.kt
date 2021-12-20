package com.yzh.demoapp.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yzh.demoapp.R

class GradientDrawableActivity : AppCompatActivity() {

    private var index = 0
    private lateinit var mBgView: View
    private lateinit var mButton: Button
    private lateinit var mText: TextView
    private val colorList = listOf(
        ColorData("红色", Color.RED),
        ColorData("绿色", Color.GREEN),
        ColorData("蓝色", Color.BLUE),
        ColorData("黄色", Color.YELLOW),
        ColorData("企业认证", 0xff0098ffu.toInt()),
        ColorData("机构认证", 0xff38c000u.toInt()),
        ColorData("个人认证", 0xffff6022u.toInt())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_drawable)
        mBgView = findViewById(R.id.bg_view)
        mButton = findViewById(R.id.button)
        mText = findViewById(R.id.text)
        changeColor()
        mButton.setOnClickListener {
            changeColor()
        }
    }

    private fun changeColor() {
        val shape = mBgView.background as GradientDrawable
        shape.setColor(colorList[index].color)
        mText.setTextColor(colorList[index].color)
        mText.text = colorList[index].colorName
        index = (index + 1) % colorList.size
        mButton.text = colorList[index].colorName
    }

    data class ColorData(val colorName: String, val color: Int)
}