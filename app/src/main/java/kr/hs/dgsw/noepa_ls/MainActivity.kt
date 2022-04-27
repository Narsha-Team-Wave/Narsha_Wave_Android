package kr.hs.dgsw.noepa_ls

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.github.pwittchen.neurosky.library.NeuroSky
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import com.github.pwittchen.neurosky.library.message.enums.State
import kr.hs.dgsw.noepa_ls.custom.RadarMarkerView
import kr.hs.dgsw.noepa_ls.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    var check = false
    var checkAttenTion: Int = 0
    var checkMeditation: Int = 0
    var checkBlink: Int = 0

    companion object {
        const val LOG_TAG = "NeuroSky"
    }

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var neuroSky: NeuroSky

    val entries1: ArrayList<RadarEntry> = ArrayList()

    private var brainWaveList: IntArray = IntArray(8)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

//        setContentView(R.layout.testnuro)
        setContentView(binding.root)


        neuroSky = createNeuroSky()
        initButtonListeners()


//        val neuroSky = NeuroSky(object : ExtendedDeviceMessageListener() {
//            override fun onStateChange(state: State) {
//                handleStateChange(state)
//            }
//
//            override fun onSignalChange(signal: Signal) {
//                handleSignalChange(signal)
//            }
//
//            override fun onBrainWavesChange(brainWaves: Set<BrainWave>) {
//                handleBrainWavesChange(brainWaves)
//            }
//        })
        initChart();

    }

    private fun initChart(){
        Log.d(LOG_TAG, "initChart")
        binding.chart1.setBackgroundColor(Color.rgb(60, 65, 82));

        binding.chart1.getDescription().setEnabled(false);

        binding.chart1.setWebLineWidth(1f);
        binding.chart1.setWebColor(Color.LTGRAY);
        binding.chart1.setWebLineWidthInner(1f);
        binding.chart1.setWebColorInner(Color.LTGRAY);
        binding.chart1.setWebAlpha(100)

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it

        val mv: MarkerView = RadarMarkerView(this, R.layout.radar_markerview)
        mv.chartView = binding.chart1 // For bounds control

        binding.chart1.setMarker(mv) // Set the marker to the chart
        setData()
        val xAxis: XAxis = binding.chart1.getXAxis()
        xAxis.textSize = 9f
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        val labels = ArrayList<String>()
        labels.add("data1")
        labels.add("data2")
        labels.add("data3")
        labels.add("data4")
        labels.add("data5")
        labels.add("data6")
        labels.add("data7")
        labels.add("data8")

        val formatData = IndexAxisValueFormatter(labels)
        xAxis.valueFormatter = formatData

        xAxis.textColor = Color.WHITE

        val yAxis: YAxis = binding.chart1.getYAxis()
        yAxis.setLabelCount(8, false)
        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 80f
        yAxis.setDrawLabels(false)

        val l: Legend = binding.chart1.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(5f)
        l.setTextColor(Color.WHITE)

    }
    private fun setData() {
        val mul = 80f
        val min = 20f
        val cnt = 8

        val entries2: ArrayList<RadarEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until cnt) {
            val val1 = (Math.random() * mul).toFloat() + min
            entries1.add(RadarEntry(val1))
            val val2 = (Math.random() * mul).toFloat() + min
            entries2.add(RadarEntry(val2))
        }
        val set1 = RadarDataSet(entries1, "Last Week")
        set1.color = Color.rgb(103, 110, 129)
        set1.fillColor = Color.rgb(103, 110, 129)
        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.isDrawHighlightCircleEnabled = true
        set1.setDrawHighlightIndicators(false)
//        val set2 = RadarDataSet(entries2, "This Week")
//        set2.color = Color.rgb(121, 162, 175)
//        set2.fillColor = Color.rgb(121, 162, 175)
//        set2.setDrawFilled(true)
//        set2.fillAlpha = 180
//        set2.lineWidth = 2f
//        set2.isDrawHighlightCircleEnabled = true
//        set2.setDrawHighlightIndicators(false)
        val sets: ArrayList<IRadarDataSet> = ArrayList()
        sets.add(set1)
       // sets.add(set2)
        val data = RadarData(sets)
        data.setValueTextSize(8f)
        data.setDrawValues(false)
        data.setValueTextColor(Color.WHITE)
        binding.chart1.setData(data)
        binding.chart1.invalidate()
    }

    private fun initButtonListeners() {
        binding.btnConnect.setOnClickListener() {
            try {
                neuroSky.connect()
            } catch (e: BluetoothNotEnabledException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT)
                    .show()
                Log.d(LOG_TAG, "" + e.message)
            }
        }

        binding.btnDisconnect.setOnClickListener() {
            neuroSky.disconnect()
        }

        binding.btnStartMonitoring.setOnClickListener() {
            neuroSky.startMonitoring()
        }

        binding.btnStopMonitoring.setOnClickListener() {
            neuroSky.stopMonitoring()
        }
    }

    override fun onResume() {
        super.onResume()
        if (neuroSky.isRawSignalEnabled) {
            neuroSky.startMonitoring()
        }
    }

    override fun onPause() {
        super.onPause()
        if (neuroSky.isRawSignalEnabled) {
            neuroSky.stopMonitoring()
        }
    }

    private fun createNeuroSky(): NeuroSky {
        return NeuroSky(object : ExtendedDeviceMessageListener() {
            override fun onStateChange(state: State) {
                handleStateChange(state)
            }

            override fun onSignalChange(signal: Signal) {
                handleSignalChange(signal)
            }

            override fun onBrainWavesChange(brainWaves: Set<BrainWave>) {
                handleBrainWavesChange(brainWaves)
            }
        })
    }

    private fun handleStateChange(state: State) {
        if (state == State.CONNECTED) {
            neuroSky.startMonitoring()
        }

        binding.tvState.text = state.toString()
        Log.d(LOG_TAG, state.toString())
    }

    private fun handleSignalChange(signal: Signal) {
        var num = getFormattedMessage("%d", signal).toInt()
        when (signal) {
            Signal.ATTENTION -> if (num != checkAttenTion) {
                binding.tvAttention.text = getFormattedMessage("attention: %d", signal)
                checkAttenTion = num
                check = true
            } else check = false
            Signal.MEDITATION -> if (num != checkMeditation) {
                binding.tvMeditation.text = getFormattedMessage("meditation: %d", signal)
                checkMeditation = num
                check = true
            } else check = false
            Signal.BLINK -> if (num != checkBlink) {
                binding.tvBlink.text = getFormattedMessage("blink: %d", signal)
                checkBlink = num
                check = true
            } else check = false
            else -> Log.d(LOG_TAG, "unhandled signal")


        }
        check = checkAttenTion != 0 || checkMeditation != -0

        Log.d(LOG_TAG, String.format("%s: %d", signal.toString(), signal.value))
    }

    private fun getFormattedMessage(
        messageFormat: String,
        signal: Signal,
    ): String {
        return String.format(Locale.getDefault(), messageFormat, signal.value)
    }

    private fun handleBrainWavesChange(brainWaves: Set<BrainWave>) {
        Log.d(LOG_TAG, "check : " + check)
        for (brainWave in brainWaves) {
            Log.d(LOG_TAG, String.format("test %s: %d", brainWave.toString(), brainWave.value))

            if (check) {
                if (brainWave.value < 1000000 && brainWave.value != 0) {
                    when (brainWave.toString()) {
                        "DELTA" -> {
                            binding.tvDelta.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[0] != brainWave.value){
                                brainWaveList[0] = brainWave.value
                                entries1[0] = RadarEntry(brainWave.value.toFloat() * 100/400000)
                                Log.d(LOG_TAG + "1",  entries1[0].toString())
                            }
                        }
                        "THETA" -> {
                            binding.tvTheta.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[1] != brainWave.value) {
                                brainWaveList[1] = brainWave.value
                                entries1[1] = RadarEntry(brainWave.value.toFloat() * 100/45000)
                            }
                        }
                        "LOW_ALPHA" -> {
                            binding.tvLowalpha.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[2] != brainWave.value) {
                                brainWaveList[2] = brainWave.value
                                entries1[2] = RadarEntry(brainWave.value.toFloat() * 100/10000)
                            }
                        }
                        "HIGH_ALPHA" -> {
                            binding.tvHighalpha.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[3] != brainWave.value) {
                                brainWaveList[3] = brainWave.value
                                entries1[3] = RadarEntry(brainWave.value.toFloat() * 100/15000)
                            }
                        }
                        "LOW_BETA" -> {
                            binding.tvLowbeta.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[4] != brainWave.value) {
                                brainWaveList[4] = brainWave.value
                                entries1[4] = RadarEntry(brainWave.value.toFloat() * 100/18000)
                            }
                        }
                        "HIGH_BETA" -> {
                            binding.tvHighbeta.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[5] != brainWave.value) {
                                brainWaveList[5] = brainWave.value
                                entries1[5] = RadarEntry(brainWave.value.toFloat() * 100/24000)
                            }
                        }
                        "LOW_GAMMA" -> {
                            binding.tvLowgamma.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[6] != brainWave.value) {
                                brainWaveList[6] = brainWave.value
                                entries1[6] = RadarEntry(brainWave.value.toFloat() * 100/10000)
                            }
                        }
                        "MID_GAMMA" -> {
                            binding.tvMidgamma.text =
                                brainWave.toString() + ": " + brainWave.value.toString()
                            if(brainWaveList[7] != brainWave.value) {
                                brainWaveList[7] = brainWave.value
                                entries1[7] = RadarEntry(brainWave.value.toFloat() * 100/10000)
                            }
                        }
                        else -> Log.d(LOG_TAG, "unhandled signal")
                    }
                    var LogText = ""
                    for(i in 0..7) {
                        LogText = LogText + " " + brainWaveList[i].toString()
                    }
                    Log.d("LOG_TAG", LogText)
                    binding.chart1.notifyDataSetChanged()
                    binding.chart1.invalidate()
                }
            }
        }
    }
}