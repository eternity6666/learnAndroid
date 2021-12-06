package com.yzh.demoapp.data

import com.yzh.demoapp.calculator.CalculatorActivity
import com.yzh.demoapp.activity.DemoActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity

object DataSource {

    private val dataList: ArrayList<MainPageItemData> = ArrayList()

    init {
        dataList.add(
            MainPageItemData(
                title = "demoActivity",
                clazz = DemoActivity::class,
                description = "description"
            )
        )
        dataList.add(
            MainPageItemData(
                title = "ValueAnimatorActivity",
                clazz = ValueAnimatorActivity::class,
                description = "解决协调布局中复杂动画的demo"
            )
        )
        dataList.add(
            MainPageItemData(
                title = "计算器",
                clazz = CalculatorActivity::class
            )
        )
    }

    fun getMainPageList(): ArrayList<MainPageItemData> {
        return dataList
    }
}