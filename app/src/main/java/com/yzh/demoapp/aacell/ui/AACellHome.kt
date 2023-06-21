package com.yzh.demoapp.aacell.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yzh.demoapp.aacell.AACellViewModel
import com.yzh.demoapp.aacell.model.RoomType


@Composable
internal fun AACellHome(
    modifier: Modifier = Modifier,
    viewModel: AACellViewModel,
) {
    val roomType by viewModel.roomType.collectAsState(initial = RoomType.Unknown)
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            var roomId by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = roomId,
                onValueChange = {
                    roomId = it
                    viewModel.fetchRoomType(it)
                },
            )
            when (roomType) {
                RoomType.Unknown -> {
                    Button(onClick = {
                        viewModel.randomRoom()
                    }) {
                        Text(text = "随机创建")
                    }
                }

                RoomType.NotFound, RoomType.Found.NoPassword -> {
                    Button(onClick = {
                        viewModel.loadData(roomId)
                    }) {
                        Text(text = "创建或进入")
                    }
                }

                RoomType.Found.NeedPassword -> {
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                    )
                    Button(onClick = {
                        viewModel.createRoom(roomId)
                    }) {
                        Text(text = "创建")
                    }
                }
            }
        }
    }
}
