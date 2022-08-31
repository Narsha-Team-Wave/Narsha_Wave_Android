package kr.hs.dgsw.noepa_ls.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import kr.hs.dgsw.noepa_ls.R
import kr.hs.dgsw.noepa_ls.databinding.ActivityScreenBinding
import kr.hs.dgsw.noepa_ls.fragments.LoginFragment
import kr.hs.dgsw.noepa_ls.fragments.Mainfragment

class ScreenActivity : AppCompatActivity() {

    private var mBinding: ActivityScreenBinding? = null
    private val binding get() = mBinding!!

    val LOGIN_SCREEN = 0;
    val MAIN_SCREEN =1;

    var DID = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment()

    }

    fun setFragment(){
        //첫 프래그먼트는 무조건 ADD가 한개 이상 필요함
        var transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentLayout, LoginFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun setDid(did: String){
        DID = did
    }

    public fun changeFragment(screenNum : Int){
        var transaction = supportFragmentManager.beginTransaction()
        //프래그먼트 교체는 replace로 현재 fragment layout을 다른 프래그 먼트로 대체 한다.
        when(screenNum){
            0 ->{
                transaction.replace(R.id.fragmentLayout, LoginFragment())
            }
            1 -> {
                val bundle = Bundle();
                bundle.putString("DID",this.DID);
                var fragment = Mainfragment()
                fragment.arguments = bundle
                transaction.replace(R.id.fragmentLayout, fragment)
            }

        }
        transaction.addToBackStack(null)
        transaction.commit()
    }
}