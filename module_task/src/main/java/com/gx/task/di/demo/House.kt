package com.gx.task.di.demo

import android.view.View
import javax.inject.Inject

class House @Inject constructor(var view: View) {
    fun getView():String = view.toString()
}