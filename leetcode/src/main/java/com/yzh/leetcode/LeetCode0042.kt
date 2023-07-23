package com.yzh.leetcode

import java.util.Stack

class LeetCode0042 {
    fun trap(height: IntArray): Int {
        var result = 0
        val stack = Stack<Int>()
        for (index in height.indices) {
            while (!stack.empty() && height[index] > height[stack.peek()]) {
                val h = height[stack.peek()]
                stack.pop()
                if (stack.empty()) {
                    break
                }
                val distance = index - stack.peek() - 1
                val min = minOf(height[index], height[stack.peek()])
                result += distance * (min - h)
            }
            stack.push(index)
        }
        return result
    }
}