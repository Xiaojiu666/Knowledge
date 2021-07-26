package com.gx.module_task

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gx.accountbooks.base.BaseFragment
import com.gx.base.base.ToolbarFragment
import com.gx.base.themeswitcher.ThemeSwitcherHelper
import com.gx.module_task.databinding.FragmentTaskHomeBinding
import com.gx.module_task.dummy.DummyContent
import com.gx.module_task.ui.adapter.RvTaskListAdapter
import com.gx.utils.log.LogUtil

/**
 * A fragment representing a list of Items.
 */
class TaskHomeFragment : ToolbarFragment() {
    var themeSwitcherHelper: ThemeSwitcherHelper? = null
    var dataBinding: FragmentTaskHomeBinding? = null

    override fun initView(view: View) {
        LogUtil.i(TAG, "initView")
        themeSwitcherHelper = ThemeSwitcherHelper(parentFragmentManager)
        val taskRecyclerView = dataBinding?.taskRecyclerView
        toolbar = dataBinding!!.baseToolbar
        with(taskRecyclerView!!) {
            layoutManager = LinearLayoutManager(context)
            adapter = RvTaskListAdapter(DummyContent.ITEMS)
        }
        dataBinding!!.textView.setOnClickListener {
            themeSwitcherHelper!!.showThemeSwitcher();
        }
        super.initView(view)
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        dataBinding = FragmentTaskHomeBinding.inflate(layoutInflater)
        return dataBinding?.root
    }

    override fun getToolBarTitle(): String {
        return TAG
    }


}