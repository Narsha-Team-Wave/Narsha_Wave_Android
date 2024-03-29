package kr.hs.dgsw.noepa_ls.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.hs.dgsw.noepa_ls.App
import kr.hs.dgsw.noepa_ls.Prefs
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentLoginBinding
import kr.hs.dgsw.noepa_ls.util.NoepaUtil

class LoginFragment : Fragment() {
    private var mainActivity: ScreenActivity? = null
    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!

    private var num: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mainActivity = (activity as ScreenActivity)

        binding.NextBtn.setOnClickListener {
            if(binding.etId.text.isNullOrBlank()){
                Toast.makeText(activity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                // TODO            NoepaUtil.sendSMS("01038540977","김상추", context)
            }
            else {
                App.prefs.id = binding.etId.text.toString()
                mainActivity!!.changeFragment(mainActivity!!.MAIN_SCREEN)
            }
        }

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.prefs = Prefs(requireContext())
        if(App.prefs.id != null){
            mainActivity!!.changeFragment(mainActivity!!.MAIN_SCREEN)
        }
    }
}