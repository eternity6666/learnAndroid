package com.yzh.leetcode

import java.util.LinkedList

class LeetCode0933 {
    private val queue = LinkedList<Int>()

    fun ping(t: Int): Int {
        while (queue.isNotEmpty()) {
            if (queue.first + 3000 >= t) {
                break
            }
            queue.removeFirst()
        }
        queue.add(t)
        return queue.size
    }
}