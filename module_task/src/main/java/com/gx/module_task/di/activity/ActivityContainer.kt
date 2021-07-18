package com.gx.module_task.di.activity

import com.gx.module_task.TaskActivity
import dagger.Component

//@Component
interface ActivityContainer{
    fun inject(taskActivity: TaskActivity)
}