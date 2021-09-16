package com.gx.task.di.computer

import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Computer {
    @Inject
    lateinit var cpu: CPU

    @Inject
    lateinit var disk: Disk

    @Inject
    lateinit var mainBoard: MainBoard

    var phoneName = "Mac"

    init {
        DaggerComputerComponent.builder().mainBoardModule(MainBoardModule()).build().inject(this)
        Log.e("Computer cpu",cpu.getCpuName())
        Log.e("Computer disk" ,disk.getDiskName())
        Log.e("Computer mainBoard",mainBoard.getMainBoardName())
    }
}