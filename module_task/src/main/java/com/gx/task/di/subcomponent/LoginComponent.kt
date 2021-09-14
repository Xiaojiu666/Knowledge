package com.gx.task.di.subcomponent

import com.gx.task.di.base.BaseActivity
import dagger.Subcomponent

@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

//    fun inject(loginActivity: BaseActivity)

}