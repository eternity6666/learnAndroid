package com.yzh.leetcode

class LeetCode2050 {
    fun minimumTime(n: Int, relations: Array<IntArray>, time: IntArray): Int {
        val prevMap = mutableMapOf<Int, MutableList<Int>>()
        repeat(n) { index ->
            prevMap[index] = mutableListOf()
        }
        relations.forEach {
            val prev = it[0]
            val next = it[1]
            prevMap[next - 1]?.add(prev - 1)
        }
        val timeMap = mutableMapOf<Int, Int>()
        return prevMap.keys.maxOf { index ->
            dp(index, prevMap, timeMap, time)
        }
    }

    private fun dp(
        index: Int,
        prevMap: MutableMap<Int, MutableList<Int>>,
        timeMap: MutableMap<Int, Int>,
        time: IntArray,
    ): Int {
        return timeMap[index] ?: (prevMap[index]?.maxOf {
            dp(it, prevMap, timeMap, time)
        } ?: 0).let {
            timeMap[index] = it + time[index]
//            println("$index==${timeMap[index]} $it ${time[index]}")
            it + time[index]
        }
    }

    private inline fun <T> Collection<T>.maxOf(block: (T) -> Int): Int {
        var max = 0
        forEach {
            max = maxOf(max, block(it))
        }
        return max
    }
}