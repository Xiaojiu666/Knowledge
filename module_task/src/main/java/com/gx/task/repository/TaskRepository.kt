package com.gx.task.repository

class TaskRepository(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource
) {

    class TaskLocalDataSource() {

    }

    class TaskRemoteDataSource() {
    }

}