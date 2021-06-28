package com.sn.accountbooks.test

import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.sn.accountbooks.R
import com.sn.plugin_base.base.BaseAppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseAppCompatActivity() {
    override fun init() {}
    override fun initView() {
        editTextTextPersonName.setOnClickListener {
            Toast.makeText(baseContext,"AAAAAA",Toast.LENGTH_LONG).show()
            ARouter.getInstance().build("/app/loginactivity").navigation()  }
    }
    override fun setViewId(): Int {
        return R.layout.activity_test
    }
}