/*
 * CopyRight (C) 2024 Tencent. All rights reserved.
 */
package com.yzh.demoapp.yapp.colorhistogram

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.yzh.demoapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ColorHistogramPage(modifier: Modifier = Modifier) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val resources = LocalContext.current.resources
    LaunchedEffect(key1 = "ImageBitmap") {
        launch(Dispatchers.IO) {
            runCatching {
                ImageBitmap.imageResource(res = resources, id = R.drawable.worker_image_1)
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                imageBitmap = it
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = "打开图片")
        }
        imageBitmap?.let {
            Image(painter = BitmapPainter(it), contentDescription = "图片")
            ColorHistogram(pixelMap = it.toPixelMap())
        }
    }
}

@Composable
private fun ColorHistogram(
    modifier: Modifier = Modifier,
    pixelMap: PixelMap,
) {
    var list by remember {
        mutableStateOf<List<Pair<Color, Int>>>(emptyList())
    }
    LaunchedEffect(key1 = "计算") {
        launch(Dispatchers.IO) {
            val result = mutableMapOf<Color, Int>()
            val width = pixelMap.width
            val height = pixelMap.height
            for (x in 0..<width) {
                for (y in 0..<height) {
                    val color = pixelMap[x, y]
                    result[color] = result.getOrDefault(color, 0) + 1
                }
            }
            list = result.toList().sortedByDescending { it.second }.take(100)
            Log.i("ColorHistogram", "pixelMap count = ${result.size}")
        }
    }
    LazyColumn {
        items(list) { (color, count) ->
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .background(color)
                        .border(width = 1.dp, color = Color.Black)
                        .size(32.dp)
                )
                Text(text = "$color $count")
            }
        }
    }
}
