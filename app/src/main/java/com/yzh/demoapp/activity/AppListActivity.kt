package com.yzh.demoapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yzh.annotation.YActivity
import com.yzh.demoapp.ui.theme.LearnAndroidTheme
import com.yzh.demoapp.yapp.applist.AppListPage


@YActivity(title = "获取手机上安装的应用程序列表", description = "使用了 PackageManager, Compose")
class AppListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnAndroidTheme {
                AppListPage()
            }
        }
    }
}