package com.yzh.demoapp.aacell.model

sealed class AACellScreen(
    val route: String,
) {
    object AACellHome: AACellScreen("aacellHome")
    object AACellCreate: AACellScreen("aacellCreate")
    object AACellConnected: AACellScreen("aacellConnected")
}