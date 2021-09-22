package com.gx.task.di.computer

import dagger.Component

@Component(modules = [PartsModule::class])
interface ComputerComponent {
    fun inject(computer: Computer)
}