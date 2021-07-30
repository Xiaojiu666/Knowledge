package com.gx.task.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gx.task.repository.TaskRepository

class TaskDetailViewModel(val taskRepository: TaskRepository) : ViewModel() {

    val taskDetail: MutableLiveData<TaskDetailInfo> by lazy {
        MutableLiveData<TaskDetailInfo>()
    }

    fun upDataTaskDetailInfo() {
        taskDetail.value = TaskDetailInfo(1)
    }
}

