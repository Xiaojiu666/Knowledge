package com.gx.task.di.computer

import javax.inject.Inject

class Electric @Inject constructor() {
    fun getElectric() = "650V"
}