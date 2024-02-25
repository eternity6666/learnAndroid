package com.yzh.leetcode

class LeetCode0049 {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val alphabetMap = mutableMapOf<String, MutableList<String>>()
        strs.forEach { str ->
            val alphabetString = calculateAlphabetString(str)
            alphabetMap[alphabetString] = (alphabetMap[alphabetString] ?: mutableListOf()).also {
                it.add(str)
            }
        }
        return alphabetMap.values.toList()
    }

    private fun calculateAlphabetString(str: String): String {
        val charCountList = MutableList(26) { 0 }
        str.forEach {
            charCountList[it - 'a'] = charCountList[it - 'a'] + 1
        }
        val stringBuilder = StringBuilder()
        charCountList.forEachIndexed { index, count ->
            if (count > 0) {
                stringBuilder.append("${'a' + index}$count")
            }
        }
        return stringBuilder.toString()
    }
}