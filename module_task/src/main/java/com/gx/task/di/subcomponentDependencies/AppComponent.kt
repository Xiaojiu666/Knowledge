package com.gx.task.di.subcomponentDependencies

import com.gx.task.di.DatabaseModule
import com.gx.task.di.base.BaseActivity
import com.gx.task.model.room.TaskDatabase
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
//    fun provideTaskDatabase(): TaskDatabase
}