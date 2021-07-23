package com.gx.module_task.repository


interface ViewModelFactory<T> {
    fun create(): T
}
