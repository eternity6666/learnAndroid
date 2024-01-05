/*
 * CopyRight (C) 2024 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.yzh.annotation.YActivity
import com.yzh.demoapp.compose.TAG

/**
 * @author baronyang@tencent.com
 * @since 2024/1/4 16:16
 */
@YActivity(title = "测试 onConfigurationChange")
class ConfigurationChangeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Button(onClick = { doOrientationChange(false) }) {
                    Text(text = "转竖屏")
                }
                Button(onClick = { doOrientationChange(true) }) {
                    Text(text = "转横屏")
                }
                Button(onClick = { doJump() }) {
                    Text(text = "跳转")
                }
                Button(onClick = { doJumpOut() }) {
                    Text(text = "跳外部应用")
                }
                Button(onClick = { jumpToQQ() }) {
                    Text(text = "跳QQ")
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    private fun doOrientationChange(isLandscape: Boolean) {
        requestedOrientation = if (isLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun doJump() {
        val intent = Intent()
        intent.setClass(this, ConfigurationChangeSecondActivity::class.java)
        startActivity(intent)
    }


    private fun doJumpOut() {
        runCatching {
            val intent = Intent()
//            intent.setClassName(this, "com.yzh.outpullup.MainActivity")
            intent.setAction("com.yzh.outpullup.MainActivity")
            startActivity(intent)
        }.onFailure {
            Log.i(TAG, "doJumpOut", it)
        }
    }

    private fun jumpToQQ() {
        runCatching {
            val pkgName = "com.tencent.mobileqq"
            val intent = packageManager.getLaunchIntentForPackage(pkgName)
//            intent?.let {
//                it.component?.let { componentName ->
//                    it.component = ComponentName(componentName.packageName, ".activity.LoginActivity")
//                }
//            }
            Log.i(TAG, "intent: $intent")
            startActivity(intent)
        }.onFailure {
            Log.i(TAG, "jumpToQQ", it)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(TAG, "onConfigurationChanged $newConfig")
    }
}