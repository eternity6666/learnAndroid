/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.base.network

import okhttp3.WebSocket

private var subscribeId = 0

fun WebSocket.subscribe(destination: String) {
    subscribeId += 1
    stomp(StompType.Subscribe, "id:sub-$subscribeId\ndestination:$destination")
}

private fun WebSocket.stomp(type: StompType, message: String) {
    send("${type.name}\n$message\n\n\u0000")
}

private sealed class StompType(val name: String) {
    object Connect : StompType("CONNECT")
    object Begin: StompType("BEGIN")
    object Subscribe : StompType("SUBSCRIBE")
    object Commit: StompType("COMMIT")
    object ACK: StompType("ACK")
}