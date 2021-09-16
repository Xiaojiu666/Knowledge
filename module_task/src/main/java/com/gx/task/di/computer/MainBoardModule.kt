package com.gx.task.di.computer

import dagger.Module
import dagger.Provides

@Module
class MainBoardModule {

    @Provides
    fun provideMainBoard(): MainBoard {
        return MainBoard.create()!!
    }
}