package com.gx.task.di.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R
import com.gx.task.di.demo.Clothes
import com.gx.task.di.demo.House
import com.gx.task.di.demo.Person
import com.gx.utils.log.LogUtil
import javax.inject.Inject

class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var clothes: Clothes
    @Inject
    lateinit var view: View
    @Inject
    lateinit var view1: View
    @Inject
    lateinit var house: House


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        DaggerBaseContainer.builder().viewModule(ViewModule(baseContext)).build().inject(this)
//        LogUtil.e("BaseActivity", basePresenter.getData())
        LogUtil.e("BaseActivity", view.toString())
        LogUtil.e("BaseActivity", view1.toString())
//        LogUtil.e("BaseActivity", view1.toString())
//        LogUtil.e("BaseActivity", basePresenter.toString())
//        LogUtil.e("BaseActivity", basePresenterCopy.toString())
    }
}