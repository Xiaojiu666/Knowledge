package com.gx.module_task

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_task.databinding.FragmentTaskHomeBinding
import com.gx.module_task.dummy.DummyContent
import com.gx.module_task.ui.adapter.RvTaskListAdapter

/**
 * A fragment representing a list of Items.
 */
class TaskHomeFragment : BaseFragment() {

    var dataBinding : FragmentTaskHomeBinding? = null

    override fun initView(view: View) {
        val taskRecyclerView = dataBinding?.taskRecyclerView
        with(taskRecyclerView!!) {
            layoutManager = LinearLayoutManager(context)
            adapter = RvTaskListAdapter(DummyContent.ITEMS)
        }
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        dataBinding = FragmentTaskHomeBinding.inflate(layoutInflater)
        return dataBinding?.root
    }

}