/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.base.network

/**
 * @author eternity6666@qq.com
 * @since 2023/6/9 16:16
 */
data class ResponseData<T>(
    val code: Int = 0,
    val msg: String = "",
    val data: List<T> = emptyList(),
)
