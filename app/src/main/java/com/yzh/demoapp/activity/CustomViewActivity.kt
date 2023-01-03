package com.yzh.demoapp.activity

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yzh.demo.custom_view.CustomView
import com.yzh.demo.custom_view.LayerOverlayView
import com.yzh.demoapp.R
import kotlin.math.max

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)
        Handler(Looper.getMainLooper()).post {
            Log.i("baronyang", "onCreate")
            updateTargetArea()
        }
    }

    private fun updateTargetArea() {
        val customView = findViewById<CustomView>(R.id.custom_view)
        val layerOverlayView = findViewById<LayerOverlayView>(R.id.layer_overlay_view)
        layerOverlayView.updateTargetArea(
            Triple(
                customView.x + customView.width / 2,
                customView.y + customView.height / 2,
                max(customView.width, customView.height).toFloat() / 2
            )
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Handler(Looper.getMainLooper()).postDelayed({
            Log.i("baronyang", "onConfigurationChanged")
            updateTargetArea()
        }, 100)
    }
}