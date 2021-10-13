package com.gx.task.di.computer

import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun provideElectric(): Electric
}