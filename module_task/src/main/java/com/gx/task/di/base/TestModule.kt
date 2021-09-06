package com.gx.task.di.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.gx.module_task.R
import com.gx.task.di.demo.House
import com.gx.task.di.demo.Phone
import dagger.Module
import dagger.Provides

@Module
class TestModule constructor(var context: Context){

    @Provides
    fun house(): House {
        return House(Phone())
    }
}