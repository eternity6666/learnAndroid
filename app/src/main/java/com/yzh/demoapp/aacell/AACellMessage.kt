package com.yzh.demoapp.aacell

import com.yzh.demoapp.base.data.toTyped

/**
 * CopyRight (C) 2023 Tencent. All rights reserved.
 *
 * @author eternity6666@qq.com
 * @since 2023/6/9 16:16
 */
data class AACellMessage(
    val message: String = "",
    val id: Int = 0,
    val isDelete: Boolean = false,
    val time: Long = 0L,
)

fun AACellMessage(json: String): AACellMessage? {
    return json.toTyped()
}