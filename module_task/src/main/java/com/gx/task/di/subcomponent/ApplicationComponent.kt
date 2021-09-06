package com.gx.task.di.subcomponent

import com.gx.task.di.base.ViewModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    // This function exposes the LoginComponent Factory out of the graph so consumers
    // can use it to obtain new instances of LoginComponent
    fun loginComponent(): LoginComponent.Factory
}