package com.gx.task.di.subcomponent

import com.gx.task.di.base.BaseActivity
import dagger.Subcomponent


interface LoginComponent {
    // Factory that is used to create instances of this subcomponent
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(loginActivity: BaseActivity)

}