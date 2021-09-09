package com.gx.task.di.subcomponent

import com.gx.task.di.demo.Clothes
import dagger.Module
import dagger.Provides

@Module(subcomponents = [LoginComponent::class])
class SubcomponentsModule {

    @Provides
    fun provideClothes(): Clothes {
        return Clothes()
    }
}