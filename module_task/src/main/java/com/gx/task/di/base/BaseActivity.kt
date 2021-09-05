package com.gx.task.di.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R
import com.gx.task.di.demo.*
import com.gx.utils.log.LogUtil
import dagger.internal.DoubleCheck
import javax.inject.Inject

class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var clothes: Clothes
    @Inject
    lateinit var clothes1: Clothes

    @Inject
    lateinit var view: View

    @Inject
    lateinit var house: House

    @Inject
    lateinit var phone: Phone


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        DaggerBaseContainer.builder().viewModule(ViewModule(baseContext)).testModule(TestModule()).build().inject(this)
//        LogUtil.e("BaseActivity", basePresenter.getData())

        LogUtil.e("BaseActivity", clothes.toString())
        LogUtil.e("BaseActivity", clothes1.toString())
        val create = Test.create()
        LogUtil.e("BaseActivity", create.get().toString())
        LogUtil.e("BaseActivity", create.get().toString())


        LogUtil.e("BaseActivity",Clothes_Factory.create().get().toString())
        LogUtil.e("BaseActivity", Clothes_Factory.create().get().toString())


        val provider = DoubleCheck.provider(Clothes_Factory.create())

        LogUtil.e("BaseActivity",provider.get().toString())
        LogUtil.e("BaseActivity", provider.get().toString())
//        LogUtil.e("BaseActivity", view.toString())
//        LogUtil.e("BaseActivity", view1.toString())
//        LogUtil.e("BaseActivity", view1.toString())
//        LogUtil.e("BaseActivity", basePresenter.toString())
//        LogUtil.e("BaseActivity", basePresenterCopy.toString())
    }

}