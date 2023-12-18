package com.yzh.base.extension

import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun <T : Any> Context.jumpTo(clazz: KClass<T>) {
    val intent = Intent()
    intent.setClass(this, clazz.java)
    startActivity(intent)
}