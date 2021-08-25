package com.gx.task.model.data

data class SubTask(var subTaskId: Int) {
    var subTaskListStatus: TaskStatus = TaskStatus.IN_PROGRESS
    var subContent: String? = null
    var parentId = 0
}