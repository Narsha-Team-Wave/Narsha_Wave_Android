package kr.hs.dgsw.noepa_ls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.pwittchen.neurosky.library.NeuroSky
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import com.github.pwittchen.neurosky.library.message.enums.State
import kr.hs.dgsw.noepa_ls.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "NeuroSky"
    }

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var neuroSky: NeuroSky

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

//        setContentView(R.layout.testnuro)
        setContentView( binding.root)


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



    }
    private fun initButtonListeners() {
        binding.btnConnect.setOnClickListener() {
            try {
                neuroSky.connect()
            } catch (e: BluetoothNotEnabledException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT)
                    .show()
                Log.d(LOG_TAG, ""+e.message)
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
        when (signal) {
            Signal.ATTENTION -> binding.tvAttention.text = getFormattedMessage("attention: %d", signal)
            Signal.MEDITATION -> binding.tvMeditation.text = getFormattedMessage("meditation: %d", signal)
            Signal.BLINK -> binding.tvBlink.text = getFormattedMessage("blink: %d", signal)
            else -> Log.d(LOG_TAG, "unhandled signal")
        }

        Log.d(LOG_TAG, String.format("%s: %d", signal.toString(), signal.value))
    }

    private fun getFormattedMessage(
        messageFormat: String,
        signal: Signal
    ): String {
        return String.format(Locale.getDefault(), messageFormat, signal.value)
    }

    private fun handleBrainWavesChange(brainWaves: Set<BrainWave>) {
        for (brainWave in brainWaves) {
            Log.d(LOG_TAG, String.format("%s: %d", brainWave.toString(), brainWave.value))
        }
    }
}