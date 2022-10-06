package kr.hs.dgsw.noepa_ls.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
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

    val MY_PERMISSION_ACCESS_ALL = 100

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

        settingSwitch()

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


        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun settingSwitch() {
        binding.switchConnect.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.switchConnect.isChecked = true
                }
                MotionEvent.ACTION_UP -> {
                    binding.switchConnect.isChecked = false
                    mainActivity!!.changeFragment(mainActivity!!.CONNECT_SCREEN)
                }
                else -> {
                    binding.switchConnect.isChecked = false
                }
            }
            return@setOnTouchListener true
        }

        binding.switchMeasure.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.switchMeasure.isChecked = true
                }
                MotionEvent.ACTION_UP -> {
                    binding.switchMeasure.isChecked = false
                    mainActivity!!.changeFragment(mainActivity!!.MEASURE_SCREEN)
                }
                else -> {
                    binding.switchMeasure.isChecked = false
                }
            }
            return@setOnTouchListener true
        }

        binding.switchHistory.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.switchHistory.isChecked = true
                }
                MotionEvent.ACTION_UP -> {
                    binding.switchHistory.isChecked = false
                    //mainActivity!!.changeFragment(mainActivity!!.CONNECT_SCREEN)
                }
                else -> {
                    binding.switchHistory.isChecked = false
                }
            }
            return@setOnTouchListener true
        }

        binding.switchLie.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.switchLie.isChecked = true
                }
                MotionEvent.ACTION_UP -> {
                    binding.switchLie.isChecked = false
                    mainActivity!!.changeFragment(mainActivity!!.LIE_SCREEN)
                }
                else -> {
                    binding.switchLie.isChecked = false
                }
            }
            return@setOnTouchListener true
        }
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