package com.gx.task.di

import android.content.Context
import com.gx.task.model.room.TaskDao
import com.gx.task.model.room.TaskDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
//@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTaskDatabase( context: Context):TaskDatabase{
        return TaskDatabase.getInstance(context)!!
    }
    @Provides
    fun providePlantDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()!!
    }


}