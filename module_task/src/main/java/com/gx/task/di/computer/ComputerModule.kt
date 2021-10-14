package com.gx.task.di.computer

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ComputerModule {
    @Provides
    fun provideMainBoard(electric: Electric): MainBoard {
        return MainBoard.create(electric)!!
    }

    @Singleton
    @Provides
    fun provideCPU(): CPU {
        return CPU()
    }
}