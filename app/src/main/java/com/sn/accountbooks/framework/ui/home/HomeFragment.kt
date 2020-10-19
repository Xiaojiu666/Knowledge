package com.sn.myapplication.ui.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sn.accountbooks.R
import com.sn.accountbooks.base.BaseFragment
import kotlinx.android.synthetic.main.sliding_sliding_view.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        homeViewModel =
//            ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: ImageView = root.findViewById(R.id.sliding_view_arrow)
////        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//////            textView.text = it
////        })
//        return root
//    }

    override fun initView(view: View) {
        sliding_view_arrow.animate().rotationX(1.0f).start()
        startRotate(view.findViewById(R.id.sliding_view_arrow))
    }

    fun startRotate(view: View){
        val rotateAnimator = ObjectAnimator.ofFloat(view,"rotation", 0f,360f)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home;
    }
}