package com.gx.task.repository

import com.gx.task.model.data.TaskDetailInfo
import com.gx.task.model.data.Task

interface TaskDataSource {
    fun getTaskList() : List<Task>

    fun getTaskDetails(): TaskDetailInfo
}