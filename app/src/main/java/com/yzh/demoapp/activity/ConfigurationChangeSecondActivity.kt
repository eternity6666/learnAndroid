/*
 * CopyRight (C) 2024 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text

/**
 * @author baronyang@tencent.com
 * @since 2024/1/4 16:16
 */
class ConfigurationChangeSecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Button(onClick = { back() }) {
                    Text(text = "返回")
                }
            }
        }
    }

    private fun back() {
        onBackPressedDispatcher.onBackPressed()
    }

    companion object {
        private const val TAG: String = "ConfigurationChangeSecondActivity"
    }
}