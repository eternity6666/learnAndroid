package com.yzh.leetcode

class LeetCode0208 {
    private val root = TreeNode()
    fun insert(word: String) {
        var tmp = root
        word.forEachIndexed { index, char ->
            tmp = tmp.treeMap[char] ?: TreeNode().also {
                tmp.treeMap[char] = it
            }
            if (index == word.lastIndex) {
                tmp.treeMap['E'] = TreeNode()
            }
        }
    }

    fun search(word: String): Boolean {
        var tmp = root
        for (index in word.indices) {
            val char = word[index]
            tmp = tmp.treeMap[char] ?: return false
        }
        return tmp.treeMap.containsKey('E')
    }

    fun startsWith(prefix: String): Boolean {
        var tmp = root
        for (index in prefix.indices) {
            val char = prefix[index]
            tmp = tmp.treeMap[char] ?: return false
        }
        return true
    }

    private data class TreeNode(
        val treeMap: MutableMap<Char, TreeNode> = mutableMapOf(),
    )
}