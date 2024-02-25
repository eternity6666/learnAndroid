package com.yzh.leetcode

import java.util.Stack

class LeetCode0226 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    fun invertTree(root: TreeNode?): TreeNode? {
        root?.innerInvertTreeDg()
        return root
    }

    private fun TreeNode.innerInvertTreeDg() {
        val tmp = left
        left = right
        right = tmp
        left?.innerInvertTreeDg()
        right?.innerInvertTreeDg()
    }

    private fun TreeNode.innerInvertTreeStack() {
        val nodeStack = Stack<TreeNode>()
        nodeStack.push(this)
        while (!nodeStack.empty()) {
            val current = nodeStack.pop()
            val tmp = current.left
            current.left = current.right
            current.right = tmp
            current.left?.let { nodeStack.push(it) }
            current.right?.let { nodeStack.push(it) }
        }
    }
}