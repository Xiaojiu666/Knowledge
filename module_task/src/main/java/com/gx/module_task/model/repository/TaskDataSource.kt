package com.gx.module_task.model.repository

import com.gx.module_task.model.data.TaskDetailInfo
import com.gx.module_task.model.data.TaskListInfo

interface TaskDataSource {
    fun getTaskList() : List<TaskListInfo>

    fun getTaskDetails(): TaskDetailInfo
}