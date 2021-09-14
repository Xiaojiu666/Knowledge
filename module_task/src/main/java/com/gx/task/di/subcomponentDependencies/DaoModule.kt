package com.gx.task.di.subcomponentDependencies

import android.content.Context
import com.gx.task.di.demo.Phone
import com.gx.task.model.room.TaskDao
import com.gx.task.model.room.TaskDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule {


    @Provides
    fun provideTaskTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()!!
    }
//
//    @Provides
//    fun provideTaskDatabase(taskDatabase: TaskDatabase): TaskDao {
//        return taskDatabase.taskDao()!!
//    }

//    @Provides
//    fun provideTaskDatabase(taskDatabase: TaskDatabase): Phone {
//        return taskDatabase.()!!
//    }

}