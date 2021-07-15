package com.gx.module_task.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_task.R
import com.gx.module_task.TaskApplication
import com.gx.module_task.databinding.FragmentTaskDetailBinding
import com.gx.utils.log.LogUtil
import com.tencent.mars.xlog.Log

class TaskDetailFragment : BaseFragment() {

    var inflate: FragmentTaskDetailBinding? = null
    var taskViewModel: TaskDetailViewModel? = null
    init {

    }


    override fun initView(view: View) {
        val appContainer = (activity?.application as TaskApplication).appContainer
        taskViewModel = appContainer.taskDetailViewModel.create()
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