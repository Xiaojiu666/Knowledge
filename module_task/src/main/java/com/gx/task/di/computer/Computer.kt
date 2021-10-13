package com.gx.task.di.computer

import android.content.Context
import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Computer(var context: Context) {

    val TAG = "Computer"
    @Inject
    lateinit var cpu: CPU

    @Inject
    lateinit var cpu1: CPU

    @Inject
    lateinit var electric: Electric

    @Inject
    lateinit var otherPart: OtherPart

    @Inject
    lateinit var mainBoard: MainBoard

    init {
        val appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()

        DaggerComputerComponent.builder().appComponent(appComponent).partsModule(PartsModule())
            .build().inject(this)
        Log.e(TAG , cpu.toString())
        Log.e(TAG , cpu1.toString())
        Log.e(TAG , electric.toString())
        Log.e(TAG , otherPart.toString())
        Log.e(TAG , mainBoard.toString())

    }
}