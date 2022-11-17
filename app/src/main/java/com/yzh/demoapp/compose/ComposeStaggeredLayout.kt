/*
 * CopyRight (C) 2022 Tencent. All rights reserved.
 */
package com.yzh.demoapp.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yzh.demo.mockdata.randomString

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LazyStaggeredLayout() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(90.dp),
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(List(100) { randomString().repeat(randomInt() % 3 + 1) }) { index, text ->
            Card {
                Text(text = "$index$text", modifier = Modifier.padding(5.dp))
            }
        }
    }
}