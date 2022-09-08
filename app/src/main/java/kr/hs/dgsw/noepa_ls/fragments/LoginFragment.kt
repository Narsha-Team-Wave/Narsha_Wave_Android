package kr.hs.dgsw.noepa_ls.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.hs.dgsw.noepa_ls.App
import kr.hs.dgsw.noepa_ls.Prefs
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentLoginBinding
import kr.hs.dgsw.noepa_ls.util.NoepaUtil

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var DID: String? = null
    private var param2: String? = null

    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.NextBtn.setOnClickListener {
            if(binding.etId.text.isNullOrBlank()){
                Toast.makeText(activity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                // TODO            NoepaUtil.sendSMS("01038540977","김상추", context)
            }
            else {
                var main = (activity as ScreenActivity)
                App.prefs.id = binding.etId.text.toString()
                main.changeFragment(main.MAIN_SCREEN)
            }
        }

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.prefs = Prefs(requireContext())
        if(App.prefs.id != null){
            var main = (activity as ScreenActivity)
            main.changeFragment(main.MAIN_SCREEN)
        }
    }
}