package com.gx.task.di.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.gx.module_task.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModule constructor(var context: Context){

    @Provides
    @Singleton
    fun getView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_task_home, null)
    }
}