package com.gx.module_task

import com.gx.module_task.details.TaskDetailInfo
import com.gx.module_task.di.DaggerTestContainer
import com.gx.module_task.di.TestRepository
import com.gx.plugin_base.base.BaseAppCompatActivity
import com.tencent.mars.xlog.Log
import javax.inject.Inject

class TaskActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var testRepository: TestRepository

    override fun init() {
        DaggerTestContainer.create().inject(this)
    }

    override fun initView() {
        Log.e(TAG, "testRepository name : ${testRepository.returnName()}")
        Log.e(TAG,
            "testRepository.remote name : ${testRepository.testRemoteDataSource.returnName()}"
        )
    }

    override fun setViewId(): Int {
        return R.layout.activity_task
    }
}