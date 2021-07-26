package com.gx.base.base

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.accountbooks.base.BaseFragment
import com.gx.base.view.BaseToolbar
import com.gx.utils.log.LogUtil
import com.tencent.mars.xlog.Log

abstract class ToolbarFragment : BaseFragment() {
    var toolbar: BaseToolbar? = null

    override fun initView(view: View) {
        initToolBar()
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        LogUtil.i("getLayoutView")
        return null
    }

    private fun initToolBar() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        toolbar!!.title = getToolBarTitle()
    }

    abstract fun getToolBarTitle(): String

}