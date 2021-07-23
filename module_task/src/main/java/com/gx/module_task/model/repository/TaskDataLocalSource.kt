package com.gx.module_task.model.repository

import com.gx.module_task.model.data.TaskDetailInfo
import com.gx.module_task.model.data.TaskListInfo

class TaskDataLocalSource : TaskDataSource {
    override fun getTaskList() = ArrayList<TaskListInfo>()

    override fun getTaskDetails() = TaskDetailInfo(1)
}