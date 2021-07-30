package com.gx.task.repository


interface ViewModelFactory<T> {
    fun create(): T
}
