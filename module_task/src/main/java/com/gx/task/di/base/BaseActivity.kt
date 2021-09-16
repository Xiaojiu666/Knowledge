package com.gx.task.di.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gx.module_task.R
import com.gx.task.di.DatabaseModule
import com.gx.task.di.computer.Computer
import com.gx.task.di.computer.MainBoard
import com.gx.task.di.demo.Clothes
import com.gx.task.di.demo.House
import com.gx.task.di.demo.Person
import com.gx.task.di.subcomponentDependencies.DaggerAppComponent
import com.gx.task.di.subcomponentDependencies.DaoModule
import com.gx.task.model.room.TaskDao
import com.gx.task.model.room.TaskDao_Impl
import com.gx.utils.log.LogUtil
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

class BaseActivity : AppCompatActivity() {

//    @Inject
//    lateinit var clothes: Clothes
//
//    @Inject
//    lateinit var view: View
//
////    @Inject
////    lateinit var view1: View
//
//    @Inject
//    lateinit var house: House

//    @Inject
//    lateinit var taskDao: TaskDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val computer = Computer()
//        val appComponent =
//            DaggerAppComponent.builder().databaseModule(DatabaseModule(baseContext)).build().inject(this)
//
//        val daoComponent =
//            DaggerDaoComponent.builder().appComponent(appComponent).daoModule(DaoModule()).build()
//                .inject(this)
//        LogUtil.e("BaseActivity", taskDao.toString())
//        LogUtil.e("BaseActivity", CPU_Factory.create().toString())
//        LogUtil.e("BaseActivity", CPU_Factory.create().toString())
        LogUtil.e("BaseActivity", MainBoard.create().toString())
        LogUtil.e("BaseActivity", MainBoard.create().toString())

//        DaggerBaseContainer.builder().viewModule(ViewModule(baseContext)).build().inject(this)
//        DaggerApplicationComponent.builder().viewModule(ViewModule(baseContext)).build()
//            .loginComponent().create().inject(this)
////        LogUtil.e("BaseActivity", basePresenter.getData())
//        LogUtil.e("BaseActivity", view.toString())
//        LogUtil.e("BaseActivity", house.getView())
//        LogUtil.e("BaseActivity", house.toString())
////        LogUtil.e("BaseActivity", view1.toString())
////        LogUtil.e("BaseActivity", basePresenter.toString())
////        LogUtil.e("BaseActivity", basePresenterCopy.toString())
//
//        textView.setOnClickListener {
//            DaggerApplicationComponent.builder().viewModule(ViewModule(baseContext)).build()
//                .loginComponent().create().inject(this)
//            LogUtil.e("BaseActivity", view.toString())
//            LogUtil.e("BaseActivity", house.getView())
//            LogUtil.e("BaseActivity", house.toString())
//        }
    }


}