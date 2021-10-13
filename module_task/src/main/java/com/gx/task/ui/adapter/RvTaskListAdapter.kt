package com.gx.task.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gx.base.base.BaseRecyclerViewAdapter
import com.gx.base.base.interfaces.OnItemClickListener
import com.gx.module_task.R
import com.gx.module_task.databinding.ItemTaskListBinding
import com.gx.task.model.data.Task
import com.gx.task.ui.fragment.TaskHomeFragmentDirections


class RvTaskListAdapter(mList: MutableList<Task>?) :
    BaseRecyclerViewAdapter<Task>(mList) {

    private var dataBinding: ItemTaskListBinding? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val taskListInfo = mList!![position]
        dataBinding!!.taskList = taskListInfo
    }

    inner class TaskListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            setOnItemClickListener(OnItemClickListener { view, position ->
                navigateToPlant(0, view)
            })
        }
    }

    private fun navigateToPlant(plantId: Int, view: View) {
        val direction = TaskHomeFragmentDirections.actionTaskHomeDetail(0)
        view.findNavController().navigate(direction)
    }

    override fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        dataBinding = ItemTaskListBinding.bind(getView(parent!!, R.layout.item_task_list))
        return TaskListViewHolder(dataBinding!!.root)
    }
}