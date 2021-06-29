package com.sn.accountbooks.test

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.sn.accountbooks.R
import com.sn.config.AppConfig
import com.sn.module_login.mvp.ui.login.LoginActivity
import com.sn.plugin_base.base.BaseAppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseAppCompatActivity() {
    override fun init() {}
    override fun initView() {
        editTextTextPersonName.setOnClickListener {
            ARouter.getInstance().build(AppConfig.ACTIVITY_LOGIN).navigation()
        }
    }
    override fun setViewId(): Int {
        return R.layout.activity_test
    }
}