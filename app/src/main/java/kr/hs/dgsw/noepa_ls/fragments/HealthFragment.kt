package kr.hs.dgsw.noepa_ls.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentHealthBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentMesureBinding

class HealthFragment : Fragment() {
    private var mainActivity: ScreenActivity? = null
    private var mBinding: FragmentHealthBinding? = null
    private val binding get() = mBinding!!
    var livedata: MutableLiveData<Int> = MutableLiveData()
    private var countDown: CountDownTimer? = null
    private var measure: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentHealthBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startMeasure()
    }

    private fun startMeasure() {
        var list = mutableListOf<Int>()
        measure = MediaPlayer.create(activity, R.raw.measure);
        measure!!.start()
        measure!!.setOnCompletionListener {
            it.start()
        }
        livedata.observe(mainActivity!!, Observer {
            Log.d("countTag", it.toString())
            list.add(it)
        })

        countDown = object : CountDownTimer(1000 * 30, 1000) {
            override fun onTick(p0: Long) {
                binding.countTv.text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                measure!!.stop()
                var avg = list[0]
                var badunder = 0
                var badover = 0
                var sosounder = 0
                var sosoover = 0
                for(i in 1 until list.size) {
                    avg+=list[i]
                }
                avg /= list.size
                for(i in list.indices) {
                    if(list[i] > avg * 1.3){
                        sosoover++
                        if(list[i] > avg * 1.5){
                            badover++
                        }
                    } else if(list[i] < avg * 0.7){
                        sosounder++
                        if(list[i] < avg * 0.5){
                            badunder++
                        }
                    }
                }
                if(badover+badunder > 20){
                    binding.measureText.text = "정신이 매우 불안정해요! 휴식을 취하세요."
                    binding.measureText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                } else if(sosoover+sosounder > 20){
                    binding.measureText.text = "정신이 약간 불안정해요! 휴식을 권장드립니다."
                    binding.measureText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                } else {
                    binding.measureText.text = "정신 상태가 매우 양호해요!"
                    binding.measureText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                }

            }
        }.start()
    }


}