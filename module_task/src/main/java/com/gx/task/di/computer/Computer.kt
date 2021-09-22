package com.gx.task.di.computer

import android.content.Context
import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Computer(var context: Context) {
    @Inject
    lateinit var cpu: CPU

    @Inject
    lateinit var disk: Disk

    @Inject
    lateinit var mainBoard: MainBoard

    var phoneName = "Mac"

    init {
//        DaggerComputerComponent.builder().computerModule(PartsModule(context)).build().inject(this)
//        Log.e("Computer cpu",cpu.getCpuName())
        Log.e("Computer disk" ,disk.getDiskName())
//        Log.e("Computer mainBoard",mainBoard.getMainBoardName())
    }
}