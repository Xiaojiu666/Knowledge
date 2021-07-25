package com.gx.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.base.themeswitcher.ThemeOverlayUtils.applyThemeOverlays

/**
 * Created by GuoXu on 2020/10/13 19:26.
 */
abstract class BaseAppCompatActivity : AppCompatActivity() {

    val TAG: String = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyThemeOverlays(this)
        init()
        setContentView(getLayoutView())
        initView()
    }

    abstract fun init()

    abstract fun initView()

    // 通过View 绑定，主要为了适用DataBinding
    abstract fun getLayoutView(): View

    fun createView(resId: Int): View {
//        View.inflate(baseContext,R.layout.activity_task,null)
        return LayoutInflater.from(this).inflate(resId, null)
    }


}