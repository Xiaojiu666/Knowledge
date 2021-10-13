package com.gx.task.di.computer

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CPU @Inject constructor()  {

    fun getCpuName() = "I9-10900X"

}