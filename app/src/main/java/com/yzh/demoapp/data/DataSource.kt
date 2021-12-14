package com.yzh.demoapp.data

import com.yzh.demo.recycler_view.RecyclerViewActivity
import com.yzh.demoapp.activity.DemoActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity
import com.yzh.demoapp.calculator.CalculatorActivity

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
        dataList.add(
            MainPageItemData(
                title = "RecyclerView",
                clazz = RecyclerViewActivity::class,
                description = "验证RecyclerView的demo"
            )
        )
    }

    fun getMainPageList(): ArrayList<MainPageItemData> {
        return dataList
    }
}