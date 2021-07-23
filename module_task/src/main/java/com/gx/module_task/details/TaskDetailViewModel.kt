package com.gx.module_task.details

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gx.module_task.repository.TaskRepository

class TaskDetailViewModel(val taskRepository: TaskRepository) : ViewModel() {

    val taskDetail: MutableLiveData<TaskDetailInfo> by lazy {
        MutableLiveData<TaskDetailInfo>()
    }

    fun upDataTaskDetailInfo() {
        taskDetail.value = TaskDetailInfo(1)
    }
}

