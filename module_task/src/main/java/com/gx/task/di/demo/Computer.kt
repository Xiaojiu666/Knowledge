package com.gx.task.di.demo

import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Computer {
    @Inject
    lateinit var cpu: CPU

    @Inject
    lateinit var disk: Disk

    var phoneName = "Mac"

    init {
        DaggerComputerComponent.create().inject(this)
    }
}