package com.gx.task.di.subcomponentDependencies

import dagger.Component

@Component(dependencies = [AppComponent::class])
interface AppComponent {

}