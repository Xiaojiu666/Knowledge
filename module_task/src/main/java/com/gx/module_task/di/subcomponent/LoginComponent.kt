package com.gx.module_task.di.subcomponent

import com.gx.module_task.di.base.BaseActivity
import dagger.Subcomponent

@Subcomponent
interface LoginComponent {
    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }


    fun inject(loginActivity: BaseActivity)

}