package com.yzh.demoapp.compose

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@Composable
fun CubicBezierPage() {
    val point1 = remember {
        mutableStateOf(Offset(1f, 0f))
    }
    val point2 = remember {
        mutableStateOf(Offset(0f, 1f))
    }
    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xffa3f9ff),
                        Color(0xfffcffcc)
                    )
                )
            )
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "cubic-bezier(${point1.value.x}, ${point1.value.y}, ${point2.value.x}, ${point2.value.y})",
        )
        CubicBezierBoard(
            modifier = Modifier.weight(1f),
            controlPoint1 = point1,
            controlPoint2 = point2,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("x1")
            Slider(value = point1.value.x, onValueChange = {
                point1.value = point1.value.copy(x = it)
            })
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("y1")
            Slider(value = point1.value.y, onValueChange = {
                point1.value = point1.value.copy(y = it)
            })
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("x2")
            Slider(value = point2.value.x, onValueChange = {
                point2.value = point2.value.copy(x = it)
            })
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("y2")
            Slider(value = point2.value.y, onValueChange = {
                point2.value = point2.value.copy(y = it)
            })
        }
    }
}

@Composable
fun CubicBezierBoard(
    modifier: Modifier = Modifier,
    ratio: Float = 0.05f,
    lineCount: Int = 5,
    controlPoint1: State<Offset> = mutableStateOf(Offset(1f, 0f)),
    controlPoint2: State<Offset> = mutableStateOf(Offset(0f, 1f)),
) {
    val flag = remember {
        mutableStateOf(false)
    }
    val process by animateIntAsState(
        targetValue = if (flag.value) 1000 else 0,
        animationSpec = repeatable(
            iterations = 1000,
            animation = tween(delayMillis = 500),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "process",
    )
    LaunchedEffect(key1 = "CubicBezierPage") {
        flag.value = !flag.value
    }
    val startOffset = Offset(0f, 0f)
    val endOffset = Offset(1f, 1f)
    val point1 = controlPoint1.value
    val point2 = controlPoint2.value
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)
        ) {
            drawBaseLine(ratio = ratio, lineCount = lineCount)
            drawAuxiliaryLine(ratio = ratio, start = startOffset, end = point1)
            drawAuxiliaryLine(ratio = ratio, start = endOffset, end = point2)

            drawOriginCircle(ratio = ratio, offset = startOffset)
            drawOriginCircle(ratio = ratio, offset = endOffset)

            drawOriginCircle(color = Color.Blue, ratio = ratio, offset = point1)
            drawOriginCircle(color = Color.Blue, ratio = ratio, offset = point2)

            val width = size.width
            val height = size.height
            val contentWidth = width * (1 - ratio * 2)
            val contentHeight = height * (1 - ratio * 2)

            for (processIndex in 0..process) {
                val t = processIndex.toFloat() / 1000
                val tOffset = Offset(
                    (1 - t).pow(3) * startOffset.x + 3 * ((1 - t).pow(2)) * t * point1.x + 3 * (1 - t) * (t.pow(2)) * point2.x + t.pow(
                        3
                    ) * endOffset.x,
                    (1 - t).pow(3) * startOffset.y + 3 * ((1 - t).pow(2)) * t * point1.y + 3 * (1 - t) * (t.pow(2)) * point2.y + t.pow(
                        3
                    ) * endOffset.y,
                )
                val offset = Offset(
                    width * ratio + contentWidth * tOffset.x,
                    height * ratio + contentHeight * (1 - tOffset.y),
                )
                drawCircle(color = Color.Black, radius = 2.dp.toPx(), center = offset)
            }
        }
    }
}

private fun DrawScope.drawAuxiliaryLine(
    color: Color = Color.Red,
    ratio: Float = 0.05f,
    start: Offset,
    end: Offset,
) {
    val width = size.width
    val height = size.height
    val contentWidth = width * (1 - ratio * 2)
    val contentHeight = height * (1 - ratio * 2)
    drawLine(
        color = color,
        start = Offset(
            width * ratio + contentWidth * start.x,
            height * ratio + contentHeight * (1 - start.y),
        ),
        end = Offset(
            width * ratio + contentWidth * end.x,
            height * ratio + contentHeight * (1 - end.y),
        ),
        strokeWidth = 2.dp.toPx()
    )
}

private fun DrawScope.drawBaseLine(
    color: Color = Color.Black.copy(alpha = 0.8f),
    ratio: Float = 0.05f,
    lineCount: Int = 5,
) {
    val width = size.width
    val height = size.height
    val contentWidth = width * (1 - ratio * 2)
    val contentHeight = height * (1 - ratio * 2)
    val intervals = contentWidth.coerceAtLeast(contentHeight) / 20 / lineCount
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(intervals * 1.2f, intervals * 0.8f))
    for (i in 0..lineCount) {
        val x = width * ratio + contentWidth * i / lineCount
        val startX = Offset(x, height * ratio)
        val endX = Offset(x, height * (1 - ratio))
        drawLine(
            color = color,
            start = startX,
            end = endX,
            strokeWidth = 1.dp.toPx(),
            pathEffect = pathEffect,
        )

        val y = height * ratio + contentHeight * i / lineCount
        val startY = Offset(width * ratio, y)
        val endY = Offset(width * (1 - ratio), y)
        drawLine(
            color = color,
            start = startY,
            end = endY,
            strokeWidth = 1.dp.toPx(),
            pathEffect = pathEffect,
        )
    }
}

private fun DrawScope.drawOriginCircle(
    color: Color = Color.Black,
    ratio: Float,
    offset: Offset,
) {
    val width = size.width
    val height = size.height
    val contentWidth = width * (1 - ratio * 2)
    val contentHeight = height * (1 - ratio * 2)
    drawCircle(
        color = color,
        radius = 5.dp.toPx(),
        center = Offset(
            width * ratio + contentWidth * offset.x,
            height * ratio + contentHeight * (1 - offset.y),
        )
    )
}

@Preview
@Composable
fun CubicBezierPage_PreView() {
    CubicBezierPage()
}