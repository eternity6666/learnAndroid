package com.yzh.leetcode

class LeetCode1137 {
    private val dict = mutableMapOf<Int, Int>()

    fun tribonacci(n: Int): Int {
        for (i in 0..n) {
            dict[i] = when (i) {
                0 -> 0
                1, 2 -> 1
                else -> (dict[i - 1] ?: 0) + (dict[i - 2] ?: 0) + (dict[i - 3] ?: 0)
            }
        }
        return dict[n] ?: 0
    }
}