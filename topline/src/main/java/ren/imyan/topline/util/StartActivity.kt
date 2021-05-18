package ren.imyan.topline.util

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-27 16:45
 * @website https://imyan.ren
 */

/**
 * 使用内联函数启动一个 Activity
 * @param T 要启动的 Activity
 * @param pairs （可选）Intent 中需要传递的参数
 */
inline fun <reified T> Activity.start(vararg pairs: Pair<String,Any>){
    startActivity(Intent(this,T::class.java), bundleOf(*pairs))
}
