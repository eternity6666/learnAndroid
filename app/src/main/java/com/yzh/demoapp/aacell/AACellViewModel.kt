/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.aacell

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yzh.demoapp.aacell.model.RoomType
import com.yzh.demoapp.aacell.repository.AACellRepository
import com.yzh.demoapp.base.data.emitAll
import com.yzh.demoapp.base.data.emitItem
import com.yzh.demoapp.base.data.toTyped
import com.yzh.demoapp.base.network.ResponseData
import com.yzh.demoapp.base.network.newWebSocket
import com.yzh.demoapp.base.network.okHttpClient
import com.yzh.demoapp.base.network.subscribe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * @author eternity6666@qq.com
 * @since 2023/6/8 16:16
 */

class AACellViewModel(application: Application) : AndroidViewModel(application) {
    private val client by lazy {
        okHttpClient
    }
    private val repository by lazy {
        AACellRepository()
    }

    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected
    private val _messageList = MutableStateFlow(emptyList<AACellMessage>())
    val messageList: Flow<List<AACellMessage>> = _messageList
    private var webSocket: WebSocket? = null
    private val _roomType = MutableStateFlow<RoomType>(RoomType.Unknown)
    val roomType: Flow<RoomType> = _roomType

    fun sendMessage(message: String) {
    }

    fun fetchRoomType(roomId: String) {
        viewModelScope.launch {
            _roomType.emit(repository.fetchRoomType(roomId))
        }
    }

    fun createRoom(roomId: String) {
        viewModelScope.launch {
            repository.createRoom(roomId)
        }
    }

    fun randomRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val roomId = repository.randomRoom()
            if (roomId.isNotEmpty()) {
                loadData(roomId = roomId)
            }
        }
    }

    fun loadData(roomId: String = "") = viewModelScope.launch(Dispatchers.IO) {
        webSocket?.cancel()
        val token = client.fetchToken(roomId)
        Log.i(TAG, token)
        webSocket = client.newWebSocket(
            request = buildRequest(roomId, token),
            onOpen = { webSocket, response ->
                Log.i(TAG, "onOpen: response=$response")
                viewModelScope.launch {
                    requestMessages(roomId, token)
                    webSocket.send("CONNECT\naccept-version:1.2,1.1,1.0\nheart-beat:10000,10000\nroomid:$roomId\n\n\u0000")
                    webSocket.subscribe(destination = "/topic/message/$roomId")
                    webSocket.subscribe(destination = "/topic/file/$roomId")
                    webSocket.subscribe(destination = "/topic/category/$roomId")
                    webSocket.subscribe(destination = "/topic/info/$roomId")
                    _isConnected.emit(true)
                }
            },

            onTextMessage = { _, text ->
                Log.i(TAG, "onMessageString: $text")
                viewModelScope.launch {
                    if (text.startsWith(KEY_MESSAGE)) {
                        AACellMessage(
                            text.substringAfter("\n\n")
                                .substringBefore("\u0000")
                        )?.let { message ->
                            _messageList.emitItem(message)
                        }
                    }
                }
            },

            onByteMessage = { _, bytes ->
                Log.i(TAG, "onMessageByteString: $bytes")
            },

            onClosing = { _, code, reason ->
                Log.i(TAG, "onClosing: code=$code reason=$reason")
            },

            onClosed = { _, code, reason ->
                Log.i(TAG, "onClosed: code=$code reason=$reason")
            },

            onFailure = { _, t, response ->
                Log.i(TAG, "onFailure: response=$response")
                t.printStackTrace()
            },
        )
    }

    private fun requestMessages(roomId: String, token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val responseJson = runCatching {
                client.newCall(
                    Request.Builder()
                        .url("https://aacell.me/-/res/messages")
                        .header("roomid", roomId)
                        .header("token", token)
                        .build()
                ).execute()
            }.onFailure {
                Log.e(TAG, "requestMessages failure")
                it.printStackTrace()
            }.getOrNull()?.body?.string().orEmpty()
            val allMessage = responseJson.toTyped<ResponseData<AACellMessage>>()?.data
                ?: emptyList()
            Log.i(TAG, "$allMessage")
            _messageList.emitAll(allMessage)
        }

    companion object {
        private const val TAG = "AACell"
        private const val KEY_MESSAGE = "MESSAGE"

        private fun buildRequest(
            roomId: String = "",
            token: String = "",
        ): Request {
            return Request.Builder()
                .url(buildWebSocketUrl(roomId, token))
                .header("Host", "aacell.me")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"
                )
                .build()
        }

        private fun buildWebSocketUrl(roomId: String, token: String): String {
            return "wss://aacell.me/socket/connect/websocket?roomid=$roomId&token=$token"
        }

        private fun OkHttpClient.fetchToken(roomId: String, retry: Int = 3): String {
            if (roomId.isEmpty() || retry < 0) {
                return ""
            }
            val response = newCall(
                Request.Builder()
                    .url("https://aacell.me/$roomId")
                    .build()
            ).execute()
            val responseString = response.body?.string().orEmpty()
            val tokenPattern = Regex("""token =\s+"(.*?)"\s*""")
            val token = tokenPattern.find(responseString)?.groupValues?.get(1)
                ?.substringAfter("token = ")
            return token?.takeIf { it.isNotEmpty() } ?: fetchToken(roomId, retry - 1)
        }
    }
}
