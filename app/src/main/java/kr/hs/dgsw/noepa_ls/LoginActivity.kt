package kr.hs.dgsw.noepa_ls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.hs.dgsw.noepa_ls.databinding.ActivityLoginBinding
import kr.hs.dgsw.noepa_ls.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.NextBtn.setOnClickListener {
            if(binding.IDET.text.isNullOrBlank()){
                Toast.makeText(this, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("ID", binding.IDET.text.toString())
                startActivity(intent)
            }
        }
    }
}