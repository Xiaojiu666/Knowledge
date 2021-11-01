package com.gx.task.di.subcomponent

import android.view.View
import com.gx.task.di.demo.Clothes
import com.gx.task.di.demo.House
import dagger.Module
import dagger.Provides

@Module(subcomponents = [LoginComponent::class])
class SubcomponentsModule {

    @Provides
    fun provideClothes(): Clothes {
        return Clothes()
    }


    @Provides
    fun provideHouse(view: View): House {
        return House(view)
    }

}