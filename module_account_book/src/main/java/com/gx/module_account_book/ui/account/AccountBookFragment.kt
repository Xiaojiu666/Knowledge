package com.gx.module_account_book.ui.account

import android.view.LayoutInflater
import android.view.View
import com.gx.accountbooks.base.BaseFragment
import com.gx.module_account_book.R
import kotlinx.android.synthetic.main.sliding_background.*


class AccountBookFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun initView(view: View) {
//        sliding_view_arrow.animate().rotationX(1.0f).start()
        accountMonthChartView.initBaseConfig()
//        chart.setUsePercentValues(true)
//        chart.description.isEnabled = false
////        chart.setExtraOffsets(5f, 10f, 5f, 5f)
//        chart.dragDecelerationFrictionCoef = 0.95f
//        chart.isDrawHoleEnabled = true
//        chart.setHoleColor(Color.WHITE)
//        chart.setTransparentCircleColor(Color.WHITE)
////        chart.setTransparentCircleAlpha(110)
////        chart.holeRadius = 58f
////        chart.transparentCircleRadius = 61f
//        chart.setDrawCenterText(true)
//        chart.rotationAngle = 0f
//        // enable rotation of the chart by touch
//        // enable rotation of the chart by touch
//        chart.isRotationEnabled = true
//        chart.isHighlightPerTapEnabled = true
//        // chart.spin(2000, 0, 360);
//        val l = chart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        l.orientation = Legend.LegendOrientation.VERTICAL
//        l.setDrawInside(false)
//        l.textColor = Color.WHITE
////        l.xEntrySpace = 7f
////        l.yEntrySpace = 0f
////        l.yOffset = 0f
//
//        // chart.setUnit(" €");
//        // chart.setDrawUnitsInChart(true);
//        // add a selection listener
//        // chart.setUnit(" €");
//        // chart.setDrawUnitsInChart(true);
//        chart.setDrawEntryLabels(false)
//        chart.setDrawMarkers(false)
//        chart.setEntryLabelColor(Color.WHITE)
//        // add a selection listener
//        chart.animateY(1400, Easing.EaseInOutQuad)
//        //dataSet.setSelectionShift(0f);
//        setData(2, 10.0f);
    }

    override fun getLayoutView(inflater: LayoutInflater): View? = createView(R.layout.fragment_account_home)


//    override fun getLayoutId(): Int {
//        return R.layout.fragment_account_home
//    }
}