package com.yzh.leetcode

class LeetCode0605 {

    fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
        val len = flowerbed.size
        if (len == 0) {
            return n == 0
        }
        var enableCount = 0
        var index = 0
        while (index < len) {
            val enableLeft = index - 1 < 0 || flowerbed[index - 1] == 0
            val enableRight = index + 1 >= len || flowerbed[index + 1] == 0
            val enableCurrent = flowerbed[index] == 0
            if (enableRight && enableLeft && enableCurrent) {
                enableCount ++
                index += 2
            } else {
                index += 1
            }
        }
        print(enableCount)
        return enableCount >= n
    }
}