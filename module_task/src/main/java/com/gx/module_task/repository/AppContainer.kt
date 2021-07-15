package com.gx.module_task.repository

class AppContainer {
    private val remoteDataSource = TaskRepository.TaskRemoteDataSource()
    private val localDataSource = TaskRepository.TaskLocalDataSource()
    private val taskRepository = TaskRepository(localDataSource, remoteDataSource)
    val taskDetailViewModel = TaskViewModelFactory(taskRepository)
}