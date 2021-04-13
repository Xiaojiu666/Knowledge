package com.sn.plugin_base.application

import android.app.Application
import android.content.Context

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRoom(applicationContext)
    }

    abstract fun initRoom(application: Context)


}