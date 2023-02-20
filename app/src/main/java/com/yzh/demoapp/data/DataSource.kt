package com.yzh.demoapp.data

import com.yzh.demo.recycler_view.RecyclerViewActivity
import com.yzh.demoapp.activity.CameraXDemoActivity
import com.yzh.demoapp.activity.CustomViewActivity
import com.yzh.demoapp.activity.GradientDrawableActivity
import com.yzh.demoapp.activity.OrientationActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity
import com.yzh.demoapp.activity.WeatherActivity
import com.yzh.demoapp.calculator.CalculatorActivity

object DataSource {

    private val dataList: ArrayList<MainPageItemData> = ArrayList()

    init {
        dataList.add(
            MainPageItemData(
                title = "Camerax Demo",
                clazz = CameraXDemoActivity::class,
            )
        )
        dataList.add(
            MainPageItemData(
                title = "天气",
                clazz = WeatherActivity::class,
                description = "retrofit + compose + flow"
            )
        )
        dataList.add(
            MainPageItemData(
                title = "设备方向",
                clazz = OrientationActivity::class,
                description = "设备方向",
            )
        )
        dataList.add(
            MainPageItemData(
                title = "CustomView",
                clazz = CustomViewActivity::class,
                description = "自定义 view"
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
        dataList.add(
            MainPageItemData(
                title = "GradientDrawableActivity",
                clazz = GradientDrawableActivity::class
            )
        )
    }

    fun getMainPageList(): ArrayList<MainPageItemData> {
        return dataList
    }
}