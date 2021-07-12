package com.gx.module_task

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_task.databinding.FragmentTaskListBinding
import com.gx.module_task.dummy.DummyContent
import com.gx.module_task.ui.adapter.RvTaskListAdapter

/**
 * A fragment representing a list of Items.
 */
class TaskHomeFragment : BaseFragment() {

    override fun initView(view: View) {
        val fragmentTaskListBinding = FragmentTaskListBinding.inflate(layoutInflater)
        fragmentTaskListBinding.taskRecyclerView.layoutManager =LinearLayoutManager(context)
        fragmentTaskListBinding.taskRecyclerView.adapter = RvTaskListAdapter(DummyContent.ITEMS)

//        with(fragmentTaskListBinding.taskRecyclerView) {
//            layoutManager = LinearLayoutManager(context)
//            adapter = RvTaskListAdapter(DummyContent.ITEMS)
//        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_task_list
    }

}