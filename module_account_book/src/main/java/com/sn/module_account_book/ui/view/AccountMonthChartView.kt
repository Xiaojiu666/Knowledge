package com.sn.module_account_book.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.sn.module_account_book.R
import kotlinx.android.synthetic.main.sliding_background.*
import java.util.ArrayList

class AccountMonthChartView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) : PieChart(context, attrs, defStyle){

    init {
//        initBaseConfig()
//        setData(2, 10.0f)
    }

    fun initLegend(){
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.textColor = Color.WHITE
    }

   open fun initBaseConfig(){
        setUsePercentValues(true)
        description.isEnabled = false
//        setExtraOffsets(5f, 10f, 5f, 5f)
        dragDecelerationFrictionCoef = 0.95f
        isDrawHoleEnabled = true
        setHoleColor(Color.WHITE)
        setTransparentCircleColor(Color.WHITE)
//        setTransparentCircleAlpha(110)
//        holeRadius = 58f
//        transparentCircleRadius = 61f
        setDrawCenterText(true)
        rotationAngle = 0f
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        isRotationEnabled = true
        isHighlightPerTapEnabled = true
        // spin(2000, 0, 360);
        val l = legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.textColor = Color.WHITE
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 0f
//        l.yOffset = 0f

        // setUnit(" €");
        // setDrawUnitsInChart(true);
        // add a selection listener
        // setUnit(" €");
        // setDrawUnitsInChart(true);
        setDrawEntryLabels(false)
        setDrawMarkers(false)
        setEntryLabelColor(Color.WHITE)
        // add a selection listener
        animateY(1400, Easing.EaseInOutQuad)
        //dataSet.setSelectionShift(0f);
        setData(2, 10.0f);
    }
    
    
    open fun setData(count: Int, range: Float){
        val entries = ArrayList<PieEntry>()
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the 
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
        this.data = data
        // undo all highlights
        highlightValues(null)
        invalidate()
    }
    
}