package com.gx.module_task.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gx.module_task.R
import com.gx.module_task.TaskHomeFragment
import com.gx.module_task.TaskHomeFragmentDirections
import com.gx.module_task.dummy.DummyContent.DummyItem
import com.gx.plugin_base.base.BaseRecyclerViewAdapter
import com.gx.plugin_base.base.interfaces.OnItemClickListener
import com.gx.utils.log.LogUtil


class RvTaskListAdapter(mList: MutableList<DummyItem>?) :
    BaseRecyclerViewAdapter<DummyItem>(mList) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = mList!![position]
        var holder = holder as TaskListViewHolder;
        holder.idView.text = item.id
        holder.contentView.text = item.content

    }

    inner class TaskListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        init {
            setOnItemClickListener(OnItemClickListener { view, position ->
                navigateToPlant(0, view)
            })
        }
        private fun navigateToPlant(plantId: Int, view: View) {
            val direction = TaskHomeFragmentDirections.actionTaskHomeDetail(0)
            view.findNavController().navigate(direction)
//            val direction = TaskHomeFragmentDeat
//                .actionViewPagerFragmentToPlantDetailFragment(plantId)
//            view.findNavController().navigate(direction)
        }
    }

    override fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return TaskListViewHolder(getView(parent!!, R.layout.item_task_list))
    }
}