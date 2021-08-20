package com.gx.task

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_task.databinding.FragmentTaskHomeBinding
import com.gx.task.ui.adapter.RvTaskListAdapter
import com.gx.utils.log.LogUtil

/**
 * A fragment representing a list of Items.
 */
class TaskHomeFragment : BaseFragment() {
    var dataBinding: FragmentTaskHomeBinding? = null

    override fun initView(view: View) {
        val taskRecyclerView = dataBinding?.taskRecyclerView
        with(taskRecyclerView!!) {
            layoutManager = LinearLayoutManager(context)
            adapter = RvTaskListAdapter(getTaskData())
        }
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        dataBinding = FragmentTaskHomeBinding.inflate(layoutInflater)
        return dataBinding?.root
    }


}