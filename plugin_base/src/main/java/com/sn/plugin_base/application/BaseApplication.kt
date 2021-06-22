package com.sn.plugin_base.application

import android.app.Application
import android.content.Context
import android.os.Environment
import com.sn.plugin_common.utils.log.LogUtil

abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initRoom(applicationContext)
        initBasePlugin(applicationContext)
    }

    abstract fun initRoom(application: Context)

    private fun initBasePlugin(applicationContext: Context){
        val s = Environment.getExternalStorageDirectory()
            .absolutePath + "/" + "AccountBook/cache";
        val s1 = Environment.getExternalStorageDirectory()
            .absolutePath + "/" + "AccountBook/";
        LogUtil.initLog(s,s1)
    }
}