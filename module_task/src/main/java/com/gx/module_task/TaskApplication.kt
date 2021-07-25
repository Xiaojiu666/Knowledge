package com.gx.module_task

import android.content.Context
import com.gx.module_task.repository.AppContainer
import com.gx.base.application.BaseApplication

class TaskApplication : BaseApplication() {

    val appContainer = AppContainer()

    override fun initRoom(application: Context) {}

    override fun initBasePlugin() {
        super.initBasePlugin()
        val appContainer = AppContainer()

    }
}