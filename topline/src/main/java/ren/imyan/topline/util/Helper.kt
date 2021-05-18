package ren.imyan.topline.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-05-06 14:48
 * @website https://imyan.ren
 */
val Activity.screenWidth: Int
    get() {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.widthPixels
    }