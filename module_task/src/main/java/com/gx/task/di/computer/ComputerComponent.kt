package com.gx.task.di.computer

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PartsModule::class], dependencies = [AppComponent::class])
interface ComputerComponent {
    fun inject(computer: Computer)
}