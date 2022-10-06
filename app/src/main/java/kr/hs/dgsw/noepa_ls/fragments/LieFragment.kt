package kr.hs.dgsw.noepa_ls.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.Completable.timer
import kr.hs.dgsw.noepa_ls.MainActivity
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentLieBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentMesureBinding
import kotlin.concurrent.timer

class LieFragment : Fragment() {
    private var mainActivity: ScreenActivity? = null
    private var mBinding: FragmentLieBinding? = null
    private val binding get() = mBinding!!
    var livedata: MutableLiveData<Int> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentLieBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

        val measure = MediaPlayer.create(activity, R.raw.measure);
        measure.start()
        measure.setOnCompletionListener {
            it.start()
        }
        var over = false
        livedata.observe(mainActivity!!, Observer {
            Log.d("countTag", it.toString())
            if (it >= 70) {
                over = true
            }
        })

        val countDown = object : CountDownTimer(1000 * 10, 1000) {
            override fun onTick(p0: Long) {
                binding.countTv.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                measure.stop()
                if (over) {
                    val lie = MediaPlayer.create(activity, R.raw.lie);
                    lie.start()
                    lie.setOnCompletionListener {
                        it.stop()
                    }
                    binding.imgBrain.setImageResource(R.drawable.lie_brain)
                    binding.etTruthLie.setText("당신은 거짓말을 했어요!")
                } else {
                    val truth = MediaPlayer.create(activity, R.raw.truth);
                    truth.start()
                    truth.setOnCompletionListener {
                        it.stop()
                    }
                    binding.imgBrain.setImageResource(R.drawable.truth_brain)
                    binding.etTruthLie.setText("당신의 말은 진실이군요!")
                }
            }
        }.start()


        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}