package com.yzh.leetcode

import java.util.Stack

class LeetCode0739 {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size)
        val stack = Stack<Int>()
        for (index in temperatures.indices) {
            while (true) {
                val topIndex = stack.lastOrNull()?.takeIf {
                    temperatures[it] < temperatures[index]
                } ?: break
                result[topIndex] = index - topIndex
                stack.pop()
            }
            stack.add(index)
        }
        return result
    }
}