package com.gx.task.di.subcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R

class LoginActivity : AppCompatActivity() {

    lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        loginComponent = DaggerApplicationComponent.create().loginComponent().create()
        loginComponent.inject(this)
//        loginComponent = (applicationContext as MyDaggerApplication)
//            .appComponent.loginComponent().create()
    }
}