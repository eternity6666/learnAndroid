package com.yzh.demo.data.source

import com.yzh.demo.data.item.DataItem

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