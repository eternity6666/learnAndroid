package com.yzh.demoapp.data

import com.yzh.annotation.YActivity
import com.yzh.demoapp.aacell.AACellActivity
import com.yzh.demoapp.activity.AppBarLayoutActivity
import com.yzh.demoapp.activity.AppListActivity
import com.yzh.demoapp.activity.CameraXDemoActivity
import com.yzh.demoapp.activity.ComposeLearnActivity
import com.yzh.demoapp.activity.CustomViewActivity
import com.yzh.demoapp.activity.FundActivity
import com.yzh.demoapp.activity.GradientDrawableActivity
import com.yzh.demoapp.activity.OrientationActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity
import com.yzh.demoapp.activity.ViewPagerActivity
import com.yzh.demoapp.activity.WeatherActivity
import com.yzh.demoapp.calculator.CalculatorActivity
import com.yzh.demoapp.yapp.YAppActivity
import com.yzh.demoapp.card_view.CardViewActivity
import com.yzh.demoapp.recycler_view.RecyclerViewActivity

object DataSource {

    private val activityList = listOf(
        YAppActivity::class,
        ViewPagerActivity::class,
        AACellActivity::class,
        AppListActivity::class,
        FundActivity::class,
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
        AppBarLayoutActivity::class,
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