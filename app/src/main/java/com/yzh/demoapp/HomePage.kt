package com.yzh.demoapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yzh.base.extension.jumpTo
import com.yzh.demoapp.data.DataSource
import com.yzh.demoapp.data.MainPageItemData

@Preview
@Composable
fun HomePage(
    modifier: Modifier = Modifier
) {
    val dataList = DataSource.getMainPageList()
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        itemsIndexed(dataList) { index, data ->
            HomePageItem(
                data = data,
                containerColor = if (index % 2 == 0)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary,
            ) {
                context.jumpTo(data.clazz)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageItem(
    data: MainPageItemData,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit,
) {
    val isExpand = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .combinedClickable(
                enabled = true,
                onClick = onClick,
                onLongClick = { isExpand.value = !isExpand.value }
            ),
        colors = cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            val rotate by animateFloatAsState(if (isExpand.value) 180f else 0f)
            Row {
                Text(
                    text = data.title,
                    modifier = Modifier.weight(1f)
                )
                if (data.description.isNotEmpty()) {
                    Icon(
                        Icons.Outlined.KeyboardArrowDown,
                        if (isExpand.value) "收起" else "展开",
                        modifier = Modifier
                            .clickable { isExpand.value = !isExpand.value }
                            .rotate(rotate)
                    )
                }
            }
            if (data.description.isNotEmpty()) {
                AnimatedVisibility(visible = isExpand.value) {
                    Text(
                        text = data.description,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
