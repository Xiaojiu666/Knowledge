package com.gx.task.model.repository

import com.gx.task.model.data.TaskDetailInfo
import com.gx.task.model.data.TaskListInfo

interface TaskDataSource {
    fun getTaskList() : List<TaskListInfo>

    fun getTaskDetails(): TaskDetailInfo
}