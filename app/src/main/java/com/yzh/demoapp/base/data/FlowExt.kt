/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.base.data

import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> MutableStateFlow<List<T>>.emit(newItem: T) {
    emit(buildList {
        addAll(value)
        add(newItem)
    })
}