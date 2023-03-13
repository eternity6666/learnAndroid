package com.yzh.demoapp.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.yzh.demoapp.R
import com.yzh.demoapp.activity.OrientationActivity.Orientation.Companion.isParallel

/**
 * @author eternity6666@qq.com
 * @since 2022/11/24 16:16
 */
class OrientationActivity : ComponentActivity() {

    private val uiMode = MutableLiveData(false)
    private val sensorMode = MutableLiveData<Orientation>()
    private val orientationMode = MutableLiveData<Orientation>()
    private val textValues = MutableLiveData(Triple("", "", ""))

    private val standardUI by lazy { findViewById<ConstraintLayout>(R.id.standard_ui) }
    private val standardFullScreenIcon by lazy { findViewById<View>(R.id.standard_fullscreen_icon) }

    private val fullscreenUI by lazy { findViewById<ConstraintLayout>(R.id.fullscreen_ui) }
    private val fullscreenBackIcon by lazy { findViewById<View>(R.id.fullscreen_back_icon) }
    private val textView by lazy { findViewById<TextView>(R.id.value) }
    private val sensorListener by lazy {
        object : OrientationEventListener(this) {
            private val SENSOR_ANGLE = 10
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }
                if (orientation > 360 - SENSOR_ANGLE || orientation < SENSOR_ANGLE) {
                    updateSensorMode(Orientation.PORTRAIT)
                } else if (orientation > 90 - SENSOR_ANGLE && orientation < 90 + SENSOR_ANGLE) {
                    updateSensorMode(Orientation.REVERSE_LANDSCAPE)
                } else if (orientation > 180 - SENSOR_ANGLE && orientation < 180 + SENSOR_ANGLE) {
                    updateSensorMode(Orientation.REVERSE_PORTRAIT)
                } else if (orientation > 270 - SENSOR_ANGLE && orientation < 270 + SENSOR_ANGLE) {
                    updateSensorMode(Orientation.LANDSCAPE)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)
        initView()
        initUIMode()
        initOrientationMode()
        initSensorMode()
        textValues.observe(this) { textList ->
            textView.text = "${textList.first}\n${textList.second}\n${textList.third}"
        }
        sensorListener.enable()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorListener.disable()
    }

    private fun initView() {
        standardFullScreenIcon.setOnClickListener {
            if (isPhone()) {
                orientationMode.value = Orientation.LANDSCAPE
            }
            uiMode.value = true
        }
        fullscreenBackIcon.setOnClickListener {
            if (isPhone()) {
                orientationMode.value = Orientation.PORTRAIT
            }
            uiMode.value = false
        }
    }

    private fun initUIMode() {
        uiMode.observe(this) { ui ->
            textValues.value =
                textValues.value?.copy(if (ui) "全面屏" else "标准屏") ?: Triple("", "", "")
            if (ui == true) {
                standardUI.isVisible = false
                fullscreenUI.isVisible = true
            } else {
                standardUI.isVisible = true
                fullscreenUI.isVisible = false
            }
        }
        uiMode.value = defaultUIMode()
    }

    private fun initOrientationMode() {
        orientationMode.observe(this) { orientation ->
            textValues.value =
                textValues.value?.copy(second = orientation.name) ?: Triple("", "", "")
            requestedOrientation = if (isPhone()) {
                when (orientation) {
                    Orientation.PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    Orientation.REVERSE_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    Orientation.LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    Orientation.REVERSE_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            } else {
                ActivityInfo.SCREEN_ORIENTATION_USER
            }
        }
        orientationMode.value = if (isPhone()) {
            if (defaultUIMode()) {
                Orientation.LANDSCAPE
            } else {
                Orientation.PORTRAIT
            }
        } else {
            Orientation.match(resources.configuration.orientation)
        }
    }

    private fun initSensorMode() {
        sensorMode.observe(this) { orientation ->
            textValues.value =
                textValues.value?.copy(third = orientation.name) ?: Triple("", "", "")
        }
    }

    private fun defaultUIMode(): Boolean {
        return false
    }

    private fun updateSensorMode(newOrientation: Orientation) {
        if (newOrientation != sensorMode.value) {
            sensorMode.updateValue(newOrientation)
            if (isPhone()) {
                if (newOrientation == Orientation.REVERSE_PORTRAIT) {
                    return
                }
                if (newOrientation.isParallel(Orientation.PORTRAIT)) {
                    uiMode.updateValue(false)
                } else {
                    uiMode.updateValue(true)
                }
            }
            orientationMode.updateValue(newOrientation)
        }
    }


    @SuppressLint("LongLogTag")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("[eternity6666][$TAG]", "newConfig = ${newConfig.orientation}")
//        updateSensorMode(Orientation.match(newConfig.orientation))
    }

    private fun isPhone(): Boolean {
        return !isPad()
    }

    private fun isPad(): Boolean {
        return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    enum class Orientation {
        PORTRAIT,
        LANDSCAPE,
        REVERSE_PORTRAIT,
        REVERSE_LANDSCAPE;

        companion object {
            fun match(value: Int): Orientation {
                return when (value) {
                    Configuration.ORIENTATION_PORTRAIT -> PORTRAIT
                    Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE
                    else -> PORTRAIT
                }
            }

            private fun Orientation.isLandscape(): Boolean {
                return this == LANDSCAPE || this == REVERSE_LANDSCAPE
            }

            fun Orientation.isParallel(orientation: Orientation?): Boolean {
                return this.isLandscape() == orientation?.isLandscape()
            }
        }
    }


    companion object {
        private const val TAG: String = "OrientationActivity"

        private fun <T> MutableLiveData<T>.updateValue(newValue: T) {
            if (isMainThread()) {
                value = newValue
            } else {
                postValue(newValue)
            }
        }

        private fun isMainThread(): Boolean {
            return Thread.currentThread() == Looper.getMainLooper().thread
        }
    }
}