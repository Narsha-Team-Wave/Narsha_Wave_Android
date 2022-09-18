package kr.hs.dgsw.noepa_ls.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentConnectBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentLoginBinding

class ConnectFragment : Fragment() {
    private var mainActivity: ScreenActivity? = null
    private var mBinding: FragmentConnectBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentConnectBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity!!.connectNeuroSky()

    }


    public fun changeView(){
        //Log.d("Tag", "Tag")
        Glide.with(this).load(R.drawable.brain_connect).into(binding.ivBrain)
        binding.tvConnect.text = "기기가 연결되었습니다."
    }
}