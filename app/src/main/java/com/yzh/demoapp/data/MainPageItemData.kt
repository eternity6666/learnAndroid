package com.yzh.demoapp.data

import kotlin.reflect.KClass

data class MainPageItemData(
    val title: String,
    val description: String = "",
    val clazz: KClass<*>
)
