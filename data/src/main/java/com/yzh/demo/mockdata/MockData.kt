/*
 * CopyRight (C) 2022 Tencent. All rights reserved.
 */
package com.yzh.demo.mockdata

import android.graphics.Color
import java.util.ArrayList


val stringList = listOf(
    "雨",
    "龙珠",
    "星辰变",
    "斗罗大陆",
    "哇哦好腻害",
    "盖茨比了不起",
    "李白之天火燎原",
    "从秦始皇到汉武帝",
    "星游记之风暴法米粒",
    "一二三四五六七八九十",
    "短视频浪潮下的造星运动",
)

fun randomString(length: Int = -1): String {
    return if (length == -1) {
        stringList.random()
    } else {
        try {
            stringList.filter { it.length < length }.random()
        } catch (e: Throwable) {
            stringList.random()
        }
    }
}

fun randomImageUrl(): String {
    return listOf(
        "http://puui.qpic.cn/vpic_cover/w3351wjrqe4/w3351wjrqe4_hz.jpg/1280",
        "http://puui.qpic.cn/vpic_cover/f3350q4elat/f3350q4elat_hz.jpg/1280",
        "http://puui.qpic.cn/vpic_cover/j33535628ox/j33535628ox_hz.jpg/1280",
        "https://images.mepai.me/app/static/activity/2022-08-11/2022-08-11/b5b1bcb8f81f55891fedad596eabe83c.png!720w.jpg",
        "https://images.mepai.me/app/works/517934/2022-08-24/w_6305d0889e4c3/06305d0889e5ab.jpg!720w.jpg",
        "https://images.mepai.me/app/works/26629/2022-08-24/w_630635643aa5e/206306356472398.jpg!720w.jpg",
    ).random()
}

fun randomMarkLabelUrl(): String {
    return listOf(
        "https://vfiles.gtimg.cn/wuji_dashboard/wupload/xy/starter/OWFk439Z.png",
        "https://vfiles.gtimg.cn/wuji_dashboard/wupload/xy/starter/U6Qw6Xma.png",
        "https://vfiles.gtimg.cn/wuji_dashboard/wupload/xy/starter/hDVbcPJj.png",
        "https://vfiles.gtimg.cn/wuji_dashboard/wupload/xy/starter/DxCEUpLt.png"
    ).random()
}

val whiteForegroundColorList = listOf(
    Color.BLUE, Color.GRAY, Color.DKGRAY,
    Color.RED, Color.BLACK, Color.LTGRAY,
    Color.MAGENTA,
)

val blackForegroundColorList = listOf(
    Color.YELLOW, Color.CYAN, Color.GREEN,
)

val foregroundAndBackgroundColorPairList: List<Pair<Int, Int>> = ArrayList<Pair<Int, Int>>().apply {
    whiteForegroundColorList.forEach { add(Color.WHITE to it) }
    blackForegroundColorList.forEach { add(Color.BLACK to it) }
}

