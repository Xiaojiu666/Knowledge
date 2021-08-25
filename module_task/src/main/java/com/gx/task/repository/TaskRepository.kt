package com.gx.task.repository

import com.gx.task.model.data.Task
import com.gx.task.model.room.TaskDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    var localDataSource: TaskLocalDataSource,
    var remoteDataSource: TaskRemoteDataSource
) {

    fun getTaskList() = localDataSource.getTaskList()

    fun createTask(task: Task) = localDataSource.createTask(task)

    fun createTasks(task: MutableList<Task>) = localDataSource.createTasks(task)

    class TaskLocalDataSource @Inject constructor(private var taskDao: TaskDao) {
        fun getTaskList() = taskDao.getAllTaskData()

        fun createTask(task: Task) = taskDao.insert(task)

        fun createTasks(task: MutableList<Task>) =taskDao.insertAll(task)

    }

    class TaskRemoteDataSource@Inject constructor(){

    }

}