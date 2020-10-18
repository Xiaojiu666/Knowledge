package com.sn.accountbooks

import com.gyf.immersionbar.ImmersionBar
import com.sn.accountbooks.base.BaseFragment

/**
 * Created by GuoXu on 2020/10/14 17:12.
 *
 */
class HomeFragmentA : BaseFragment() {
    override fun initStatusBar() {
        ImmersionBar.with(this)
            .init()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

}