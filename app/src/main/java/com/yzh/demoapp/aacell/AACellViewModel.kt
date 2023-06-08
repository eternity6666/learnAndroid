/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.aacell

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
        webSocket?.send(message)
        viewModelScope.launch {
            _messageList.emit(message)
        }
    }

    fun loadData(
        roomId: String = "",
        token: String = TOKEN,
    ) {
        webSocket?.cancel()
        webSocket = client.newWebSocket(request = buildRequest(roomId, token), object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.i(TAG, "onOpen: response=$response")
                super.onOpen(webSocket, response)
                viewModelScope.launch {
                    _isConnected.emit(true)
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.i(TAG, "onMessageString: $text")
                super.onMessage(webSocket, text)
                viewModelScope.launch {
                    _messageList.emit(text)
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.i(TAG, "onMessageByteString: $bytes")
                super.onMessage(webSocket, bytes)
                viewModelScope.launch {
                    _messageList.emit(bytes.utf8())
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.i(TAG, "onClosing: code=$code reason=$reason")
                super.onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.i(TAG, "onClosed: code=$code reason=$reason")
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.i(TAG, "onFailure: response=$response")
                t.printStackTrace()
                super.onFailure(webSocket, t, response)
            }
        })
    }

    companion object {
        private const val TAG = "AACell"
        private const val TOKEN =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NjIzMTc2MCwicm9vbWlkIjoiMDkwOSJ9.Smmr8LknMwsF1XXud6oBa8kUrD71Jk2TB0J02q5ACDo"

        private fun buildRequest(
            roomId: String = "",
            token: String = "",
        ): Request {
            return Request.Builder()
                .get()
                .url(
                    "wss://aacell.me/socket/connect/421/svwoft3c/websocket?roomid=$roomId&token=$token"
                )
                .build()
        }

        private suspend fun <T> MutableStateFlow<List<T>>.emit(newItem: T) {
            emit(buildList {
                addAll(value)
                add(newItem)
            })
        }
    }
}
