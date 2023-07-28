package com.yzh.leetcode

import java.util.PriorityQueue

class LeetCode2500 {
    fun deleteGreatestValue(grid: Array<IntArray>): Int {
        return if (grid.isEmpty()) {
            0
        } else {
            val queueList = grid.map {
                PriorityQueue<Int> { o1, o2 -> o2 - o1 }.apply {
                    addAll(it.toList())
                }
            }
            var result = 0
            repeat(grid.first().size) {
                var max = 0
                queueList.forEach {
                    max = maxOf(max, it.remove())
                }
                result += max
            }
            result
        }
    }
}