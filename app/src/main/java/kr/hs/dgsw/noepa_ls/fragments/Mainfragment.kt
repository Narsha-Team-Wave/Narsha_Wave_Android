package kr.hs.dgsw.noepa_ls.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.pwittchen.neurosky.library.NeuroSky
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import com.github.pwittchen.neurosky.library.message.enums.State
import kr.hs.dgsw.noepa_ls.*
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.custom.RadarMarkerView
import kr.hs.dgsw.noepa_ls.databinding.FragmentLoginBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentMainfragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class Mainfragment : Fragment() {

//    private var Counting = IntArray(10)
//    private var BindMindWave = IntArray(10)
//    private var attention = mutableListOf<String>()
//    private var delta = mutableListOf<String>()
//    private var high_alpha = mutableListOf<String>()
//    private var high_beta = mutableListOf<String>()
//    private var low_alpha = mutableListOf<String>()
//    private var low_beta = mutableListOf<String>()
//    private var low_gamma = mutableListOf<String>()
//    private var meditation = mutableListOf<String>()
//    private var mid_gamma = mutableListOf<String>()
//    private var theta = mutableListOf<String>()



    val MY_PERMISSION_ACCESS_ALL = 100

//    var check = false
//    var checkAttenTion: Int = 0
//    var checkMeditation: Int = 0
//    var checkBlink: Int = 0



    private var mBinding: FragmentMainfragmentBinding? = null
    private val binding get() = mBinding!!


    private var mainActivity: ScreenActivity? = null

//    val entries1: ArrayList<RadarEntry> = ArrayList()
//
//    private var brainWaveList: IntArray = IntArray(8)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_mainfragment, container, false)
        mBinding = FragmentMainfragmentBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

//        initButtonListeners()
//
//        initChart()
//        initLineChart()

        if (android.os.Build.VERSION.SDK_INT >= 31){
            Log.d("TAG", "asd")
            if (ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.BLUETOOTH_ADVERTISE,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                )
                ActivityCompat.requestPermissions(mainActivity!!, permissions, MY_PERMISSION_ACCESS_ALL)
            }
        }else{
            Log.d("TAG", "asd2")
            if (ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mainActivity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(mainActivity!!, permissions, MY_PERMISSION_ACCESS_ALL)
            }
        }

        binding.logoutBtn.setOnClickListener {
            App.prefs.id = null
            mainActivity!!.changeFragment(mainActivity!!.LOGIN_SCREEN)
        }

        binding.connectLayout.setOnClickListener{
            mainActivity!!.changeFragment(mainActivity!!.CONNECT_SCREEN)
        }


//        binding.btnHistory.setOnClickListener {
//            InsertData()
//            val intent = Intent(mainActivity!!, HistoryActivity::class.java)
//            intent.putExtra("attention", attention.toTypedArray())
//            intent.putExtra("delta", delta.toTypedArray())
//            intent.putExtra("high_alpha", high_alpha.toTypedArray())
//            intent.putExtra("high_beta", high_beta.toTypedArray())
//            intent.putExtra("low_alpha", low_alpha.toTypedArray())
//            intent.putExtra("low_beta", low_beta.toTypedArray())
//            intent.putExtra("low_gamma", low_gamma.toTypedArray())
//            intent.putExtra("meditation", meditation.toTypedArray())
//            intent.putExtra("mid_gamma", mid_gamma.toTypedArray())
//            intent.putExtra("theta", theta.toTypedArray())
//            startActivity(intent)
//        }

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode,
            permissions,
            grantResults)
        if (requestCode == MY_PERMISSION_ACCESS_ALL) {
            if (grantResults.size > 0) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) System.exit(0)
                }
            }
        }
    }
}


//    private fun initLineChart() {
//        binding.LineChart1.setDrawGridBackground(true)
//        binding.LineChart1.setBackgroundColor(Color.BLACK)
//        binding.LineChart1.setGridBackgroundColor(Color.BLACK)
//
//        binding.LineChart2.setDrawGridBackground(true)
//        binding.LineChart2.setBackgroundColor(Color.BLACK)
//        binding.LineChart2.setGridBackgroundColor(Color.BLACK)
//        // description text
//        // description text
//        binding.LineChart1.description.isEnabled = true
//        binding.LineChart2.description.isEnabled = true
//        val des1: Description = binding.LineChart1.description
//        val des2: Description = binding.LineChart2.description
//        des1.isEnabled = true
//        des1.text = "Real-Time DATA"
//        des1.textSize = 15f
//        des1.textColor = Color.WHITE
//        des2.isEnabled = true
//        des2.text = "Real-Time DATA"
//        des2.textSize = 15f
//        des2.textColor = Color.WHITE
//
//// touch gestures (false-비활성화)
//
//// touch gestures (false-비활성화)
//        binding.LineChart1.setTouchEnabled(false)
//        binding.LineChart2.setTouchEnabled(false)
//
//// scaling and dragging (false-비활성화)
//
//// scaling and dragging (false-비활성화)
//        binding.LineChart1.isDragEnabled = false
//        binding.LineChart1.setScaleEnabled(false)
//        binding.LineChart2.isDragEnabled = false
//        binding.LineChart2.setScaleEnabled(false)
//
////auto scale
//
////auto scale
//        binding.LineChart1.isAutoScaleMinMaxEnabled = true
//        binding.LineChart2.isAutoScaleMinMaxEnabled = true
//
//// if disabled, scaling can be done on x- and y-axis separately
//
//// if disabled, scaling can be done on x- and y-axis separately
//        binding.LineChart1.setPinchZoom(false)
//        binding.LineChart2.setPinchZoom(false)
//
////X축
//
////X축
//        binding.LineChart1.xAxis.setDrawGridLines(true)
//        binding.LineChart1.xAxis.setDrawAxisLine(false)
//        binding.LineChart2.xAxis.setDrawGridLines(true)
//        binding.LineChart2.xAxis.setDrawAxisLine(false)
//
//        binding.LineChart1.xAxis.isEnabled = true
//        binding.LineChart1.xAxis.setDrawGridLines(false)
//        binding.LineChart2.xAxis.isEnabled = true
//        binding.LineChart2.xAxis.setDrawGridLines(false)
//
////Legend
//
////Legend
//        val l1: Legend = binding.LineChart1.legend
//        val l2: Legend = binding.LineChart2.legend
//        l1.isEnabled = true
//        l1.formSize = 10f // set the size of the legend forms/shapes
//        l2.isEnabled = true
//        l2.formSize = 10f
//
//        l1.textSize = 12f
//        l1.textColor = Color.WHITE
//        l2.textSize = 12f
//        l2.textColor = Color.WHITE
//
////Y축
//
////Y축
//        val leftAxis1: YAxis = binding.LineChart1.axisLeft
//        val leftAxis2: YAxis = binding.LineChart2.axisLeft
//        leftAxis1.isEnabled = true
//        leftAxis1.textColor = Color.RED
//        leftAxis1.setDrawGridLines(true)
//        leftAxis1.gridColor = Color.GRAY
//        leftAxis2.isEnabled = true
//        leftAxis2.textColor = Color.RED
//        leftAxis2.setDrawGridLines(true)
//        leftAxis2.gridColor = Color.GRAY
//
//        val rightAxis1: YAxis = binding.LineChart1.axisRight
//        rightAxis1.isEnabled = false
//        val rightAxis2: YAxis = binding.LineChart2.axisRight
//        rightAxis2.isEnabled = false
//
//
//// don't forget to refresh the drawing
//        // create a data object with the data sets
//        val yVals1 = ArrayList<Entry>()
//        yVals1.add(Entry(0F, 0F))
//        val set1 = LineDataSet(yVals1, "Meditation")
//        set1.axisDependency = YAxis.AxisDependency.LEFT
//        set1.color = ColorTemplate.getHoloBlue()
//        set1.valueTextColor = ColorTemplate.getHoloBlue()
//        set1.lineWidth = 1.5f
//        set1.setDrawCircles(false)
//        set1.setDrawValues(false)
//        set1.fillAlpha = 65
//        set1.fillColor = ColorTemplate.getHoloBlue()
//        set1.highLightColor = Color.rgb(244, 117, 117)
//        set1.setDrawCircleHole(false)
//
//        val lineData1: LineData = LineData(set1)
//        lineData1.setValueTextColor(Color.WHITE)
//        lineData1.setValueTextSize(9f)
//
//        // set data
//        binding.LineChart1.data = lineData1
//
//        //
//
//        val yVals2 = ArrayList<Entry>()
//        yVals2.add(Entry(0F, 0F))
//        val set2 = LineDataSet(yVals2, "Attention")
//        set2.axisDependency = YAxis.AxisDependency.LEFT
//        set2.color = ColorTemplate.getHoloBlue()
//        set2.valueTextColor = ColorTemplate.getHoloBlue()
//        set2.lineWidth = 1.5f
//        set2.setDrawCircles(false)
//        set2.setDrawValues(false)
//        set2.fillAlpha = 65
//        set2.fillColor = ColorTemplate.getHoloBlue()
//        set2.highLightColor = Color.rgb(244, 117, 117)
//        set2.setDrawCircleHole(false)
//
//        val lineData2: LineData = LineData(set2)
//        lineData2.setValueTextColor(Color.WHITE)
//        lineData2.setValueTextSize(9f)
//
//        // set data
//        binding.LineChart2.data = lineData2
//
//
//// don't forget to refresh the drawing
//        binding.LineChart1.invalidate()
//        binding.LineChart2.invalidate()
//    }
//
//    private fun addEntry1(num: Double) {
//        var data1: LineData = binding.LineChart1.data
//        if (data1 == null) {
//            data1 = LineData()
//            binding.LineChart1.data = data1
//        }
//
//        var set1 = data1.getDataSetByIndex(0)
//        // set.addEntry(...); // can be called as well
//        if (set1 == null) {
//            set1 = createSet()
//            data1.addDataSet(set1)
//        }
//        //data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
//
//        data1.addEntry(
//            Entry(set1.entryCount
//                .toFloat(), num.toFloat()), 0
//        )
//        data1.notifyDataChanged()
//
//        // let the chart know it's data has changed
//        binding.LineChart1.notifyDataSetChanged()
//        binding.LineChart1.setVisibleXRangeMaximum(150F)
//        // this automatically refreshes the chart (calls invalidate())
//        binding.LineChart1.moveViewTo(data1.entryCount.toFloat(), 50f, YAxis.AxisDependency.LEFT)
//    }
//
//    private fun addEntry2(num: Double) {
//        var data2: LineData = binding.LineChart2.data
//        if (data2 == null) {
//            data2 = LineData()
//            binding.LineChart2.data = data2
//        }
//
//        var set2 = data2.getDataSetByIndex(0)
//        // set.addEntry(...); // can be called as well
//        if (set2 == null) {
//            set2 = createSet()
//            data2.addDataSet(set2)
//        }
//        //data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
//
//        data2.addEntry(
//            Entry(set2.entryCount
//                .toFloat(), num.toFloat()), 0
//        )
//        data2.notifyDataChanged()
//
//        // let the chart know it's data has changed
//        binding.LineChart2.notifyDataSetChanged()
//        binding.LineChart2.setVisibleXRangeMaximum(150F)
//        // this automatically refreshes the chart (calls invalidate())
//        binding.LineChart2.moveViewTo(data2.entryCount.toFloat(), 50f, YAxis.AxisDependency.LEFT)
//    }
//
//    private fun createSet(): LineDataSet {
//        val set = LineDataSet(null, "Real-time Line Data")
//        set.lineWidth = 1f
//        set.setDrawValues(false)
//        set.valueTextColor = Color.WHITE
//        set.color = Color.WHITE
//        set.mode = LineDataSet.Mode.LINEAR
//        set.setDrawCircles(false)
//        set.highLightColor = Color.rgb(190, 190, 190)
//        return set
//    }
//
//    private fun initChart() {
//        Log.d(LOG_TAG, "initChart")
//        binding.chart1.setBackgroundColor(Color.rgb(60, 65, 82))
//
//        binding.chart1.description.isEnabled = false
//
//        binding.chart1.webLineWidth = 1f
//        binding.chart1.webColor = Color.LTGRAY
//        binding.chart1.webLineWidthInner = 1f
//        binding.chart1.webColorInner = Color.LTGRAY
//        binding.chart1.webAlpha = 100
//
//        // create a custom MarkerView (extend MarkerView) and specify the layout
//        // to use for it
//
//        // create a custom MarkerView (extend MarkerView) and specify the layout
//        // to use for it
//
//        val mv: MarkerView = RadarMarkerView(activity, R.layout.radar_markerview)
//        mv.chartView = binding.chart1 // For bounds control
//
//        binding.chart1.marker = mv // Set the marker to the chart
//        setData()
//        val xAxis: XAxis = binding.chart1.xAxis
//        xAxis.textSize = 9f
//        xAxis.yOffset = 0f
//        xAxis.xOffset = 0f
//        val labels = ArrayList<String>()
//        labels.add("data1")
//        labels.add("data2")
//        labels.add("data3")
//        labels.add("data4")
//        labels.add("data5")
//        labels.add("data6")
//        labels.add("data7")
//        labels.add("data8")
//
//        val formatData = IndexAxisValueFormatter(labels)
//        xAxis.valueFormatter = formatData
//
//        xAxis.textColor = Color.WHITE
//
//        val yAxis: YAxis = binding.chart1.yAxis
//        yAxis.setLabelCount(8, false)
//        yAxis.textSize = 9f
//        yAxis.axisMinimum = 0f
//        //yAxis.axisMaximum = 100f
//        yAxis.setDrawLabels(false)
//
//        val l: Legend = binding.chart1.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
//        l.xEntrySpace = 7f
//        l.yEntrySpace = 5f
//        l.textColor = Color.WHITE
//
//    }
//
//    private fun setData() {
//        val mul = 80f
//        val min = 20f
//        val cnt = 8
//
//        val entries2: ArrayList<RadarEntry> = ArrayList()
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (i in 0 until cnt) {
//            val val1 = (Math.random() * mul).toFloat() + min
//            entries1.add(RadarEntry(val1))
//            val val2 = (Math.random() * mul).toFloat() + min
//            entries2.add(RadarEntry(val2))
//        }
//        val set1 = RadarDataSet(entries1, "Last Week")
//        set1.color = Color.rgb(103, 110, 129)
//        set1.fillColor = Color.rgb(103, 110, 129)
//        set1.setDrawFilled(true)
//        set1.fillAlpha = 180
//        set1.lineWidth = 2f
//        set1.isDrawHighlightCircleEnabled = true
//        set1.setDrawHighlightIndicators(false)
////        val set2 = RadarDataSet(entries2, "This Week")
////        set2.color = Color.rgb(121, 162, 175)
////        set2.fillColor = Color.rgb(121, 162, 175)
////        set2.setDrawFilled(true)
////        set2.fillAlpha = 180
////        set2.lineWidth = 2f
////        set2.isDrawHighlightCircleEnabled = true
////        set2.setDrawHighlightIndicators(false)
//        val sets: ArrayList<IRadarDataSet> = ArrayList()
//        sets.add(set1)
//        // sets.add(set2)
//        val data = RadarData(sets)
//        data.setValueTextSize(8f)
//        data.setDrawValues(false)
//        data.setValueTextColor(Color.WHITE)
//        binding.chart1.data = data
//        binding.chart1.invalidate()
//    }
//
//    private fun initButtonListeners() {
//        binding.btnConnect.setOnClickListener {
//            try {
//                neuroSky.connect()
//            } catch (e: BluetoothNotEnabledException) {
//                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
//                    .show()
//                Log.d(LOG_TAG, "" + e.message)
//            }
//        }
//
//        binding.btnDisconnect.setOnClickListener {
//            neuroSky.disconnect()
//        }
//
//        binding.btnStartMonitoring.setOnClickListener {
//            neuroSky.startMonitoring()
//        }
//
//        binding.btnStopMonitoring.setOnClickListener {
//            neuroSky.stopMonitoring()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (neuroSky.isRawSignalEnabled) {
//            neuroSky.startMonitoring()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (neuroSky.isRawSignalEnabled) {
//            neuroSky.stopMonitoring()
//        }
//    }
//
//
//

//
//    private fun checkData(radarEntry: Float): Boolean {
//        return radarEntry <= 150
//
//    }
//
//    private fun InsertData(){
//        var appDatabase : AppDatabase? = AppDatabase.getInstance(context)
//
//        val mindWaveEntity = MindWaveEntity()
//        mindWaveEntity.MEDITATION = BindMindWave[0] / Counting[0]
//        mindWaveEntity.ATTENTION = BindMindWave[1] / Counting[1]
//        mindWaveEntity.DELTA = BindMindWave[2] / Counting[2]
//        mindWaveEntity.THETA = BindMindWave[3] / Counting[3]
//        mindWaveEntity.LOW_ALPHA = BindMindWave[4] / Counting[4]
//        mindWaveEntity.HIGH_ALPHA = BindMindWave[5] / Counting[5]
//        mindWaveEntity.LOW_BETA = BindMindWave[6] / Counting[6]
//        mindWaveEntity.HIGH_BETA = BindMindWave[7] / Counting[7]
//        mindWaveEntity.LOW_GAMMA = BindMindWave[8] / Counting[8]
//        mindWaveEntity.MID_GAMMA = BindMindWave[9] / Counting[9]
//
//        Log.d("TAG1234", mindWaveEntity.TIMESTAMP)
//        mindWaveEntity.TIMESTAMP = SimpleDateFormat("yyyyMMddHHmmss", Locale("ko", "KR")).format(
//            Date(System.currentTimeMillis())
//        )
//        Log.d("TAG1234", mindWaveEntity.ATTENTION.toString())
//        Log.d("TAG1234", mindWaveEntity.HIGH_BETA.toString())
//        Log.d("TAG1234", mindWaveEntity.LOW_GAMMA.toString())
//        Log.d("TAG1234", mindWaveEntity.HIGH_ALPHA.toString())
//        var res = appDatabase?.dao()?.insert(mindWaveEntity)
//        Log.d("TAG1234", res.toString())
//
//        Log.d("TAG1234", "datas : " +appDatabase?.dao()?.getAll()?.size)
//    }
//}