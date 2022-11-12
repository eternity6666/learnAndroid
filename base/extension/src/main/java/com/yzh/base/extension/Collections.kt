/*
 * CopyRight (C) 2022 Tencent. All rights reserved.
 */
package com.yzh.base.extension

import java.util.*

inline fun <T> PriorityQueue(
    size: Int,
    init: (Int) -> T,
    comparable: Comparator<in T>,
): PriorityQueue<T> {
    return PriorityQueue<T>(size, comparable).apply {
        repeat(size) { index ->
            add(init(index))
        }
    }
}