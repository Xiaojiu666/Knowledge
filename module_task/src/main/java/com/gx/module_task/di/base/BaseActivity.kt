package com.gx.module_task.di.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R
import com.gx.utils.log.LogUtil
import javax.inject.Inject

class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var basePresenter: BasePresenter

    @Inject
    lateinit var basePresenterCopy: BasePresenter

    @Inject
    lateinit var view: View
    @Inject
    lateinit var view1: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        DaggerBaseContainer.builder().baseModule(BaseModule(baseContext)).build().inject(this)
        LogUtil.e("BaseActivity", basePresenter.getData())
        LogUtil.e("BaseActivity", view.toString())
        LogUtil.e("BaseActivity", view1.toString())
        LogUtil.e("BaseActivity", basePresenter.toString())
        LogUtil.e("BaseActivity", basePresenterCopy.toString())
    }
}