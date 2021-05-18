package ren.imyan.topline.util

import android.content.Context
import android.widget.Toast

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-27 17:24
 * @website https://imyan.ren
 */

/**
 * 通过 Context 扩展的 toast 方法
 * @param any 需要 Toast 的东西，会调用它的 toString() 方法
 * @param duration （可选）时间长度，默认为 Toast.LENGTH_SHORT
 * @return 返回 Toast 对象
 */
fun Context.toast(any: Any, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, any.toString(), duration).apply { show() }

/**
 * 通过 Any 扩展的 toast 方法，会调用它的 toString() 方法
 * @param context 上下文对象
 * @param duration （可选）时间长度，默认为 Toast.LENGTH_SHORT
 * @return 返回 Toast 对象
 */
fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(context, this.toString(), duration).apply { show() }

/**
 * 最垃圾的 toast 方法，应该没有人会用吧
 * @param context 上下文对象
 * @param any 需要 Toast 的东西，会调用它的 toString() 方法
 * @param duration （可选）时间长度，默认为 Toast.LENGTH_SHORT
 * @return 返回 Toast 对象
 */
@JvmName("toast1")
fun toast(context: Context, any: Any, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(context, any.toString(), duration).apply { show() }