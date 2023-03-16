package com.yzh.demoapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yzh.annotation.YActivity
import com.yzh.demoapp.MainActivity
import com.yzh.demoapp.data.DataSource
import com.yzh.demoapp.data.MainPageItemData

/**
 * @author eternity6666@qq.com
 * @since 2022/6/12 16:16
 */
@YActivity(description = "学一学 Compose")
class ComposeLearnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    LazyColumn(
        contentPadding = PaddingValues(12.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
    ) {
        items(DataSource.getMainPageList()) { mainPageItemData ->
            MainPageItemView(mainPageItemData = mainPageItemData)
        }
    }
}

@Composable
@Preview
fun PreviewMainContent() {
    MainContent()
}

@Composable
fun MainPageItemView(mainPageItemData: MainPageItemData) {
    var isExpanded by remember { mutableStateOf(false) }
    val hasDescription = mainPageItemData.description.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(CornerSize(8.dp))
            )
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = mainPageItemData.title,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
            )
            if (hasDescription) {
                Spacer(modifier = Modifier.requiredWidth(8.dp))
                Icon(
                    if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    null,
                    Modifier
                        .size(16.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .animateContentSize()
        ) {
            if (hasDescription && isExpanded) {
                Text(
                    text = mainPageItemData.description,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainPageItemView() {
    MainPageItemView(
        MainPageItemData(
            "123123".repeat(20),
            "哇哦哈哈哈哈",
            MainActivity::class,
        )
    )
}