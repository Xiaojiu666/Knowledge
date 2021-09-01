package com.gx.task.di.base

import com.gx.task.TaskActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [BaseModule::class])
interface BaseContainer {
    fun inject(baseActivity: BaseActivity)
}