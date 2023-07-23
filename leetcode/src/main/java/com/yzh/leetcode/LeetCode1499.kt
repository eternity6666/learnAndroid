package com.yzh.leetcode

class LeetCode1499 {
    fun findMaxValueOfEquation(points: Array<IntArray>, k: Int): Int {
        var result = Int.MIN_VALUE
        val deque = ArrayDeque<IntArray>()
        points.forEach {
            val x = it[0]
            val y = it[1]
            while (deque.isNotEmpty() && x - deque.first()[0] > k) {
                deque.removeFirst()
            }
            if (deque.isNotEmpty()) {
                val first = deque.first()
                result = maxOf(result, x + y + first[1] - first[0])
            }
            while (deque.isNotEmpty() && y - x > deque.last()[1] - deque.last()[0]) {
                deque.removeLast()
            }
            deque.add(it)
        }
        return result
    }
}
