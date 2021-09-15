package com.gx.task.di.demo

import javax.inject.Inject

class Disk @Inject constructor() {
    fun getDiskName() = "SAMSUNG"
}