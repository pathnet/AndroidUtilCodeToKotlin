package com.pape.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.File

/**
 * Created by zhangzhanyong on 2018/4/19.
 */
fun Context.getInstallAppIntent(file: File?,
                                authority: String,
                                isNewTask: Boolean): Intent? {
    if (file == null) return null
    val intent = Intent(Intent.ACTION_VIEW)
    val data: Uri
    val type = "application/vnd.android.package-archive"
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        data = Uri.fromFile(file)
    } else {
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        data = FileProvider.getUriForFile(this.applicationContext, authority, file)
    }
    intent.setDataAndType(data, type)
    return getIntent(intent, isNewTask)
}

private fun getIntent(intent: Intent, isNewTask: Boolean): Intent {
    return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
}
