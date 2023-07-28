package com.yzh.leetcode

class LeetCode1732 {
    fun largestAltitude(gain: IntArray): Int {
        if (gain.isEmpty()) {
            return 0
        }
        var result = maxOf(0, gain.first())
        gain.reduce { acc, i ->
            result = maxOf(result, acc + i)
            acc + i
        }
        return result
    }
}