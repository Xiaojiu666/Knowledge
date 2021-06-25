package com.sn.utils.apk

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

object AppInfoUtil {

    fun getVersionName(context: Context): String {
        return getPackInfo(context).versionName
    }

    fun getVersionCode(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getPackInfo(context).longVersionCode.toString()
        } else {
            getPackInfo(context).versionCode.toString()
        }
    }


    fun getAppName(context: Context): String {
        val labelRes = getPackInfo(context).applicationInfo.labelRes
        return context.resources.getString(labelRes)
    }

    private fun getPackInfo(context: Context): PackageInfo {
        val packageManager = context.packageManager
        return packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_CONFIGURATIONS
        )
    }
}