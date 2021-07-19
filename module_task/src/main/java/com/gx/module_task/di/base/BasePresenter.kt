package com.gx.module_task.di.base

import javax.inject.Inject

class BasePresenter @Inject constructor() {

    fun getData(): String {
        return "BasePresenter"
    }
}