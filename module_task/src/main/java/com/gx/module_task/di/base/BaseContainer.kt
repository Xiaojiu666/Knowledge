package com.gx.module_task.di.base

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BaseModule::class])
interface BaseContainer {
    fun inject(baseActivity: BaseActivity)
}