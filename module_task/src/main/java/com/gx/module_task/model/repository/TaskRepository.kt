package com.gx.module_task.model.repository

import com.gx.module_task.model.data.TaskDetailInfo
import com.gx.module_task.model.data.TaskListInfo

class TaskRepository : TaskDataSource {

    override fun getTaskList(): List<TaskListInfo> {
        TODO("Not yet implemented")
    }

    override fun getTaskDetails(): TaskDetailInfo {
        TODO("Not yet implemented")
    }
}