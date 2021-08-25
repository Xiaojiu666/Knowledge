package com.gx.task.di.base

import dagger.Component
import javax.inject.Singleton

//@Component()
interface BaseContainer {
    fun inject(baseActivity: BaseActivity)
}