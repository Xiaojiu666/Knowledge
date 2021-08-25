package com.gx.task.model.data

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = Task.TASK_TABLE_NAME)
data class Task(@PrimaryKey @ColumnInfo(name = TASK_TABLE_PRIMARY_KEY) var taskId: Int) {

    companion object {
        //-----------ROOM-------------
        const val TASK_TABLE_NAME = "task"
        const val TASK_TABLE_PRIMARY_KEY = "id"

    }

    var taskName: String? = null
    var taskStartTime: Long? = 0L
    var taskEndTime: Long? = 0L
    var taskListTotalSize: Int = 0
    var taskListCompleteSize: Int = 0
    // 0 进行中 1 已完成 2 延期
    var taskListStatus: Int = 0


    override fun toString(): String {
        return "Task(taskId=$taskId, taskName=$taskName, taskStartTime=$taskStartTime, taskEndTime=$taskEndTime, taskListTotalSize=$taskListTotalSize, taskListCompleteSize=$taskListCompleteSize, taskListStatus=$taskListStatus)"
    }


}