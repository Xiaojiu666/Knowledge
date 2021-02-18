package com.sn.module_account_book.ui.account

import android.util.Log
import android.view.View
import com.sn.accountbooks.base.BaseFragment
import com.sn.module_account_book.R

class AccountBookFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    override fun initView(view: View) {
        Log.e("HomeFragment", "module")
    }


    private fun setData(count: Int, range: Float) {

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_slideshow;
    }
}