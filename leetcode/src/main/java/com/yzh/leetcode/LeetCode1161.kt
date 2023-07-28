package com.yzh.leetcode

import java.util.LinkedList
import java.util.Queue

class LeetCode1161 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    fun maxLevelSum(root: TreeNode?): Int {
        return root?.let { rootNode ->
            var resultIndex = 1
            var resultSum = rootNode.`val`
            val queue: Queue<Pair<TreeNode, Int>> = LinkedList()
            queue.offer(rootNode to 1)
            var tmpIndex = 1
            var tmpSum = 0
            while (!queue.isEmpty()) {
                val pair = queue.remove()
                val node = pair.first
                if (tmpIndex == pair.second) {
                    tmpSum += node.`val`
                } else {
                    if (resultSum < tmpSum) {
                        resultSum = tmpSum
                        resultIndex = tmpIndex
                    }
                    tmpSum = node.`val`
                    tmpIndex = pair.second
                }
                node.left?.let {
                    queue.offer(it to tmpIndex + 1)
                }
                node.right?.let {
                    queue.offer(it to tmpIndex + 1)
                }
            }
            if (resultSum < tmpSum) {
                resultIndex = tmpIndex
            }
            resultIndex
        } ?: 1
    }
}