package com.gx.module_task.di.base

import dagger.Component

@Component(modules = [BaseModule::class])
interface BaseContainer {
    fun inject(baseActivity: BaseActivity)
}