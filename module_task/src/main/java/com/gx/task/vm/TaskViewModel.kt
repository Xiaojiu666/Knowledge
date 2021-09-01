package com.gx.task.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gx.task.model.data.Task
import com.gx.task.repository.TaskRepository
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    var taskRepository: TaskRepository
) : ViewModel() {

    var name = "TaskViewModel"
    val tasks: LiveData<MutableList<Task>> = taskRepository.getTaskList().asLiveData()

}