package com.sn.accountbooks.test

import com.alibaba.android.arouter.launcher.ARouter
import com.sn.accountbooks.R
import com.sn.plugin_base.config.AppConfig
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