package com.yzh.demoapp.yapp.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yzh.demoapp.compose.randomColor
import com.yzh.demoapp.yapp.weather.YWeatherPage

sealed class YAppRoute(
    val page: @Composable () -> Unit,
    val name: String = "",
    val bgColor: Color = randomColor()
) {
    object Weather : YAppRoute(page = { YWeatherPage() })

    val routeName: String
        get() = this.javaClass.simpleName
}

internal val yAppSupportList = listOf<YAppRoute>(
    YAppRoute.Weather
)