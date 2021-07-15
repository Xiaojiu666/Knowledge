package com.gx.module_task.repository

import androidx.lifecycle.ViewModel
import com.gx.module_task.details.TaskDetailViewModel

class LoginViewModelFactory(private val userRepository: TaskRepository) :

    ViewModelFactory<TaskDetailViewModel> {
    override fun create(): TaskDetailViewModel {
        return TaskDetailViewModel(userRepository)
    }
}