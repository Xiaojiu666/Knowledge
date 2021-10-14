package com.gx.task.di.computer

import com.gx.task.di.base.BaseActivity
import dagger.Component

@Component(modules = [PartsModule::class], dependencies = [AppComponent::class])
interface PartsComponent {

    fun inject(computer: Computer)
}