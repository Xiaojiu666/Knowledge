package com.gx.base.base

import android.view.LayoutInflater
import android.view.View
import com.gx.accountbooks.base.BaseFragment
import com.gx.base.view.BaseToolbar
import com.gx.utils.log.LogUtil
import com.tencent.mars.xlog.Log

open class ToolbarFragment : BaseFragment() {
    var toolbar: BaseToolbar? = null

    override fun initView(view: View) {
        activity.
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        LogUtil.i("getLayoutView")
        return null
    }
}