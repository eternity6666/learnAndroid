package com.yzh.demoapp.yapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yzh.annotation.YActivity
import com.yzh.demoapp.yapp.main.YAppMainPage

@YActivity()
class YAppActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YAppMainPage()
        }
    }
}