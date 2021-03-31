package com.sn.plugin_base.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by GuoXu on 2020/10/13 19:26.
 */
abstract class BaseAppCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(setViewId())
        //绑定到butterKnife
        initView()
    }

    abstract fun init()

    abstract fun initView()

    abstract fun setViewId(): Int
}