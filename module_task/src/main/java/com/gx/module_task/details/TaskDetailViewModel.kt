package com.gx.module_task.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskDetailViewModel : ViewModel() {

    val taskDetail: MutableLiveData<TaskDetailInfo> by lazy {
        MutableLiveData<TaskDetailInfo>()
    }
}

