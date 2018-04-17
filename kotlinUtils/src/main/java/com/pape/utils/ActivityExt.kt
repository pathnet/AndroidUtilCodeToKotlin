package com.pape.utils

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import android.content.ComponentName

/**
 * Created by zzy on 2018/4/17.
 */
/**
 * TODO
 * 判断activity是否存在
 */
fun isActivityExists(pkg: String, cls: String): Boolean {
    return true
}

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(this, T::class.java, params)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(activity, T::class.java, params)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递，有返回值
 */
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) =
        Internals.internalStartActivityForResult(this, T::class.java, requestCode, params)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递，有返回值
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) =
        startActivityForResult(Internals.createIntent(activity, T::class.java, params), requestCode)

/**
 * 启动目标服务，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Service> Context.startService(vararg params: Pair<String, Any?>) =
        Internals.internalStartService(this, T::class.java, params)

/**
 * 启动目标服务，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Service> Fragment.startService(vararg params: Pair<String, Any?>) =
        Internals.internalStartService(activity, T::class.java, params)

/**
 * 停止目标服务，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Service> Context.stopService(vararg params: Pair<String, Any?>) =
        Internals.internalStopService(this, T::class.java, params)

/**
 * 停止目标服务，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Service> Fragment.stopService(vararg params: Pair<String, Any?>) =
        Internals.internalStopService(activity, T::class.java, params)

/**
 * 创建Intent
 */
inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
        Internals.createIntent(this, T::class.java, params)

/**
 * 创建Intent
 */
inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent =
        Internals.createIntent(activity, T::class.java, params)

/**
 * 启动Activity时，清除之前已经存在的Activity实例所在的task；这自然也就清除了之前存在的Activity实例！
 */
inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

/**
 * 启动Activity时 清除"包含Activity的task"中位于该Activity实例之上的其他Activity实例
 */
inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

/**
 * 如果设置这个标志，这个Activity就不会在近期任务中显示
 */
inline fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

/**
 * 这个标识用来创建一个新的task栈，并且在里面启动新的activity（所有情况，不管系统中存在不存在该activity实例）
 */
inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

/**
 * 这个是最常用的，但是往往会被误解，在程序根Activity的Task栈里加此标识开启新Activity都不会创建新的Task，
 * 只有当另一程序（进程）启动带有改标识的Activity时，才会创建新的Task。如果配合FLAG_ACTIVITY_NEW_MULTI_TASK，
 * 则无论什么情况都会创建新的Task，就成了类似 singleInstance 的情况了（singleInstance中的Activity独占一个栈）
 * 如果配合 FLAG_ACTIVITY_CLEAR_TASK，则会先清空该栈，然后向栈中添加目标Activity，栈ID不变
 */
inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

/**
 * 禁用掉系统默认的Activity切换动画
 */
inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * 用这个FLAG启动的Activity，一但退出，就不会存在于栈中
 */
inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

/**
 * 同singleTop启动模式，比如说原来栈中情况是A,B,C,D在D中启动D，栈中的情况还是A,B,C,D。
 */
inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }


