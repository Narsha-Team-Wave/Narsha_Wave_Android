package kr.hs.dgsw.noepa_ls.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.pwittchen.neurosky.library.NeuroSky
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import com.github.pwittchen.neurosky.library.message.enums.State
import kr.hs.dgsw.noepa_ls.MainActivity
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.databinding.ActivityScreenBinding
import kr.hs.dgsw.noepa_ls.fragments.ConnectFragment
import kr.hs.dgsw.noepa_ls.fragments.LoginFragment
import kr.hs.dgsw.noepa_ls.fragments.Mainfragment
import java.util.*

class ScreenActivity : AppCompatActivity() {

    private var mBinding: ActivityScreenBinding? = null
    private lateinit var neuroSky: NeuroSky
    private val binding get() = mBinding!!
    private var mfragment : Fragment? = null

    val LOGIN_SCREEN = 0
    val MAIN_SCREEN = 1
    val CONNECT_SCREEN = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        neuroSky = createNeuroSky()
        setFragment()

    }

    override fun onBackPressed() {
        if(mfragment is Mainfragment){
            finish()
        } else if(mfragment is LoginFragment){
            finish()
        }
        else {
            changeFragment(MAIN_SCREEN)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        neuroSky.disconnect()
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
                transaction.replace(R.id.fragmentLayout, mfragment!!)
            }
            1 -> {
                //val bundle = Bundle();
                //bundle.putString("DID",this.DID);
                mfragment = Mainfragment()
                //fragment.arguments = bundle
                transaction.replace(R.id.fragmentLayout, mfragment!!)
            }
            2 -> {
               mfragment = ConnectFragment()
               transaction.replace(R.id.fragmentLayout, mfragment!!)
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
            Toast.makeText(this, "기기 연결 성공", Toast.LENGTH_SHORT).show()
        } catch (e: BluetoothNotEnabledException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            Log.d(MainActivity.LOG_TAG, "" + e.message)
        }
    }

    private fun handleStateChange(state: State) {
        if (state == State.CONNECTED) {
            neuroSky.startMonitoring()
        }
        Log.d(MainActivity.LOG_TAG, state.toString())
    }

    private fun handleSignalChange(signal: Signal) {

        var num = getFormattedMessage("%d", signal).toInt()
        when (signal) {
            Signal.ATTENTION -> {
//                binding.tvAttention.text = getFormattedMessage("attention: %d", signal)
//                runOnUiThread { addEntry2(checkAttenTion.toDouble()) }
            }
            Signal.MEDITATION -> {
//                binding.tvMeditation.text = getFormattedMessage("meditation: %d", signal)
//                runOnUiThread { addEntry1(checkMeditation.toDouble()) }
            }
            Signal.BLINK -> {
//                binding.tvBlink.text = getFormattedMessage("blink: %d", signal)
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
            Log.d(
                MainActivity.LOG_TAG,
                String.format("test %s: %d", brainWave.toString(), brainWave.value)
            )
            if (brainWave.value < 1000000 && brainWave.value != 0) {
                when (brainWave.toString()) {
                    "DELTA" -> {
//                        binding.tvDelta.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
//                            Log.d(MainActivity.LOG_TAG, entries1[0].toString())

                    }
                    "THETA" -> {
//                        binding.tvTheta.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "LOW_ALPHA" -> {
//                        binding.tvLowalpha.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "HIGH_ALPHA" -> {
//                        binding.tvHighalpha.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "LOW_BETA" -> {
//                        binding.tvLowbeta.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "HIGH_BETA" -> {
//                        binding.tvHighbeta.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "LOW_GAMMA" -> {
//                        binding.tvLowgamma.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    "MID_GAMMA" -> {
//                        binding.tvMidgamma.text =
//                            brainWave.toString() + ": " + brainWave.value.toString()
                    }
                    else -> Log.d(MainActivity.LOG_TAG, "unhandled signal")
                }


//                Log.d("LOG_TAG", entries1.toString())
//                Log.d("LOG_TAG", "data3 +" + entries1.size)
//                binding.chart1.notifyDataSetChanged()
//                binding.chart1.invalidate()
            }
        }
    }
}