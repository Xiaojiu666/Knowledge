package com.sn.plugin_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by GuoXu on 2020/10/13 19:26.
 */
abstract class BaseAppCompatActivity : AppCompatActivity() {

    val TAG: String = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(setViewId())
        initView()
    }

    abstract fun init()

    abstract fun initView()

    abstract fun setViewId(): Int

}