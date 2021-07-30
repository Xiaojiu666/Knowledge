package com.gx.task

import com.gx.task.dummy.DummyContent
import com.gx.task.model.data.TaskListInfo
import java.util.ArrayList


val ITEMS: MutableList<TaskListInfo> = ArrayList()

fun getTaskData(): MutableList<TaskListInfo> {
    for (i in 1..20) {
        val taskListInfo = TaskListInfo(i)
        taskListInfo.taskTitle = "第 $i 个任务"
        ITEMS.add(taskListInfo)
    }
    return ITEMS
}