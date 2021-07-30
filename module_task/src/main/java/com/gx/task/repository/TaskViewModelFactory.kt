package com.gx.task.repository

import com.gx.task.details.TaskDetailViewModel

class TaskViewModelFactory(private val userRepository: TaskRepository) :
    ViewModelFactory<TaskDetailViewModel> {
    override fun create(): TaskDetailViewModel {
        return TaskDetailViewModel(userRepository)
    }
}