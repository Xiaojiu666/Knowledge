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
import com.gx.module_task.databinding.FragmentTaskDetailBinding
import com.gx.utils.log.LogUtil
import com.tencent.mars.xlog.Log

class TaskDetailFragment : BaseFragment() {

    private lateinit var viewModel: TaskDetailViewModel
    var inflate: FragmentTaskDetailBinding? = null

    override fun initView(view: View) {
        viewModel = ViewModelProvider(this).get(TaskDetailViewModel::class.java)
        LogUtil.e(TAG, "TaskDetail ")
        viewModel.taskDetail.observe(this, Observer {
            LogUtil.e(TAG, "TaskDetail ${it.toString()}")
        })
        inflate?.textView2?.setOnClickListener {
            viewModel.upDataTaskDetailInfo()
        }
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        inflate = FragmentTaskDetailBinding.inflate(layoutInflater)
        return inflate?.root
    }
}