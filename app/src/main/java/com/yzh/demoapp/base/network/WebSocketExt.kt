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
    data object Connect : StompType("CONNECT")
    data object Begin: StompType("BEGIN")
    data object Subscribe : StompType("SUBSCRIBE")
    data object Commit: StompType("COMMIT")
    data object ACK: StompType("ACK")
}