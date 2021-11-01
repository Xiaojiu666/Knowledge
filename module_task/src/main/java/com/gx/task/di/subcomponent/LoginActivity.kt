package com.gx.task.di.subcomponent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R
import com.gx.task.di.base.ViewModule
import com.gx.task.di.demo.Clothes
import com.gx.task.di.demo.House
import com.gx.utils.log.LogUtil
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    lateinit var loginComponent: LoginComponent

    @Inject
    lateinit var view: View

    @Inject
    lateinit var clothes: Clothes

    @Inject
    lateinit var house: House

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        loginComponent =
            DaggerApplicationComponent.builder().viewModule(ViewModule(baseContext)).build()
                .loginComponent().create()
        loginComponent.inject(this)
//        loginComponent = (applicationContext as MyDaggerApplication)
//            .appComponent.loginComponent().create()

        LogUtil.e("BaseActivity", view.toString())
        LogUtil.e("BaseActivity", clothes.toString())
        LogUtil.e("BaseActivity", house.getView().toString())
    }
}