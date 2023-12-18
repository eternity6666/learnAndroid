package com.yzh.demoapp.base.data

import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> MutableStateFlow<List<T>>.emitItem(newItem: T) {
    emit(buildList {
        addAll(value)
        add(newItem)
    })
}

suspend fun <T> MutableStateFlow<List<T>>.emitAll(list: List<T>) {
    emit(buildList {
        addAll(value)
        addAll(list)
    })
}