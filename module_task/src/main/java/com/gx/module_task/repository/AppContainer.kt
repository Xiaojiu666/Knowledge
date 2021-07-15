package com.gx.module_task.repository

import com.gx.module_task.details.TaskDetailViewModel

class AppContainer {
    private val remoteDataSource = TaskRepository.TaskRemoteDataSource()
    private val localDataSource = TaskRepository.TaskLocalDataSource()
    private val taskRepository = TaskRepository(localDataSource, remoteDataSource)
    val taskDetailViewModel = LoginViewModelFactory(taskRepository)
}