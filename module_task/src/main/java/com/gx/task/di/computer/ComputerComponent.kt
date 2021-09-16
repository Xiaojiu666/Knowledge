package com.gx.task.di.computer

import com.gx.task.di.computer.Computer
import dagger.Component

@Component(modules = [MainBoardModule::class])
interface ComputerComponent {
    fun inject(computer: Computer)
}