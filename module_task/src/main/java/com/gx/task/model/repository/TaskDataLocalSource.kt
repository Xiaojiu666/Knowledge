package com.gx.task.model.repository

import com.gx.task.model.data.TaskDetailInfo
import com.gx.task.model.data.TaskListInfo

class TaskDataLocalSource : TaskDataSource {
    override fun getTaskList() = ArrayList<TaskListInfo>()

    override fun getTaskDetails() = TaskDetailInfo(1)
}