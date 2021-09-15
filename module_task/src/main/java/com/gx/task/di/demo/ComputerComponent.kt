package com.gx.task.di.demo

import dagger.Component

@Component
interface ComputerComponent {
    fun inject(computer: Computer)
}