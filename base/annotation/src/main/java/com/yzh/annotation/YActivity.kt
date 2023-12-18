package com.yzh.annotation


/**
 * @author eternity6666@qq.com
 * @since 2023/3/16 16:16
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class YActivity(
    val title: String = "",
    val description: String = "",
)
