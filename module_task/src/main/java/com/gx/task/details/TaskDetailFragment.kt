package com.gx.task.details

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_task.databinding.FragmentTaskDetailBinding
import com.gx.task.TaskApplication
import com.gx.task.vm.TaskDetailViewModel
import com.gx.utils.log.LogUtil
import javax.inject.Inject

 class TaskDetailFragment : BaseFragment() {

    var inflate: FragmentTaskDetailBinding? = null

    @Inject
    @JvmField
    var taskViewModel: TaskDetailViewModel? = null

    override fun initView(view: View) {
        val appContainer = (activity?.application as TaskApplication).appContainer
//        taskViewModel = appContainer.taskDetailViewModel.create()
        LogUtil.e(TAG, "TaskDetail ")
        taskViewModel!!.taskDetail.observe(this, Observer {
            LogUtil.e(TAG, "TaskDetail ${it.toString()}")
        })
        inflate?.textView2?.setOnClickListener {
            taskViewModel!!.upDataTaskDetailInfo()
        }
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        inflate = FragmentTaskDetailBinding.inflate(layoutInflater)
        return inflate?.root
    }
}