package com.gx.base.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gx.base.base.interfaces.OnItemClickListener

abstract class BaseRecyclerViewAdapter<T>(var mList: MutableList<T>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mIsShowFooter = false
    protected var mLastPosition = -1
    var mOnItemClickListener: OnItemClickListener? = null
    var mContext: Context? = null
    private val itemClickStatus = true
    override fun getItemViewType(position: Int): Int {
        return if (mIsShowFooter && isFooterPosition(position)) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    var list: MutableList<T>?
        get() = mList
        set(list) {
            mList = list
            notifyDataSetChanged()
        }

//    fun addMore(list: MutableList<T>?) {
//        val startPosition = mList!!.size
//        mList!!.map {
//            list.addAll(it.toM)
//        }
//        notifyItemRangeInserted(startPosition, mList!!.size)
//    }

    fun add(position: Int, item: T) {
        mList!!.add(position, item)
        notifyItemInserted(position)
    }

    fun delete(position: Int) {
        mList!!.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        if (onItemClickListener == null) {
            return
        }
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemViewHolder = getItemViewHolder(parent)
        setItemOnClickEvent(itemViewHolder)
        return when (viewType) {
            TYPE_FOOTER, TYPE_ITEM -> itemViewHolder
            else -> itemViewHolder
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams != null) {
                if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                    val params =
                        holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                    params.isFullSpan = true
                }
            }
        }
    }

    protected fun getView(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    override fun getItemCount(): Int {
        if (mList == null) return 0
        var itemSize = mList!!.size
        if (mIsShowFooter) {
            //MLog.e("mIsShowFooter" + mIsShowFooter);
            itemSize += 1
            //MLog.e("itemSize" + itemSize);
        }
        return itemSize
    }

    /**
     * 是否是 footer item
     *
     * @param position
     * @return
     */
    protected fun isFooterPosition(position: Int): Boolean {
        return itemCount - 1 == position
    }

    /**
     * item 加载动画
     *
     * @param holder
     * @param position
     * @param type
     */
    protected fun setItemAppearAnimation(
        holder: RecyclerView.ViewHolder,
        position: Int,
        @AnimRes type: Int
    ) {
        if (position > mLastPosition) {
            val animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                type
            )
            holder.itemView.startAnimation(animation)
            mLastPosition = position
        }
    }

    /**
     * 显示Footer
     */
    fun showFooter() {
        mIsShowFooter = true
        notifyItemInserted(itemCount)
    }

    /**
     * 隐藏Footer
     */
    fun hideFooter() {
        mIsShowFooter = false
        notifyItemRemoved(itemCount)
    }

    abstract fun getItemViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder

    fun setItemOnClickEvent(holder: RecyclerView.ViewHolder) {
        holder.itemView.setOnClickListener { v ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(v, holder.layoutPosition)
            }
        }
    }

    protected inner class FooterViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_FOOTER = 1
    }

}