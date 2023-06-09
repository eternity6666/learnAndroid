/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.base.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputArea(
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    onClick: (String) -> Unit = {},
    label: @Composable RowScope.() -> Unit = { Text(text = "点击") },
) {
    var inputString by remember { mutableStateOf(defaultValue) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = inputString,
            onValueChange = { inputString = it }
        )
        Button(
            onClick = { onClick(inputString) },
            content = label,
        )
    }
}
