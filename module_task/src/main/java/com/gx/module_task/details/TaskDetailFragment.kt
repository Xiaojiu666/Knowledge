package com.gx.module_task.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.gx.module_task.R
import com.gx.utils.log.LogUtil
import com.tencent.mars.xlog.Log

class TaskDetailFragment : Fragment() {
    val TAG = "TaskDetailFragment"

    companion object {
        fun newInstance() = TaskDetailFragment()
    }

    private lateinit var viewModel: TaskDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskDetailViewModel::class.java)
        viewModel.taskDetail.observe(this, Observer {
            LogUtil.e(TAG,"TaskDetail $TaskDetailInfo")
        })
    }

}