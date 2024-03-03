package com.yzh.leetcode

import java.util.TreeSet

class LeetCode1268 {
    fun suggestedProducts(products: Array<String>, searchWord: String): List<List<String>> {
        val root = TreeNode()
        products.forEach { str ->
            var tmp = root
            str.forEach { char ->
                tmp = tmp.treeMap[char] ?: TreeNode().also {
                    tmp.treeMap[char] = it
                }
                tmp.list.add(str)
            }
        }
        println(root.toString())
        val result: MutableList<List<String>> = mutableListOf()
        var tmp: TreeNode? = root
        searchWord.forEach {
            tmp = tmp?.treeMap?.get(it)
            result.add(tmp?.list?.take(3) ?: emptyList())
        }
        return result
    }

    private data class TreeNode(
        val treeMap: MutableMap<Char, TreeNode> = mutableMapOf(),
        val list: MutableSet<String> = TreeSet<String>()
    )
}