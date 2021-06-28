package com.sn.plugin_base.application

import android.app.Application
import android.content.Context
import android.os.Environment
import com.alibaba.android.arouter.launcher.ARouter
import com.sn.config.AppFileConfig
import com.sn.utils.apk.AppInfoUtil
import com.sn.utils.log.LogUtil

abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initRoom(applicationContext)
        initBasePlugin()
        initARouter()
    }

    private fun initARouter() {
        ARouter.init(this@BaseApplication)
    }

    abstract fun initRoom(application: Context)

    private fun initBasePlugin(){
        initFileRootConfig();
        initLogUtil()
    }

    private fun initFileRootConfig() {
        val rootPath  = Environment.getExternalStorageDirectory().absolutePath + "/" + AppInfoUtil.getAppName(applicationContext)
        AppFileConfig.initRootPath(rootPath)
    }

    private fun initLogUtil() {
        LogUtil.initLog(AppFileConfig.FILE_XLOG, AppFileConfig.FILE_XLOG_CACHE)
        LogUtil.printHeader(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}