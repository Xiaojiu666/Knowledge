package com.gx.module_task.di.base

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import com.gx.module_task.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule constructor(var context: Context){

    @Singleton
    @Provides
    fun getView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_task_home, null)
    }
}