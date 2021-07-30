package com.gx.task.model.data

data class TaskListInfo(var taskId: Int) {
    var taskTitle: String? = null
    var taskStartTime: Long? = 0L
    var taskEndTime: Long? = 0L
    var taskListTotalSize: Int = 0
    var taskListCompleteSize: Int = 0
}