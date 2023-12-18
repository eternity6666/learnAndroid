package com.yzh.demoapp.yapp.drawboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.sqrt


private val sqrt2 = sqrt(2.0).toFloat()

@Composable
fun DrawBoardPage(
    edge: Float = 40f,
) {
    val halfEdge = edge / 2
    val radiusEdge = edge / 3
    val radius = 5f
    val isShowHelpPoint = false
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(0f, 100f)
            path.lineTo(100f, 100f)
            path.quadraticBezierTo(
                100f + halfEdge, 100f,
                100f + halfEdge + halfEdge * sqrt2 / 2, 100f + halfEdge * sqrt2 / 2,
            )
            path.lineTo(
                100f + halfEdge + edge * sqrt2 / 2 - radiusEdge * sqrt2 / 2,
                100f + edge * sqrt2 / 2 - radiusEdge * sqrt2 / 2,
            )
            path.quadraticBezierTo(
                100f + halfEdge + edge * sqrt2 / 2,
                100f + edge * sqrt2 / 2,
                100f + halfEdge + edge * sqrt2 / 2 + radiusEdge * sqrt2 / 2,
                100f + edge * sqrt2 / 2 - radiusEdge * sqrt2 / 2,
            )
            path.lineTo(
                100f + halfEdge + edge * sqrt2 - halfEdge * sqrt2 / 2,
                100f + halfEdge * sqrt2 / 2,
            )
            path.quadraticBezierTo(
                100f + halfEdge + edge * sqrt2,
                100f,
                100f + halfEdge * 2 + edge * sqrt2,
                100f,
            )
            path.lineTo(300f + edge * sqrt2, 100f)
            path.lineTo(300f + edge * sqrt2, 0f)
            drawPath(path, Color.Red)

            if (isShowHelpPoint) {
                drawCircle(
                    color = Color.Green,
                    radius = radius,
                    center = Offset(
                        100f + halfEdge + edge * sqrt2 - halfEdge * sqrt2 / 2,
                        100f + halfEdge * sqrt2 / 2,
                    )
                )
                drawCircle(
                    color = Color.Green,
                    radius = radius,
                    center = Offset(100f, 100f)
                )
                drawCircle(
                    color = Color.Gray,
                    radius = radius,
                    center = Offset(
                        100f + halfEdge + edge * sqrt2 / 2, 100f + edge * sqrt2 / 2
                    )
                )
                drawCircle(
                    color = Color.Blue,
                    radius = radius,
                    center = Offset(
                        100f + halfEdge + edge * sqrt2 / 2 + radiusEdge * sqrt2 / 2,
                        100f + edge * sqrt2 / 2 - radiusEdge * sqrt2 / 2,
                    )
                )
                drawCircle(
                    color = Color.Green,
                    radius = radius,
                    center = Offset(
                        100f + halfEdge * 2 + edge * sqrt2,
                        100f,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawBoardPage_Previews() {
    DrawBoardPage()
}
