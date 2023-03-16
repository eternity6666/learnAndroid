package com.yzh.demoapp.data

import android.app.Activity
import com.yzh.annotation.YActivity
import com.yzh.demo.recycler_view.RecyclerViewActivity
import com.yzh.demoapp.activity.CameraXDemoActivity
import com.yzh.demoapp.activity.CustomViewActivity
import com.yzh.demoapp.activity.GradientDrawableActivity
import com.yzh.demoapp.activity.OrientationActivity
import com.yzh.demoapp.activity.ValueAnimatorActivity
import com.yzh.demoapp.activity.WeatherActivity
import kotlin.reflect.KClass

object DataSource {

    private val dataList: ArrayList<MainPageItemData> = ArrayList()
    private val activityList = listOf<KClass<out Activity>>(
        WeatherActivity::class,
        CameraXDemoActivity::class,
        OrientationActivity::class,
        CustomViewActivity::class,
        ValueAnimatorActivity::class,
        RecyclerViewActivity::class,
        GradientDrawableActivity::class,
    )

    init {
        activityList.forEach { clazz ->
            val annotation = clazz.annotations.find { it is YActivity } as? YActivity
            val description = annotation?.description.orEmpty()
            val title = annotation?.title ?: clazz.simpleName.orEmpty()
            dataList.add(
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