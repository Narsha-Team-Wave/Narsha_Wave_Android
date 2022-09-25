package kr.hs.dgsw.noepa_ls.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentConnectBinding
import kr.hs.dgsw.noepa_ls.databinding.FragmentMesureBinding

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

}