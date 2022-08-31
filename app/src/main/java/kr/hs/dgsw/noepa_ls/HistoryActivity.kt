package kr.hs.dgsw.noepa_ls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kr.hs.dgsw.noepa_ls.databinding.ActivityHistoryBinding
import kr.hs.dgsw.noepa_ls.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private var mBinding: ActivityHistoryBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var attention = intent.getStringArrayExtra("attention")
        var delta = intent.getStringArrayExtra("delta")
        var high_alpha = intent.getStringArrayExtra("high_alpha")
        var high_beta = intent.getStringArrayExtra("high_beta")
        var low_alpha = intent.getStringArrayExtra("low_alpha")
        var low_beta = intent.getStringArrayExtra("low_beta")
        var low_gamma = intent.getStringArrayExtra("low_gamma")
        var meditation = intent.getStringArrayExtra("meditation")
        var mid_gamma = intent.getStringArrayExtra("mid_gamma")
        var theta = intent.getStringArrayExtra("theta")

        for(i in 0 until attention!!.size){
            binding.attention.text = binding.attention.text.toString() + "\n" + attention[i]
        }
        for(i in 0 until delta!!.size){
            binding.delta.text = binding.delta.text.toString() + "\n" + delta[i]
        }
        for(i in 0 until high_alpha!!.size){
            binding.highAlpha.text = binding.highAlpha.text.toString() + "\n" + high_alpha[i]
        }
        for(i in 0 until high_beta!!.size){
            binding.highBeta.text = binding.highBeta.text.toString() + "\n" + high_beta[i]
        }
        for(i in 0 until low_alpha!!.size){
            binding.lowAlpha.text = binding.lowAlpha.text.toString() + "\n" + low_alpha[i]
        }
        for(i in 0 until low_beta!!.size){
            binding.lowBeta.text = binding.lowBeta.text.toString() + "\n" + low_beta[i]
        }
        for(i in 0 until low_gamma!!.size){
            binding.lowGamma.text = binding.lowGamma.text.toString() + "\n" + low_gamma[i]
        }
        for(i in 0 until meditation!!.size){
            binding.meditation.text = binding.meditation.text.toString() + "\n" + meditation[i]
        }
        for(i in 0 until mid_gamma!!.size){
            binding.midGamma.text = binding.midGamma.text.toString() + "\n" + mid_gamma[i]
        }
        for(i in 0 until theta!!.size){
            binding.theta.text = binding.theta.text.toString() + "\n" + theta[i]
        }

//        var appDatabase: AppDatabase? = AppDatabase.getInstance(this)
//        val saveddata: List<MindWaveEntity> = appDatabase!!.dao().getAll()
//        var DataList = arrayListOf<MindWaveEntity>()
//        if (saveddata.isNotEmpty()) {
//            DataList.addAll(saveddata)
//        }
//        var MindWave = IntArray(10)
//        for (i in 0 until DataList.size) {
//            MindWave[0] += DataList[i].ATTENTION
//            MindWave[1] += DataList[i].MEDITATION
//            MindWave[2] += DataList[i].DELTA
//            MindWave[3] += DataList[i].HIGH_ALPHA
//            MindWave[4] += DataList[i].HIGH_BETA
//            MindWave[5] += DataList[i].LOW_ALPHA
//            MindWave[6] += DataList[i].LOW_BETA
//            MindWave[7] += DataList[i].LOW_GAMMA
//            MindWave[8] += DataList[i].MID_GAMMA
//            MindWave[9] += DataList[i].THETA
//            binding.timeStamp.text =
//                binding.timeStamp.text.toString() + "\n" + DataList[i].TIMESTAMP
//        }
//        for (i in 0 until 10) {
//            MindWave[i] /= DataList.size
//        }
//
//        binding.attention.text = MindWave[0].toString()
//        binding.meditation.text = MindWave[1].toString()
//        binding.delta.text = MindWave[2].toString()
//        binding.highAlpha.text = MindWave[3].toString()
//        binding.highBeta.text = MindWave[4].toString()
//        binding.lowAlpha.text = MindWave[5].toString()
//        binding.lowBeta.text = MindWave[6].toString()
//        binding.lowGamma.text = MindWave[7].toString()
//        binding.midGamma.text = MindWave[8].toString()
//        binding.theta.text = MindWave[9].toString()
//
//        binding.postBtn.setOnClickListener {
//            JoinPost(
//                MindWave_Data(
//                    binding.attention.text.toString(),
//                    binding.meditation.text.toString(),
//                    binding.delta.text.toString(),
//                    binding.highAlpha.text.toString(),
//                    binding.highBeta.text.toString(),
//                    binding.lowAlpha.text.toString(),
//                    binding.lowBeta.text.toString(),
//                    binding.lowGamma.text.toString(),
//                    binding.midGamma.text.toString(),
//                    binding.theta.text.toString(),
//                    DataList[DataList.size-1].TIMESTAMP
//                )
//            )
//        }
    }

    private fun JoinPost(mindwaveData: MindWave_Data) {
        RetrofitBuilder.api.MindWavePost(mindwaveData).enqueue(object :
            Callback<MindWave_Data> {
            override fun onResponse(
                call: Call<MindWave_Data>,
                response: Response<MindWave_Data>,
            ) {
                Log.d("testasd", response.toString())
                if (response.isSuccessful) {
                    Log.d("testasd", response.body().toString())
                    var data = response.body().toString() // GsonConverter를 사용해 데이터매핑
                    Log.d("testasd", data)
                }
            }

            override fun onFailure(call: Call<MindWave_Data>, t: Throwable) {
                Log.d("testasd", "실패$t")
            }

        })
    }
}