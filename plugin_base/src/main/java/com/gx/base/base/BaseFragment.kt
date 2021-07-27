package com.gx.accountbooks.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gx.utils.log.LogUtil

/**
 * Created by GuoXu on 2020/10/14 16:34.
 *
 */
 abstract class BaseFragment : Fragment() {

    val TAG  by lazy { this.javaClass.name }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getLayoutView(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    abstract  fun initView(view: View);
    abstract fun getLayoutView(inflater: LayoutInflater): View?

    fun createView(resId: Int): View {
//        View.inflate(baseContext,R.layout.activity_task,null)
        return LayoutInflater.from(context).inflate(resId, null)
    }
}