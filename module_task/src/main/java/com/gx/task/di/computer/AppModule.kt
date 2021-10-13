package com.gx.task.di.computer

import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideElectric() = Electric()

    @Provides
    fun provideOtherPart()  = OtherPart()
}