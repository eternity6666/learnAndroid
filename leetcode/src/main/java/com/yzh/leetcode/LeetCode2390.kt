package com.yzh.leetcode

import java.util.Stack

class LeetCode2390 {
    fun removeStars(s: String): String {
        val stack = Stack<Char>()
        s.forEach {
            stack.push(it)
        }
        var result = StringBuilder()
        var count = 0
        while (stack.isNotEmpty()) {
            val char = stack.pop()
            if (char == '*') {
                count++
            } else {
                if (count == 0) {
                    result = result.insert(0, char)
                } else {
                    count --
                }
            }
        }
        return result.toString()
    }
}