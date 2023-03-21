package com.yzh.demoapp.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.drawable.toBitmap
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
            it.applicationInfo?.loadLabel(packageManager)?.toString().orEmpty().ifEmpty { it.packageName }.orEmpty()
        }
        setContent {
            LearnAndroidTheme {
                AppListPage(list = list)
            }
        }
    }
}

@Composable
fun AppListPage(
    list: List<PackageInfo> = emptyList(),
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) {
            AppListItem(item = it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppListItem(
    item: PackageInfo,
) {
    val packageManager = LocalContext.current.packageManager
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AppListDialog(item = item, onDismissRequest = { showDialog = false })
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = true,
                onLongClick = { showDialog = true },
                onClick = { showDialog = true }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            val bitmap = remember { item.applicationInfo?.loadIcon(packageManager)?.toBitmap()?.asImageBitmap() }
            val appName = item.applicationInfo?.loadLabel(packageManager)?.toString().orEmpty()
            bitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = appName,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(text = appName)
        }
    }
}

@Composable
fun AppListDialog(
    item: PackageInfo,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        AppListDialogContent(item = item)
    }
}

@Composable
private fun AppListDialogContent(
    item: PackageInfo,
) {
    val packageManager = LocalContext.current.packageManager
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val bitmap = remember {
                    item.applicationInfo?.loadIcon(packageManager)?.toBitmap()?.asImageBitmap()
                }
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = item.packageName.orEmpty(),
                        modifier = Modifier.size(30.dp)
                    )
                }
                val appName = item.applicationInfo?.loadLabel(packageManager)?.toString().orEmpty()
                if (appName.isNotEmpty()) {
                    Text(text = appName, style = MaterialTheme.typography.titleMedium)
                }
            }
            Text(text = item.packageName, style = MaterialTheme.typography.titleSmall)
        }
    }
}