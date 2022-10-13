package kr.hs.dgsw.noepa_ls.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.pwittchen.neurosky.library.NeuroSky
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import com.github.pwittchen.neurosky.library.message.enums.State
import kr.hs.dgsw.noepa_ls.MainActivity
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.databinding.ActivityScreenBinding
import kr.hs.dgsw.noepa_ls.fragments.*
import java.util.*

class ScreenActivity : AppCompatActivity() {
    var connect = false
    private var mBinding: ActivityScreenBinding? = null
    private lateinit var neuroSky: NeuroSky
    private val binding get() = mBinding!!
    private var mfragment : Fragment? = null
    var check = false

    private val max = IntArray(8)

    val LOGIN_SCREEN = 0
    val MAIN_SCREEN = 1
    val CONNECT_SCREEN = 2
    val MEASURE_SCREEN = 3
    val LIE_SCREEN = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for(i in 0 until 8){
            max[i] = 0
        }
        neuroSky = NeuroSky(object : ExtendedDeviceMessageListener() {
            override fun onStateChange(state: State) {
                Log.d(MainActivity.LOG_TAG + "aaa", state.toString())
                handleStateChange(state)
            }

            override fun onSignalChange(signal: Signal) {
                Log.d(MainActivity.LOG_TAG + "bbb", signal.toString())
                handleSignalChange(signal)
            }

            override fun onBrainWavesChange(brainWaves: Set<BrainWave>) {
                Log.d(MainActivity.LOG_TAG+ "ccc", brainWaves.toString())
                handleBrainWavesChange(brainWaves)
            }
        })
        setFragment()

    }


    override fun onDestroy() {
        super.onDestroy()
        neuroSky.disconnect()
    }

    override fun onBackPressed() {
        when (mfragment) {
            is Mainfragment -> {
                finish()
            }
            is LoginFragment -> {
                finish()
            }
            else -> {
                changeFragment(MAIN_SCREEN)
            }
        }
    }




    private fun setFragment() {
        //첫 프래그먼트는 무조건 ADD가 한개 이상 필요함
        var transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentLayout, LoginFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun changeFragment(screenNum: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        //프래그먼트 교체는 replace로 현재 fragment layout을 다른 프래그 먼트로 대체 한다.
        when (screenNum) {
            0 -> {
                mfragment = LoginFragment()
                neuroSky.disconnect()
                transaction.replace(R.id.fragmentLayout, mfragment!!)
            }
            1 -> {
                mfragment = Mainfragment()
                transaction.replace(R.id.fragmentLayout, mfragment!!)
            }
            2 -> {
               mfragment = ConnectFragment()
               transaction.replace(R.id.fragmentLayout, mfragment!!)
            }
            3 -> {
                if(connect){
                    mfragment = MeasureFragment()
                    transaction.replace(R.id.fragmentLayout, mfragment!!)
                } else {
                    Toast.makeText(this, "기기를 연결한 후 시도해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
            5 -> {
                if(connect){
                    mfragment = LieFragment()
                    transaction.replace(R.id.fragmentLayout, mfragment!!)
                } else {
                    Toast.makeText(this, "기기를 연결한 후 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun connectNeuroSky() {
        try {
            neuroSky.connect()
            if(mfragment is ConnectFragment){
                (mfragment as ConnectFragment).changeView()
            }
            connect = true
        } catch (e: BluetoothNotEnabledException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            Log.d(MainActivity.LOG_TAG, "" + e.message)
            connect = false
        }
        if(connect){
            Toast.makeText(this, "기기 연결 성공", Toast.LENGTH_SHORT).show()
            successConnect()
            changeFragment(MAIN_SCREEN)
        }
    }

    private fun successConnect() {
        val connect = MediaPlayer.create(this, R.raw.connect);
        connect.start()
        connect.setOnCompletionListener {
            it.stop()
        }
    }

    private fun handleStateChange(state: State) {
        if (state == State.CONNECTED) {
            neuroSky.startMonitoring()
        }
        Log.d(MainActivity.LOG_TAG, state.toString())
    }

    private fun handleSignalChange(signal: Signal) {
        var checkattention = 0
        var num = getFormattedMessage("%d", signal).toInt()
        when (signal) {
            Signal.ATTENTION -> {
                Log.d(MainActivity.LOG_TAG, signal.type.toString() + ":" + num)
                runOnUiThread {
                    if(mfragment is MeasureFragment){
                        (mfragment as MeasureFragment).addEntry2(num.toDouble())
                    } else if(mfragment is Mainfragment) {
                        if(checkattention == num) {
                            check = false
                            (mfragment as Mainfragment).changeImg()
                        } else {
                            check = true
                            (mfragment as Mainfragment).changeGif()
                            checkattention = num
                        }
                    }
                }
            }
            Signal.MEDITATION -> {
                Log.d(MainActivity.LOG_TAG, signal.type.toString() + ":" + num)
                runOnUiThread {
                    if(mfragment is MeasureFragment){
                        (mfragment as MeasureFragment).addEntry1(num.toDouble())
                    }
                }
                if(mfragment is LieFragment){
                    (mfragment as LieFragment).livedata.value = num
                }
            }
            Signal.BLINK -> {
                Log.d(MainActivity.LOG_TAG, signal.type.toString() + ":" + num)
                runOnUiThread {
                    if(mfragment is MeasureFragment){
                        (mfragment as MeasureFragment).addEntry3(num.toDouble())
                    }
                }
            }
            else -> Log.d(MainActivity.LOG_TAG, "unhandled signal")
        }
        Log.d(MainActivity.LOG_TAG, String.format("%s: %d", signal.toString(), signal.value))
    }

    private fun getFormattedMessage(
        messageFormat: String,
        signal: Signal,
    ): String {
        return String.format(Locale.getDefault(), messageFormat, signal.value)
    }

    private fun handleBrainWavesChange(brainWaves: Set<BrainWave>) {
        for (brainWave in brainWaves) {
            Log.d(MainActivity.LOG_TAG, String.format("test %s: %d", brainWave.toString(), brainWave.value)
            )
            if (brainWave.value < 1000000 && brainWave.value != 0) {
                when (brainWave.toString()) {
                    "DELTA" -> {
                        if(max[0] < brainWave.value){
                            max[0] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[0], 0)
                        }
                    }
                    "THETA" -> {
                        if(max[1] < brainWave.value){
                            max[1] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[1], 1)
                        }
                    }
                    "LOW_ALPHA" -> {
                        if(max[2] < brainWave.value){
                            max[2] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[2], 2)
                        }
                    }
                    "HIGH_ALPHA" -> {
                        if(max[3] < brainWave.value){
                            max[3] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[3], 3)
                        }
                    }
                    "LOW_BETA" -> {
                        if(max[4] < brainWave.value){
                            max[4] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[4], 4)
                        }
                    }
                    "HIGH_BETA" -> {
                        if(max[5] < brainWave.value){
                            max[5] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[5], 5)
                        }
                    }
                    "LOW_GAMMA" -> {
                        if(max[6] < brainWave.value){
                            max[6] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[6], 6)
                        }
                    }
                    "MID_GAMMA" -> {
                        if(max[7] < brainWave.value){
                            max[7] = brainWave.value
                        }
                        if(mfragment is MeasureFragment){
                            (mfragment as MeasureFragment).addData(brainWave.value.toFloat() / max[7], 7)
                        }
                    }
                    else -> Log.d(MainActivity.LOG_TAG, "unhandled signal")
                }

            }
        }
    }
}