package com.gx.task.di.base

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModule::class, TestModule::class])
interface BaseContainer {
    fun inject(baseActivity: BaseActivity)
}