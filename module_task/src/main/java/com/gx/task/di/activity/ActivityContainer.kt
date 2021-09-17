package com.gx.task.di.activity

import com.gx.task.ui.activities.TaskActivity

//@Component
interface ActivityContainer{
    fun inject(taskActivity: TaskActivity)
}