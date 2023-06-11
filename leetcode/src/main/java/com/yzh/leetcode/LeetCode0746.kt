package com.yzh.leetcode

class LeetCode0746 {
    fun minCostClimbingStairs(cost: IntArray): Int {
        if (cost.size <= 2) {
            return cost.min()
        }
        val dp = MutableList(cost.size + 1) { Int.MAX_VALUE }
        dp.forEachIndexed { index, _ ->
            val item = cost.getOrNull(index) ?: 0
            when (index) {
                0, 1 -> dp[index] = item
                else -> dp[index] = (dp[index - 1]?.coerceAtMost(dp[index - 2] ?: 0) ?: 0) + item
            }
        }
        return dp[cost.size] ?: cost[0]
    }
}