package com.gx.task.di.demo

import com.tencent.mars.xlog.Log
import javax.inject.Inject

class Person {

    @Inject
    @JvmField
    var phone: Phone? = null

    @Inject
    @JvmField
    var clothes: Clothes? = null

    @Inject
    @JvmField
    var house: House? = null

    init {
        DaggerPersonContainer.create().inject(this)
    }

}