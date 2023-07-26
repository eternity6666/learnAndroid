package com.yzh.leetcode

import java.util.Stack

class LeetCode0735 {
    fun asteroidCollision(asteroids: IntArray): IntArray {
        val size = asteroids.size
        if (size < 2) {
            return asteroids
        }
        val stack = Stack<Int>()
        asteroids.forEach {
            while (true) {
                if (stack.isEmpty()) {
                    stack.push(it)
                    break
                }
                val top = stack.pop()
                if (top < 0 || it > 0) {
                    stack.push(top)
                    stack.push(it)
                    break
                }
                if (top + it == 0) {
                    break
                } else if (top + it > 0) {
                    stack.push(top)
                    break
                }
            }
        }
        return stack.toIntArray()
    }
}
