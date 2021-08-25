package com.gx.task

import android.content.Context
import com.gx.task.repository.AppContainer
import com.gx.base.application.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApplication : BaseApplication() {

    val appContainer = AppContainer()

    override fun initRoom(application: Context) {}

    override fun initBasePlugin() {
        super.initBasePlugin()
        val appContainer = AppContainer()
    }
}