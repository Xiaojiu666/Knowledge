package com.gx.module_task.di

import com.gx.module_task.TaskActivity
import dagger.Component

@Component
interface TestContainer {
//    fun repository(): TestRepository

    fun inject(taskActivity: TaskActivity)
}