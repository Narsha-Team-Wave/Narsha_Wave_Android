package kr.hs.dgsw.noepa_ls.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kr.hs.dgsw.noepa_ls.MainActivity
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.custom.RadarMarkerView
import kr.hs.dgsw.noepa_ls.databinding.FragmentConnectBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentMesureBinding
import java.util.ArrayList

class MesureFragment : Fragment() {
    private var mainActivity: ScreenActivity? = null
    private var mBinding: FragmentMesureBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentMesureBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChart()
        initLineChart()
    }

    private fun initLineChart() {
        binding.LineChart1.setDrawGridBackground(true)
        binding.LineChart1.setBackgroundColor(Color.argb(0, 255, 255, 255))
        binding.LineChart1.setGridBackgroundColor(Color.argb(0x59, 255, 255, 255))

        binding.LineChart2.setDrawGridBackground(true)
        binding.LineChart2.setBackgroundColor(Color.argb(0, 255, 255, 255))
        binding.LineChart2.setGridBackgroundColor(Color.argb(0x59, 255, 255, 255))

        binding.LineChart3.setDrawGridBackground(true)
        binding.LineChart3.setBackgroundColor(Color.argb(0, 255, 255, 255))
        binding.LineChart3.setGridBackgroundColor(Color.argb(0x59, 255, 255, 255))

        binding.LineChart1.description.isEnabled = true
        binding.LineChart2.description.isEnabled = true
        binding.LineChart3.description.isEnabled = true
        val des1: Description = binding.LineChart1.description
        val des2: Description = binding.LineChart2.description
        val des3: Description = binding.LineChart3.description
        des1.isEnabled = true
        des1.text = "Meditation"
        des1.textSize = 15f
        des1.textColor = Color.WHITE
        des2.isEnabled = true
        des2.text = "Attention"
        des2.textSize = 15f
        des2.textColor = Color.WHITE
        des3.isEnabled = true
        des3.text = "Blink"
        des3.textSize = 15f
        des3.textColor = Color.WHITE

        binding.LineChart1.setTouchEnabled(false)
        binding.LineChart2.setTouchEnabled(false)
        binding.LineChart3.setTouchEnabled(false)

        binding.LineChart1.isDragEnabled = false
        binding.LineChart1.setScaleEnabled(false)
        binding.LineChart2.isDragEnabled = false
        binding.LineChart2.setScaleEnabled(false)
        binding.LineChart3.isDragEnabled = false
        binding.LineChart3.setScaleEnabled(false)

        binding.LineChart1.isAutoScaleMinMaxEnabled = true
        binding.LineChart2.isAutoScaleMinMaxEnabled = true
        binding.LineChart3.isAutoScaleMinMaxEnabled = true

        binding.LineChart1.setPinchZoom(false)
        binding.LineChart2.setPinchZoom(false)
        binding.LineChart3.setPinchZoom(false)

        binding.LineChart1.xAxis.setDrawGridLines(true)
        binding.LineChart1.xAxis.setDrawAxisLine(false)
        binding.LineChart2.xAxis.setDrawGridLines(true)
        binding.LineChart2.xAxis.setDrawAxisLine(false)
        binding.LineChart3.xAxis.setDrawGridLines(true)
        binding.LineChart3.xAxis.setDrawAxisLine(false)

        binding.LineChart1.xAxis.isEnabled = true
        binding.LineChart1.xAxis.setDrawGridLines(false)
        binding.LineChart2.xAxis.isEnabled = true
        binding.LineChart2.xAxis.setDrawGridLines(false)
        binding.LineChart3.xAxis.isEnabled = true
        binding.LineChart3.xAxis.setDrawGridLines(false)

        val l1: Legend = binding.LineChart1.legend
        val l2: Legend = binding.LineChart2.legend
        val l3: Legend = binding.LineChart3.legend
        l1.isEnabled = false
        l2.isEnabled = false
        l3.isEnabled = false

        val leftAxis1: YAxis = binding.LineChart1.axisLeft
        val leftAxis2: YAxis = binding.LineChart2.axisLeft
        val leftAxis3: YAxis = binding.LineChart3.axisLeft
        leftAxis1.isEnabled = false
        leftAxis2.isEnabled = false
        leftAxis3.isEnabled = false

        val rightAxis1: YAxis = binding.LineChart1.axisRight
        rightAxis1.isEnabled = false
        val rightAxis2: YAxis = binding.LineChart2.axisRight
        rightAxis2.isEnabled = false
        val rightAxis3: YAxis = binding.LineChart3.axisRight
        rightAxis3.isEnabled = false

        val yVals1 = ArrayList<Entry>()
        yVals1.add(Entry(0F, 0F))
        val set1 = LineDataSet(yVals1, "Meditation")
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.color = ColorTemplate.getHoloBlue()
        set1.valueTextColor = ColorTemplate.getHoloBlue()
        set1.lineWidth = 1.5f
        set1.setDrawCircles(false)
        set1.setDrawValues(false)
        set1.fillAlpha = 65
        set1.fillColor = ColorTemplate.getHoloBlue()
        set1.highLightColor = Color.argb(0, 255, 255, 255)
        set1.setDrawCircleHole(false)

        val lineData1: LineData = LineData(set1)
        lineData1.setValueTextColor(Color.argb(0, 255, 255, 255))
        lineData1.setValueTextSize(9f)

        binding.LineChart1.data = lineData1

        val yVals2 = ArrayList<Entry>()
        yVals2.add(Entry(0F, 0F))
        val set2 = LineDataSet(yVals2, "Attention")
        set2.axisDependency = YAxis.AxisDependency.LEFT
        set2.color = ColorTemplate.getHoloBlue()
        set2.valueTextColor = ColorTemplate.getHoloBlue()
        set2.lineWidth = 1.5f
        set2.setDrawCircles(false)
        set2.setDrawValues(false)
        set2.fillAlpha = 65
        set2.fillColor = ColorTemplate.getHoloBlue()
        set2.highLightColor = Color.argb(0, 255, 255, 255)
        set2.setDrawCircleHole(false)

        val lineData2: LineData = LineData(set2)
        lineData2.setValueTextColor(Color.argb(0, 255, 255, 255))
        lineData2.setValueTextSize(9f)

        binding.LineChart2.data = lineData2

        val yVals3 = ArrayList<Entry>()
        yVals3.add(Entry(0F, 0F))
        val set3 = LineDataSet(yVals3, "Blink")
        set3.axisDependency = YAxis.AxisDependency.LEFT
        set3.color = ColorTemplate.getHoloBlue()
        set3.valueTextColor = ColorTemplate.getHoloBlue()
        set3.lineWidth = 1.5f
        set3.setDrawCircles(false)
        set3.setDrawValues(false)
        set3.fillAlpha = 65
        set3.fillColor = ColorTemplate.getHoloBlue()
        set3.highLightColor = Color.argb(0, 255, 255, 255)
        set3.setDrawCircleHole(false)

        val lineData3: LineData = LineData(set1)
        lineData3.setValueTextColor(Color.argb(0, 255, 255, 255))
        lineData3.setValueTextSize(9f)

        binding.LineChart3.data = lineData1

        binding.LineChart1.invalidate()
        binding.LineChart2.invalidate()
        binding.LineChart3.invalidate()
    }

    private fun initChart() {
        Log.d(MainActivity.LOG_TAG, "initChart")
        binding.chart.setBackgroundColor(Color.argb(0x59, 255, 255, 255))
        binding.chart.description.isEnabled = false

        binding.chart.webLineWidth = 1f
        binding.chart.webColor = Color.argb(0x99, 80, 224, 255)
        binding.chart.webLineWidthInner = 1f
        binding.chart.webColorInner = Color.argb(0x99, 80, 224, 255)
        binding.chart.webAlpha = 100

        val mv: MarkerView = RadarMarkerView(context, R.layout.radar_markerview)
        mv.chartView = binding.chart

        binding.chart.marker = mv
        setData()
        val xAxis: XAxis = binding.chart.xAxis
        xAxis.textSize = 12f
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        val labels = ArrayList<String>()
        labels.add("delta")
        labels.add("high_alpha")
        labels.add("high_beta")
        labels.add("low_alpha")
        labels.add("low_beta")
        labels.add("low_gamma")
        labels.add("theta")
        labels.add("mid_gamma")

        val formatData = IndexAxisValueFormatter(labels)
        xAxis.valueFormatter = formatData

        xAxis.textColor = Color.WHITE

        val yAxis: YAxis = binding.chart.yAxis
        yAxis.setLabelCount(8, false)
        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.setDrawLabels(false)

        val l: Legend = binding.chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.textColor = Color.WHITE

    }

    private fun setData() {
        val mul = 80f
        val min = 20f
        val cnt = 8

        val entries1: ArrayList<RadarEntry> = ArrayList()

        for (i in 0 until cnt) {
            val val1 = (Math.random() * mul).toFloat() + min
            entries1.add(RadarEntry(val1))
        }

        val set1 = RadarDataSet(entries1, "")
        set1.color = Color.argb(0x99, 80, 224, 255)
        set1.fillColor = Color.argb(0x99, 80, 224, 255)

        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.isDrawHighlightCircleEnabled = true
        set1.setDrawHighlightIndicators(false)
        val sets: ArrayList<IRadarDataSet> = ArrayList()
        sets.add(set1)
        val data = RadarData(sets)
        data.setValueTextSize(8f)
        data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)
        binding.chart.data = data
        binding.chart.legend.isEnabled = false
        binding.chart.invalidate()
    }

}