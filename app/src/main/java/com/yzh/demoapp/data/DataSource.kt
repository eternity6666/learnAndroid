package com.yzh.demoapp.data

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
                clazz = ValueAnimatorActivity::class
        ))
    }

    fun getMainPageList() : ArrayList<MainPageItemData> {
        return dataList
    }
}