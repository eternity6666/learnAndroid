package com.yzh.leetcode

class LeetCode0338 {
    fun countBits(n: Int): IntArray {
        return (0..n).map { calculate(it) }.toIntArray()
    }

    private fun calculate(x: Int): Int {
        var result = 0
        var tmp = x
        while (tmp != 0) {
            if (tmp % 2 == 1) {
                result++
            }
            tmp /= 2
        }
        return result
    }
}