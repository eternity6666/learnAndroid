package com.yzh.demoapp.data.source

import com.yzh.demoapp.data.item.DataItem

object FirstDataSource {

    val dataList: ArrayList<DataItem> = ArrayList()

    init {
        initDataList(100)
    }

    private fun initDataList(count: Int) {
        for (i in 0..count) {
            dataList.add(
                DataItem.build {
                    id = i
                    title = "title$i"
                    subTitle = "subTitle$i"
                }
            )
        }
    }

}