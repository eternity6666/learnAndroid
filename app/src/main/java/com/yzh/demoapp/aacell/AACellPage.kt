/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.aacell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yzh.demoapp.compose.base.InputArea


@Composable
internal fun AACellPage(
    viewModel: AACellViewModel,
) {
    val isConnected by viewModel.isConnected.collectAsState(initial = false)
    if (isConnected) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "数据展示")
            AACellContent(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                viewModel = viewModel,
            )
            AACellSendMessage(
                modifier = Modifier
                    .heightIn(min = 100.dp, max = 200.dp)
                    .fillMaxWidth(),
                viewModel = viewModel,
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            LoginAACell(
                modifier = Modifier.align(Alignment.Center),
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun LoginAACell(
    modifier: Modifier = Modifier,
    viewModel: AACellViewModel,
) {
    InputArea(
        modifier = modifier,
        defaultValue = "0909",
        onClick = { viewModel.loadData(roomId = it) },
    ) {
        Text(text = "请求")
    }
}

@Composable
private fun AACellContent(
    modifier: Modifier = Modifier,
    viewModel: AACellViewModel,
) {
    val messageList by viewModel.messageList.collectAsState(initial = emptyList())
    if (messageList.isEmpty()) {
        Box(modifier = modifier) {
            Text(text = "empty", modifier = Modifier.align(Alignment.Center))
        }
    }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(messageList) {
            Text(
                text = it,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}

@Composable
private fun AACellSendMessage(
    modifier: Modifier = Modifier,
    viewModel: AACellViewModel,
) {
    InputArea(
        modifier = modifier,
        defaultValue = "你好啊",
        onClick = { viewModel.sendMessage(message = it) },
    ) {
        Text(text = "发送")
    }
}

