package com.sn.accountbooks.test

import com.alibaba.android.arouter.launcher.ARouter
import com.sn.accountbooks.R
import com.sn.plugin_base.addTest
import com.sn.plugin_base.config.AppConfig
import com.sn.plugin_base.base.BaseAppCompatActivity
import com.sn.utils.log.LogUtil
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestActivity : BaseAppCompatActivity() {
    override fun init() {}
    override fun initView() {
        editTextTextPersonName.setOnClickListener {
            ARouter.getInstance().build(AppConfig.ACTIVITY_LOGIN).navigation()
        }


        GlobalScope.launch(Dispatchers.Main) {
            LogUtil.e(TAG, "TAG${getMessageFromNetwork()}")
            LogUtil.e(TAG, "TAG${getMessageFromNetwork()}")
            LogUtil.e(TAG, "TAG${getMessageFromNetwork()}")
        }
        addTest(1, 2)
        runOnUiThread {
            Thread {
                var name = ""
                LogUtil.e(TAG, "ThreadName${Thread.currentThread().name}")
                for (i in 0..1000000) {
                    //这里模拟一个耗时操作
                }
                name = TAG
            }.start()
        }
    }

    override fun setViewId(): Int {
        return R.layout.activity_test
    }


    suspend fun getMessageFromNetwork(): String {
        var name = ""
        withContext(Dispatchers.IO) {
            LogUtil.e(TAG, "ThreadName${Thread.currentThread().name}")
            for (i in 0..1000000) {
                //这里模拟一个耗时操作
            }
            name = TAG
        }
        return name
    }


}