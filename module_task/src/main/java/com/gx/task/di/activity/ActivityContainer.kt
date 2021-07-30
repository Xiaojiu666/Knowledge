package com.gx.task.di.activity

import com.gx.task.TaskActivity

//@Component
interface ActivityContainer{
    fun inject(taskActivity: TaskActivity)
}