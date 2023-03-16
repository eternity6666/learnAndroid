package com.yzh.annotation


/**
 * CopyRight (C) 2023 Tencent. All rights reserved.
 *
 * @author baronyang@tencent.com
 * @since 2023/3/16 16:16
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class YActivity(
    val title: String = "",
    val description: String = "",
)
