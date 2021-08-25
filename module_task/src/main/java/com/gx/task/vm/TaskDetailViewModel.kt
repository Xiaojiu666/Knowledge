package com.gx.task.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gx.task.details.TaskDetailInfo
import com.gx.task.repository.TaskRepository
import javax.inject.Inject

class TaskDetailViewModel @Inject constructor(val taskRepository: TaskRepository) : ViewModel() {

    val taskDetail: MutableLiveData<TaskDetailInfo> by lazy {
        MutableLiveData<TaskDetailInfo>()
    }

    fun upDataTaskDetailInfo() {
        taskDetail.value = TaskDetailInfo(1)
    }
}

