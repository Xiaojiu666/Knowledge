package com.gx.task.di.demo

import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Person {

    @Inject
    @JvmField
    public var phone: Phone? = null

    @Inject
    @JvmField
    public var computer: Computer? = null

    init {
        DaggerPersonContainer.create().inject(this)
    }

}