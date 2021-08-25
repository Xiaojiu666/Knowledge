package com.gx.task.repository

import com.gx.task.vm.TaskDetailViewModel

class TaskViewModelFactory(private val userRepository: TaskRepository) :
    ViewModelFactory<TaskDetailViewModel> {

    override fun create(): TaskDetailViewModel {
        return TaskDetailViewModel(userRepository)
    }
}