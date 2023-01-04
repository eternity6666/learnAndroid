package com.yzh.demoapp.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyText() {
    Text(
        text = "搜索全站",
        modifier = Modifier
            .padding(10.dp)
    )
}

@Preview
@Composable
fun TestBackground() {
    MyText()
}