/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.aacell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.yzh.annotation.YActivity
import com.yzh.demoapp.ui.theme.LearnAndroidTheme

@YActivity(title = "AACell")
class AACellActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[AACellViewModel::class.java]
        setContent {
            LearnAndroidTheme {
                AACellPage(viewModel)
            }
        }
    }
}