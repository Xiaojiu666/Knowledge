package com.gx.module_task.di.base

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasePresenter @Inject constructor() {

    fun getData(): String {
        return "BasePresenter"
    }
}