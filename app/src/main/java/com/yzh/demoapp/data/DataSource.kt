package com.yzh.demoapp.data

import com.yzh.annotation.YActivity
import com.yzh.demo.card_view.CardViewActivity
import com.yzh.demo.recycler_view.RecyclerViewActivity
import com.yzh.demoapp.activity.CameraXDemoActivity
import com.yzh.demoapp.activity.ComposeLearnActivity
import com.yzh.demoapp.activity.CustomViewActivity
import com.yzh.demoapp.activity.GradientDrawableActivity
import com.yzh.demoapp.activity.OrientationActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity
import com.yzh.demoapp.activity.WeatherActivity
import com.yzh.demoapp.calculator.CalculatorActivity

object DataSource {

    private val activityList = listOf(
        WeatherActivity::class,
        CameraXDemoActivity::class,
        OrientationActivity::class,
        CustomViewActivity::class,
        ValueAnimatorActivity::class,
        RecyclerViewActivity::class,
        GradientDrawableActivity::class,
        CardViewActivity::class,
        CalculatorActivity::class,
        ComposeLearnActivity::class,
    )
    private val dataList: ArrayList<MainPageItemData> = ArrayList<MainPageItemData>().apply {
        activityList.forEach { clazz ->
            val annotation = clazz.annotations.find { it is YActivity } as? YActivity
            val title = annotation?.title.orEmpty().ifEmpty { clazz.simpleName.orEmpty() }
            val description = annotation?.description.orEmpty()
            add(
                MainPageItemData(
                    title = title,
                    description = description,
                    clazz = clazz,
                )
            )
        }
    }

    fun getMainPageList(): ArrayList<MainPageItemData> {
        return dataList
    }
}