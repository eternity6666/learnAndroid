package com.yzh.demoapp.compose

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

const val TAG = "eternity6666"

data class Point(
    val x: Float = 0f,
    val y: Float = 0f,
    val radius: Float = 0f,
    val color: Color = randomColor()
)

fun randomColor(): Color {
    return Color(randomInt(), randomInt(), randomInt(), randomInt())
}

fun randomInt(): Int {
    return (10..255).random()
}

fun randomPosition(from: Int, to: Int): Int {
    check(from <= to)
    return (from..to).random()
}

fun randomFloat(): Float {
    return (10..80).random() * 0.5.toFloat()
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun CustomComposeView() {
    val countSize = 12
    val radius = remember { mutableStateOf(randomFloat()) }
    val currentIndex = remember { mutableStateOf(0) }
    val animateRadius by animateFloatAsState(
        targetValue = radius.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse,
        )
    )
    var sumRed = 0
    var sumGreen = 0
    var sumBlue = 0
    var sumAlpha = 0
    val radiusColor = remember {
        List(countSize) {
            val red = randomInt()
            val green = randomInt()
            val blue = randomInt()
            val alpha = randomInt()
            sumRed += red
            sumGreen += green
            sumBlue += blue
            sumAlpha += alpha
            randomFloat() to Color(red, green, blue, alpha)
        }
    }
    val averageColor = remember {
        Color(
            red = sumRed / countSize,
            green = sumGreen / countSize,
            blue = sumBlue / countSize,
            alpha = sumAlpha / countSize
        )
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LaunchedEffect(key1 = this, block = {
            while (true) {
                delay(1000)
                radius.value = randomFloat()
                currentIndex.value = (currentIndex.value + 1) % countSize
            }
        })
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val minRadius = size.minDimension / 4
            val pointList = radiusColor.mapIndexed { index, (radius, color) ->
                val point = Point(
                    x = centerX + minRadius * cos(2 * Math.PI * index / countSize).toFloat(),
                    y = centerY + minRadius * sin(2 * Math.PI * index / countSize).toFloat(),
                    radius = radius + animateRadius,
                    color = color
                )
                Log.i(TAG, "CustomComposeView: $point")
                point
            }
            drawIntoCanvas { canvas ->
                val paint2 = Paint()
                paint2.color = averageColor
                paint2.style = PaintingStyle.Fill
                paint2.strokeWidth = 3.dp.toPx()
                paint2.alpha = 1f
                canvas.drawCircle(Offset(centerX, centerY), animateRadius, paint2)
                val paint = Paint()
                pointList.forEach { point ->
                    if (point.x != 0f && point.y != 0f) {
                        paint.color = point.color
                        canvas.drawCircle(
                            Offset(point.x, point.y),
                            point.radius, paint
                        )
                    }
                }
                canvas.drawLine(
                    Offset(centerX, centerY),
                    Offset(pointList[currentIndex.value].x, pointList[currentIndex.value].y),
                    paint2
                )
            }
        }
    }
}