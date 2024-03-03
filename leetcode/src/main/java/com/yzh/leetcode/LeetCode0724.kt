package com.yzh.leetcode

class LeetCode0724 {
    fun pivotIndex(nums: IntArray): Int {
        val preSum = IntArray(nums.size) { 0 }
        nums.forEachIndexed { index, item ->
            preSum[index] = if (index == 0) {
                item
            } else {
                preSum[index - 1] + item
            }
        }
        nums.forEachIndexed { index, item ->
            if (preSum[index] - item == preSum.last() - preSum[index]) {
                return index
            }
        }
        return -1
    }
}