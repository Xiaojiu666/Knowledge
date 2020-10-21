package com.sn.myapplication.ui.home

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.sn.accountbooks.R
import com.sn.accountbooks.base.BaseFragment
import kotlinx.android.synthetic.main.sliding_background.*
import kotlinx.android.synthetic.main.sliding_sliding_view.*
import java.util.*

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
//        sliding_view_arrow.animate().rotationX(1.0f).start()
        linearLayout.setOnClickListener {
//            sliding_expansion_view.expansionView(sliding_view_arrow)
        }
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
//        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
//        chart.setTransparentCircleAlpha(110)

//        chart.holeRadius = 58f
//        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)
        chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        // chart.spin(2000, 0, 360);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.textColor = Color.WHITE
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 0f
//        l.yOffset = 0f

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        chart.setDrawEntryLabels(false)
        chart.setDrawMarkers(false)
        chart.setEntryLabelColor(Color.WHITE)
        // add a selection listener
        chart.animateY(1400, Easing.EaseInOutQuad)
        //dataSet.setSelectionShift(0f);
        setData(2, 10.0f);
    }


    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    "收入",
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)

        val colors = ArrayList<Int>()
//        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
//        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
//        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setDrawValues(false)
//        data.setValueTextSize(11f)
//        data.setValueTextColor(Color.WHITE)
        chart.data = data
        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_home;
    }
}