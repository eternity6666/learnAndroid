/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.aacell

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yzh.demoapp.base.network.newWebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * @author baronyang@tencent.com
 * @since 2023/6/8 16:16
 */

class AACellViewModel(application: Application) : AndroidViewModel(application) {
    private val client by lazy {
        OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected
    private val _messageList = MutableStateFlow(emptyList<String>())
    val messageList: Flow<List<String>> = _messageList
    private var webSocket: WebSocket? = null

    fun sendMessage(message: String) {
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
                    _isConnected.emit(true)
                }
            },

            onTextMessage = { _, text ->
                Log.i(TAG, "onMessageString: $text")
                viewModelScope.launch {
                    _messageList.emit(text)
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
            return "wss://aacell.me/socket/connect/websocket?roomid=$roomId&token=$token";
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
            val token = tokenPattern.find(responseString)?.groupValues?.get(1)?.substringAfter("token = ")
            return token?.takeIf { it.isNotEmpty() } ?: fetchToken(roomId, retry - 1)
        }
    }
}
