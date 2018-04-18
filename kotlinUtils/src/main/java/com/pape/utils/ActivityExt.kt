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
import android.os.Build
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityOptionsCompat
import android.view.View

/**
 * Created by zzy on 2018/4/17.
 */
/**
 * 判断activity是否存在
 */
fun Context.isActivityExists(pkg: String, cls: String): Boolean {
    val intent = Intent().also { it.setClassName(pkg, cls) }
    return !(this.packageManager.resolveActivity(intent, 0) == null ||
            intent.resolveActivity(this.packageManager) == null ||
            this.packageManager.queryIntentActivities(intent, 0).size == 0)
}

/**
 * 回到桌面
 */
fun Context.startHomeActivity() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    this.startActivity(homeIntent)
}

/**
 * TODO
 *获取 Activity 栈链表
 */
fun getActivityList(): List<Activity> {
    return emptyList()
}

/**
 * TODO
 * 获取启动项 Activity
 */
fun getLauncherActivity(pkg: String? = null): String {
    return ""
}

/**
 * TODO
 * 获取栈顶 Activity
 */
fun getTopActivity(): Activity? {

    return null
}

/**
 * TODO
 * 判断 Activity 是否存在栈中
 */
fun isActivityExistsInStack(activity: Activity): Boolean {
    return false
}

/**
 * TODO
 * 判断 Activity 是否存在栈中
 */
fun <T> isActivityExistsInStack(cls: Class<T>): Boolean {
    return false
}

fun Activity.finishActivity(isLoadAnim: Boolean) {
    this.finish()
    if (!isLoadAnim) {
        this.overridePendingTransition(0, 0)
    }
}

fun Activity.finishActivity(@AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
    this.finish()
    this.overridePendingTransition(enterAnim, exitAnim)
}

/**
 * 关闭指定Activity 是否有加载动画
 */
fun <T> finishActivity(cls: Class<T>, isLoadAnim: Boolean = false) {
    val list = getActivityList()
    list.filter { it.javaClass == cls }.forEach {
        it.finishActivity(isLoadAnim)
    }
}

/**
 * 关闭指定Activity 是否有加载动画
 */
fun <T> finishOtherActivity(cls: Class<T>, isIncludeSelf: Boolean = false, isLoadAnim: Boolean = false) {
    val list = getActivityList()
    list.forEach {
        if (it.javaClass == cls) {
            if (isIncludeSelf) {
                it.finishActivity(isLoadAnim)
            }
        } else {
            it.finishActivity(isLoadAnim)
        }
    }
}

/**
 * 关闭指定Activity 并设置动画
 */
fun <T> finishActivity(cls: Class<T>, @AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
    val list = getActivityList()
    list.filter { it.javaClass == cls }.forEach {
        it.finishActivity(enterAnim, exitAnim)
    }
}

/**
 * 结束所有其他类型的 Activity
 */
fun <T> finishOtherActivity(cls: Class<T>, isIncludeSelf: Boolean = false, @AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
    val list = getActivityList()
    list.forEach {
        if (it.javaClass == cls) {
            if (isIncludeSelf) {
                it.finishActivity(enterAnim, exitAnim)
            }
        } else {
            it.finishActivity(enterAnim, exitAnim)
        }
    }
}

/**
 * 结束所有其他类型的 Activity
 */
fun Activity.finishOtherActivity(isIncludeSelf: Boolean = false, isLoadAnim: Boolean = false) {
    val list = getActivityList()
    list.forEach {
        if (it.javaClass == this.javaClass) {
            if (isIncludeSelf) {
                finishActivity(isLoadAnim)
            }
        } else {
            finishActivity(isLoadAnim)
        }
    }
}

/**
 * 结束所有 Activity
 */
fun finishAllActivities() {
    getActivityList().forEach {
        it.finish()
    }
}

/**
 * 结束除最新之外的所有 Activity
 */
fun finishAllActivitiesExceptNewest() {
    val list = getActivityList()
    list.toMutableList().remove(list.last())
    list.forEach {
        it.finish()
    }
}

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递
 * @param option Additional options for how the Activity should be started.
 * May be null if there are no options.  See {@link android.app.ActivityOptions}
 */
inline fun <reified T : Activity> Context.startActivity(option: Bundle? = null, vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(this, T::class.java, params, option)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递
 */
inline fun <reified T : Activity> Fragment.startActivity(option: Bundle? = null, vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(activity, T::class.java, params, option)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递，有返回值
 */
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int = 0, option: Bundle? = null, vararg params: Pair<String, Any?>) =
        Internals.internalStartActivityForResult(this, T::class.java, requestCode, params, option)

/**
 * 启动目标Activity，并传入相应参数，参数以map形式传递，有返回值
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int = 0, option: Bundle? = null, vararg params: Pair<String, Any?>) =
        startActivityForResult(Internals.createIntent(activity, T::class.java, params), requestCode, option)

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
 * 创建Intent
 * @param pkg The name of the package.
 * @param cls The name of the class.
 */
fun Context.intentFor(pkg: String, cls: String, vararg params: Pair<String, Any?>) =
        Internals.createIntent(pkg, cls, params)

/**
 * 创建Intent
 * @param pkg The name of the package.
 * @param cls The name of the class.
 */
fun Fragment.intentFor(pkg: String, cls: String, vararg params: Pair<String, Any?>) =
        Internals.createIntent(pkg, cls, params)

/**
 * 启动Activity时，清除之前已经存在的Activity实例所在的task；这自然也就清除了之前存在的Activity实例！
 */
fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

/**
 * 启动Activity时 清除"包含Activity的task"中位于该Activity实例之上的其他Activity实例
 */
fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

/**
 * 如果设置这个标志，这个Activity就不会在近期任务中显示
 */
fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

/**
 * 这个标识用来创建一个新的task栈，并且在里面启动新的activity（所有情况，不管系统中存在不存在该activity实例）
 */
fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

/**
 * 这个是最常用的，但是往往会被误解，在程序根Activity的Task栈里加此标识开启新Activity都不会创建新的Task，
 * 只有当另一程序（进程）启动带有改标识的Activity时，才会创建新的Task。如果配合FLAG_ACTIVITY_NEW_MULTI_TASK，
 * 则无论什么情况都会创建新的Task，就成了类似 singleInstance 的情况了（singleInstance中的Activity独占一个栈）
 * 如果配合 FLAG_ACTIVITY_CLEAR_TASK，则会先清空该栈，然后向栈中添加目标Activity，栈ID不变
 */
fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

/**
 * 禁用掉系统默认的Activity切换动画
 */
fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * 用这个FLAG启动的Activity，一但退出，就不会存在于栈中
 */
fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

/**
 * 同singleTop启动模式，比如说原来栈中情况是A,B,C,D在D中启动D，栈中的情况还是A,B,C,D。
 */
fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

/**
 * 5.0以上版本支持
 * 实现共享元素的动画
 */
fun Activity.getOptionsBundle(sharedElements: Array<View>): Bundle? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val len = sharedElements.size
        val pairs = arrayOfNulls<android.support.v4.util.Pair<View, String>>(len)
        for (i in 0 until len) {
            pairs[i] = android.support.v4.util.Pair.create(sharedElements[i], sharedElements[i].transitionName)
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs).toBundle()
    }
    return null
}

/**
 * 实现跳转动画
 */
fun Context.getOptionsBundle(enterAnim: Int? = null, exitAnim: Int? = null) = enterAnim?.let { exitAnim?.let { it1 -> ActivityOptionsCompat.makeCustomAnimation(this, it, it1).toBundle() } }
