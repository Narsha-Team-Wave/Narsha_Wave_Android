package kr.hs.dgsw.noepa_ls.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.hs.dgsw.noepa_ls.activity.ScreenActivity
import kr.hs.dgsw.noepa_ls.databinding.FragmentLoginBinding
import kr.hs.dgsw.noepa_ls.util.NoepaUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "DID"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var DID: String? = null
    private var param2: String? = null

    private var mBinding: FragmentLoginBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            DID = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        //mBinding = FragmentLoginBinding.inflate(layoutInflater)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 1. 뷰 바인딩 설정
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.NextBtn.setOnClickListener {
            if(binding.IDET.text.isNullOrBlank()){
                Toast.makeText(activity, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show()
                NoepaUtil.sendSMS("01095491337","안녕 되네", context);
            }
            else {
                var main = (activity as ScreenActivity);
                main.setDid(binding.IDET.text.toString());
                main.changeFragment(main.MAIN_SCREEN );
            }
        }

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}