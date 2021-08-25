package com.gx.task

import com.gx.task.model.data.Task
import java.util.ArrayList


val ITEMS: MutableList<Task> = ArrayList()

fun getTaskData(): MutableList<Task> {
    for (i in 1..20) {
        val taskListInfo = Task(i)
        taskListInfo.taskName = "第 $i 个任务"
        ITEMS.add(taskListInfo)
    }
    return ITEMS
}