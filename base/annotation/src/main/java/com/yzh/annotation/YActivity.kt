package com.yzh.annotation


/**
 * CopyRight (C) 2023 Tencent. All rights reserved.
 *
 * @author eternity6666@qq.com
 * @since 2023/3/16 16:16
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class YActivity(
    val title: String = "",
    val description: String = "",
)
