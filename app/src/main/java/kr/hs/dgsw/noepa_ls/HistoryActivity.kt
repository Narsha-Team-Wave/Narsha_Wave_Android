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

        var attention = intent.getStringArrayListExtra("attention")
        var delta = intent.getStringArrayListExtra("delta")
        var high_alpha = intent.getStringArrayListExtra("high_alpha")
        var high_beta = intent.getStringArrayListExtra("high_beta")
        var low_alpha = intent.getStringArrayListExtra("low_alpha")
        var low_beta = intent.getStringArrayListExtra("low_beta")
        var low_gamma = intent.getStringArrayListExtra("low_gamma")
        var meditation = intent.getStringArrayListExtra("meditation")
        var mid_gamma = intent.getStringArrayListExtra("mid_gamma")
        var theta = intent.getStringArrayListExtra("theta")

        for(i in 0..attention!!.size){
            binding.attention.text = binding.attention.text.toString() + "\n" + attention[0]
            binding.delta.text = binding.delta.text.toString() + "\n" + delta!![0]
            binding.highAlpha.text = binding.highAlpha.text.toString() + "\n" + high_alpha!![0]
            binding.highBeta.text = binding.highBeta.text.toString() + "\n" + high_beta!![0]
            binding.lowAlpha.text = binding.lowAlpha.text.toString() + "\n" + low_alpha!![0]
            binding.lowBeta.text = binding.lowBeta.text.toString() + "\n" + low_beta!![0]
            binding.lowGamma.text = binding.lowGamma.text.toString() + "\n" + low_gamma!![0]
            binding.meditation.text = binding.meditation.text.toString() + "\n" + meditation!![0]
            binding.midGamma.text = binding.midGamma.text.toString() + "\n" + mid_gamma!![0]
            binding.theta.text = binding.theta.text.toString() + "\n" + theta!![0]
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