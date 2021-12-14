package com.yzh.demoapp.util

object StringUtils {
    fun isEmpty(string: String?): Boolean {
        return string != null && string.isEmpty()
    }

    fun parseNoNullString(string: String?): String {
        return string ?: ""
    }
}