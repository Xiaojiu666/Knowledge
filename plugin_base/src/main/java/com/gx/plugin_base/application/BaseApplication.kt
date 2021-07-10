package com.gx.plugin_base.application

import android.app.Application
import android.content.Context
import android.os.Environment
import com.alibaba.android.arouter.launcher.ARouter
import com.gx.plugin_base.config.AppFileConfig
import com.gx.plugin_common.BuildConfig
import com.gx.utils.apk.AppInfoUtil
import com.gx.utils.log.LogUtil

abstract class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initBasePlugin()
        initRoom(applicationContext)
        initARouter()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG){
            //openDebug 必须在init之前，他决定是否从缓存中读取数据
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this@BaseApplication)
    }

    abstract fun initRoom(application: Context)

    private fun initBasePlugin(){
        initFileRootConfig();
        initLogUtil()
        LogUtil.e("BaseApplication onCreate")
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