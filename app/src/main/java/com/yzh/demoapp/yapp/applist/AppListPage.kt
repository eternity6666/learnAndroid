package com.yzh.demoapp.yapp.applist

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppListPage() {
    val packageList = remember {
        mutableStateOf<List<PackageInfo>>(emptyList())
    }
    val scope = rememberCoroutineScope()
    val packageManager: PackageManager? = LocalContext.current.packageManager
    AppListPageContent(list = packageList) {
        scope.launch(Dispatchers.IO) {
            val pack = packageList.value
            val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager?.getInstalledPackages(PackageManager.PackageInfoFlags.of(0L))
            } else {
                packageManager?.getInstalledPackages(0)
            }.orEmpty().filterNotNull()
            packageList.value = list.ifEmpty { pack }
        }
    }
}

@Composable
fun AppListPageContent(
    list: State<List<PackageInfo>>,
    fetchList: () -> Unit,
) {
    val packageList = list.value
    if (packageList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Button(onClick = fetchList) {
                    Text(text = "获取应用列表")
                }
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(packageList) {
                AppListItem(item = it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppListItem(
    item: PackageInfo,
) {
    val packageManager = LocalContext.current.packageManager
    val bitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(key1 = "bitmap${item.hashCode()}") {
        launch(Dispatchers.IO) {
            bitmap.value = item.applicationInfo?.loadIcon(packageManager)?.toBitmap()?.asImageBitmap()
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AppListDialog(item = item, bitmap = bitmap, onDismissRequest = { showDialog = false })
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
            val appName = item.applicationInfo?.loadLabel(packageManager)?.toString().orEmpty()
            bitmap.value?.let {
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
private fun AppListDialog(
    item: PackageInfo,
    bitmap: State<ImageBitmap?>,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        AppListDialogContent(item = item, bitmap = bitmap)
    }
}

@Composable
private fun AppListDialogContent(
    item: PackageInfo,
    bitmap: State<ImageBitmap?>,
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
                bitmap.value?.let {
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
