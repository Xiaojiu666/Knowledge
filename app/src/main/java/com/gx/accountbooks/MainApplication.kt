package com.gx.accountbooks

import android.content.Context
import com.gx.base.application.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : BaseApplication() {

    override fun initRoom(application: Context) {
    }
}