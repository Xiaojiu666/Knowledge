package com.gx.task.di.subcomponentDependencies

import android.media.Image
import android.widget.ImageView
import dagger.Module
import dagger.Provides
import java.sql.Time

@Module
class AppModule {

    @Provides
    fun provideAppInfo() = AppInfo()

    @Provides
    fun provideTimeInfo() = TimeInfo()

}