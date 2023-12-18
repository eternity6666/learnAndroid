package com.yzh.demoapp.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import java.util.UUID
import kotlin.math.sqrt

private data class Ball(
    val size: Dp,
    var offset: DpOffset = DpOffset(0.dp, 0.dp),
) {
    val id = UUID.randomUUID()
    var dx = randomPosition(5, 10)
    var dy = randomPosition(0, 10)
}

@Composable
fun GravitySensingBallPage() {
    val ballList = List(10) {
        Ball(size = randomPosition(50, 150).dp)
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState)
    ) {
        ballList.forEachIndexed { index, ball ->
            calculateOffset(index, ballList)
            DrawBall(
                modifier = Modifier
                    .size(ball.size)
                    .offset(x = ball.offset.x, ball.offset.y)
            )
        }
    }
}

private fun calculateOffset(
    ballIndex: Int,
    ballList: List<Ball>,
) {
    require(ballIndex >= 0 && ballIndex < ballList.size)
    val current = ballList[ballIndex]
    if (ballIndex > 0) {
        val last = ballList[ballIndex - 1]
        val sqrt2 = sqrt(2f)
        val offset = last.size / 2 + (last.size - current.size * (sqrt2 - 1)) / sqrt2 / 2
        current.offset = DpOffset(
            x = max(0.dp, last.offset.x + offset),
            y = max(0.dp, last.offset.y + offset)
        )
    }
}

@Composable
private fun DrawBall(
    modifier: Modifier,
) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val minSize = minOf(size.width, size.height)
        drawIntoCanvas {
            val paint = Paint()
            paint.color = randomColor()
            it.drawCircle(center = Offset(centerX, centerY), radius = minSize / 2, paint = paint)
        }
    }
}

@Preview
@Composable
fun GravitySensingBallPage_Preview() {
    GravitySensingBallPage()
}
