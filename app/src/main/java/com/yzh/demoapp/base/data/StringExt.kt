package com.yzh.demoapp.base.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.toTyped(): T? {
    return runCatching {
        Gson().fromJson<T>(this, object : TypeToken<T>() {}.type)
    }.getOrNull()
}