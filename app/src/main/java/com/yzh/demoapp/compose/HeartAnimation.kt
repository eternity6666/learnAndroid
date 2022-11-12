/*
 * CopyRight (C) 2022 Tencent. All rights reserved.
 */
package com.yzh.demoapp.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

@Preview
@Composable
fun HeartAnimation_Previews() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeartAnimation(
            Modifier
                .size(300.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun HeartAnimation(
    modifier: Modifier = Modifier
) {
    val pointList = remember {
        mutableStateListOf<Point>()
    }
    LaunchedEffect("123") {
        while (true) {
            var t = 0.0f
            while (t <= 2 * Math.PI) {
                pointList.add(calculatePoint(t))
                t += 0.1f
                delay(50)
            }
            while (pointList.isNotEmpty()) {
                pointList.removeFirst()
                delay(50)
            }
        }
    }
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val xBias = size.width / 2
            val yBias = size.height * 2 / 5
            drawIntoCanvas { canvas ->
                val paint = Paint()
                pointList.forEach { point ->
                    paint.color = point.color
                    paint.alpha = 0.5f
                    canvas.drawCircle(
                        Offset(
                            point.x * size.minDimension * 0.03f + xBias,
                            -point.y * size.minDimension * 0.03f + yBias,
                        ),
                        size.minDimension * 0.013f, paint
                    )
                }
            }
        }
    }
}

fun calculatePoint(t: Float): Point {
    return Point(
        x = 16 * sin(t).pow(3),
        y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t),
        color = randomColor()
    )
}
