package com.yzh.demoapp.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.yzh.demoapp.ui.theme.LearnAndroidTheme


class AppListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0L))
        } else {
            packageManager.getInstalledPackages(0)
        }
        list.sortBy {
            it.applicationInfo.loadLabel(packageManager).toString()
        }
        setContent {
            LearnAndroidTheme {
                LazyColumn {
                    items(list) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row {
                                Text(text = it?.packageName.orEmpty())
                                val bitmap = it?.applicationInfo?.loadIcon(packageManager)

                            }
                        }
                    }
                }
            }
        }
    }
}