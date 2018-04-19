package com.pape.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import java.io.File

/**
 * Created by zzy on 2018/4/19.
 */
/**
 *{@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
 * @param filePath  The path of file.
 * @param authority Target APIs greater than 23 must hold the authority of a FileProvider
 *                  defined in a {@code <provider>} element in your app's manifest.
 */
fun Context.installApp(filePath: String, authority: String, requestCode: Int? = null) {
    val file = File(filePath)
    if (file.exists() && requestCode == null)
        startActivity(getInstallAppIntent(file, authority, true))
    else if (this is Activity && requestCode != null) {
        startActivityForResult(getInstallAppIntent(file, authority, true), requestCode)
    } else {
        Log.e("installApp", "installApp error file.exists:${file.exists()} requestCode:${requestCode}")
    }
}

fun Context.installAppSilent(filePath: String){

}