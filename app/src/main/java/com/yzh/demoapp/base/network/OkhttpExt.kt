/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.base.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

fun OkHttpClient.newWebSocket(
    request: Request,
    onOpen: (WebSocket, Response) -> Unit = { _, _ -> },
    onTextMessage: (WebSocket, String) -> Unit = { _, _ -> },
    onByteMessage: (WebSocket, ByteString) -> Unit = { _, _ -> },
    onClosing: (WebSocket, Int, String) -> Unit = { _, _, _ -> },
    onClosed: (WebSocket, Int, String) -> Unit = { _, _, _ -> },
    onFailure: (WebSocket, Throwable, Response?) -> Unit = { _, _, _ -> },
): WebSocket {
    return newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            onOpen(webSocket, response)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            onTextMessage(webSocket, text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            onByteMessage(webSocket, bytes)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            onClosing(webSocket, code, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            onClosed(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            onFailure(webSocket, t, response)
        }
    })
}