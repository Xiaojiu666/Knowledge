package com.gx.task.repository

import com.gx.task.model.data.TaskDetailInfo
import com.gx.task.model.data.Task

class TaskDataLocalSource : TaskDataSource {
    override fun getTaskList() = ArrayList<Task>()

    override fun getTaskDetails() = TaskDetailInfo(1)
}