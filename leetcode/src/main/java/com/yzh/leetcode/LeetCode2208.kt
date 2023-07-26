package com.yzh.leetcode

import java.util.PriorityQueue

class LeetCode2208 {
    fun halveArray(nums: IntArray): Int {
        val priorityQueue = PriorityQueue<Double> { o1, o2 -> if (o1 > o2) 1 else -1 }
        var originSum = 0.0
        nums.forEach {
            priorityQueue.add(it.toDouble())
            originSum += it
        }
        var tmpSum = originSum
        var result = 0
        while (tmpSum > originSum / 2) {
            val first = priorityQueue.poll() ?: break
            tmpSum -= first / 2
            result += 1
            priorityQueue.add(first / 2)
        }
        return result
    }
}