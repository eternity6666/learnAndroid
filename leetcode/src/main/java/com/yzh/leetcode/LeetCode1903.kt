package com.yzh.leetcode

class LeetCode1903 {
    fun largestOddNumber(num: String): String {
        val lastIndex = num.indexOfLast {
            it.digitToIntOrNull()?.mod(2) == 1
        }
        if (lastIndex == -1) {
            return ""
        }
        return num.substring(0, lastIndex + 1)
    }
}