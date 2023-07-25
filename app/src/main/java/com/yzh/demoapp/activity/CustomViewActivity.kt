package com.yzh.demoapp.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.activity.ComponentActivity
import com.yzh.annotation.YActivity
import com.yzh.demoapp.custom_view.CustomView
import com.yzh.demoapp.custom_view.LayerOverlayView
import com.yzh.demoapp.R
import kotlin.math.pow
import kotlin.math.sqrt

@YActivity(description = "自定义 View")
class CustomViewActivity : ComponentActivity() {
    private val customView by lazy {
        findViewById<CustomView>(R.id.custom_view)
    }
    private val layerOverlayView by lazy {
        LayerOverlayView(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        customView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTargetArea()
        }
        Handler(Looper.getMainLooper()).post {
            Log.i("eternity6666", "onCreate")
            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(
                layerOverlayView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            )
            updateTargetArea()
        }
    }

    private fun updateTargetArea() {
        layerOverlayView.updateTargetArea(
            Triple(
                customView.x + customView.width / 2,
                customView.y + customView.height / 2,
                sqrt(customView.width.toFloat().pow(2) + customView.height.toFloat().pow(2)) / 2,
            )
        )
    }
}
