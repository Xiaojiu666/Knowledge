package com.gx.module_setting

import android.view.LayoutInflater
import android.view.View
import com.gx.accountbooks.base.BaseFragment
import com.gx.base.themeswitcher.ThemeSwitcherHelper
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment() {
    var themeSwitcherHelper: ThemeSwitcherHelper? = null
    override fun initView(view: View) {
        textView.setOnClickListener {
            themeSwitcherHelper = ThemeSwitcherHelper(parentFragmentManager)
            themeSwitcherHelper!!.showThemeSwitcher()
        }
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        return createView(R.layout.fragment_setting)
    }
}