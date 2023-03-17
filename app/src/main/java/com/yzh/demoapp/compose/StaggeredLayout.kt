package com.yzh.demoapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.Orientation.Horizontal
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yzh.base.extension.PriorityQueue
import com.yzh.demo.mockdata.foregroundAndBackgroundColorPairList
import com.yzh.demo.mockdata.randomString
import kotlin.math.max

/**
 * @author eternity6666@qq.com
 * @since 2022/9/26 16:16
 */
@Composable
inline fun StaggeredLayout(
    modifier: Modifier = Modifier,
    orientation: Orientation = Horizontal,
    spanCount: Int = 1,
    content: @Composable () -> Unit
) = when (orientation) {
    Vertical -> StaggeredColumnLayout(modifier, spanCount, content)
    Horizontal -> StaggeredRawLayout(modifier, spanCount, content)
}

@Composable
inline fun StaggeredColumnLayout(
    modifier: Modifier = Modifier,
    spanCount: Int = 1,
    content: @Composable () -> Unit
) {
    require(spanCount > 0)
    Layout(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        content = content
    ) { measurableList, constraints ->
        val columnHeights = PriorityQueue(size = spanCount, init = { it to 0 }) { pair1, pair2 ->
            when {
                pair1.second < pair2.second -> -1
                pair1.second > pair2.second -> 1
                else -> pair1.first - pair2.first
            }
        }
        val columnWidths = IntArray(spanCount) { 0 }
        val cellIndexColumnMap = HashMap<Int, Int>()
        val childConstraints = constraints.copy(maxWidth = constraints.maxWidth / spanCount)
        val placeableList = measurableList.mapIndexed { index, measurable ->
            measurable.measure(childConstraints).apply {
                columnHeights.poll()?.let {
                    cellIndexColumnMap[index] = it.first
                    columnHeights.add(it.copy(second = it.second + height))
                    columnWidths[it.first] = max(columnWidths[it.first], width)
                }
            }
        }
        val width = columnWidths.sum()
            .coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
        val height = columnHeights.maxOf { it.second }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
        val columnX = IntArray(spanCount) { 0 }
        for (i in 1 until spanCount) {
            columnX[i] = columnX[i - 1] + columnWidths[i - 1]
        }
        layout(width, height) {
            val columnY = IntArray(spanCount) { 0 }
            placeableList.forEachIndexed { index, placeable ->
                val column = cellIndexColumnMap[index]
                column?.let {
                    placeable.placeRelative(
                        columnX[it],
                        columnY[it],
                    )
                    columnY[it] += placeable.height
                }
            }
        }
    }
}

@Composable
inline fun StaggeredRawLayout(
    modifier: Modifier = Modifier,
    spanCount: Int = 1,
    content: @Composable () -> Unit
) {
    require(spanCount > 0)
    Layout(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        content = content
    ) { measurableList, constraints ->
        val rowWidths = PriorityQueue(size = spanCount, init = { it to 0 }) { pair1, pair2 ->
            when {
                pair1.second < pair2.second -> -1
                pair1.second > pair2.second -> 1
                else -> pair1.first - pair2.first
            }
        }
        val rowHeights = IntArray(spanCount) { 0 }
        val cellIndexRowMap = HashMap<Int, Int>()
        val placeableList = measurableList.mapIndexed { index, measurable ->
            measurable.measure(constraints).apply {
                rowWidths.poll()?.let {
                    cellIndexRowMap[index] = it.first
                    rowWidths.add(it.copy(second = it.second + width))
                    rowHeights[it.first] = max(rowHeights[it.first], height)
                }
            }
        }
        val width = rowWidths.maxOf { it.second }
            .coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
        val height = rowHeights.sum()
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
        val rowY = IntArray(spanCount) { 0 }
        for (i in 1 until spanCount) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            val rowX = IntArray(spanCount) { 0 }
            placeableList.forEachIndexed { index, placeable ->
                val row = cellIndexRowMap[index]
                row?.let {
                    placeable.placeRelative(
                        rowX[it],
                        rowY[it]
                    )
                    rowX[it] += placeable.width
                }

            }
        }
    }
}

@Preview
@Composable
fun TestStaggeredRawLayout() {
    StaggeredLayout(
        spanCount = 4,
        orientation = Horizontal,
    ) {
        repeat(20) {
            val colorPair = foregroundAndBackgroundColorPairList.random()
            Text(
                text = "$it ${randomString()}",
                color = Color(colorPair.first),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(Color(colorPair.second))
                    .padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun TestStaggeredColumnLayout() {
    StaggeredLayout(
        spanCount = 4,
        orientation = Vertical,
    ) {
        repeat(120) {
            val colorPair = foregroundAndBackgroundColorPairList.random()
            Box(Modifier.border(0.5.dp, color = Color.White)) {
                Text(
                    text = "$it ${randomString()}",
                    color = Color(colorPair.first),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(colorPair.second))
                        .padding(10.dp)
                )
            }
        }
    }
}