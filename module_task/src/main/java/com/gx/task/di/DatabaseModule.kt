package com.gx.task.di

import android.content.Context
import com.gx.task.di.demo.Phone
import com.gx.task.model.room.TaskDao
import com.gx.task.model.room.TaskDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(var context: Context) {

    @Provides
    fun provideTaskDatabase():TaskDatabase{
        return TaskDatabase.getInstance(context)!!
    }

    @Provides
    fun provideTaskTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()!!
    }

    @Provides
    fun providePhone(): Phone {
        return Phone()
    }
}