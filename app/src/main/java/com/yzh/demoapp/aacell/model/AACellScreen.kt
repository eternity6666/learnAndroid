package com.yzh.demoapp.aacell.model

sealed class AACellScreen(
    val route: String,
) {
    data object AACellHome: AACellScreen("aacellHome")
    data object AACellCreate: AACellScreen("aacellCreate")
    data object AACellConnected: AACellScreen("aacellConnected")
}