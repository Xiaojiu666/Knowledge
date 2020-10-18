package com.sn.accountbooks.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

/**
 * Created by GuoXu on 2020/10/14 16:34.
 *
 */
abstract class BaseFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(getLayoutId(), container, false);
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusBar()
    }

    abstract fun initStatusBar()

    abstract fun getLayoutId(): Int
}