package com.sn.plugin_commin.System

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by GuoXu on 2020/10/13 19:48.
 */
object NetWorkUtil {

    fun isNetworkAvailable(mContext: Context): Boolean {
        val connectivityManager = mContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val info = connectivityManager.activeNetworkInfo as NetworkInfo
            if (info != null && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}