/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp

class Page(
    val modules: List<Module>,
)

class Module(
    val sections: List<Section>,
)

class Section(
    val blocks: List<Block>,
)

class Block


// 计算所有 Block 的数量
fun Page.calculateAllBlocksCount(): Int = modules.fold(0) { sum, module ->
    sum + module.sections.fold(0) { sectionSum, section ->
        sectionSum + section.blocks.size
    }
}

fun Page.calculateAllBlocksCount2(): Int = modules.sumOf { it.sections.sumOf { it.blocks.size } }

fun main() {
    val section = Section(List(5) { Block() })
    val module = Module(listOf(section, section, section))
    val page = Page(listOf(module, module, module))
    println(page.calculateAllBlocksCount())
    println(page.calculateAllBlocksCount2())
}