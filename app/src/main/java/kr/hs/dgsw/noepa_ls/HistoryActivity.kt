package kr.hs.dgsw.noepa_ls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kr.hs.dgsw.noepa_ls.databinding.ActivityHistoryBinding
import kr.hs.dgsw.noepa_ls.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {
    private var mBinding: ActivityHistoryBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var appDatabase : AppDatabase? = AppDatabase.getInstance(this)
        val saveddata : List<MindWaveEntity> = appDatabase!!.dao().getAll()
        var DataList = arrayListOf<MindWaveEntity>()
        if(saveddata.isNotEmpty()){
            DataList.addAll(saveddata)
        }
        var MindWave = IntArray(10)
        for (i in 0 until DataList.size){
            MindWave[0] += DataList[i].ATTENTION
            MindWave[1] += DataList[i].MEDITATION
            MindWave[2] += DataList[i].DELTA
            MindWave[3] += DataList[i].HIGH_ALPHA
            MindWave[4] += DataList[i].HIGH_BETA
            MindWave[5] += DataList[i].LOW_ALPHA
            MindWave[6] += DataList[i].LOW_BETA
            MindWave[7] += DataList[i].LOW_GAMMA
            MindWave[8] += DataList[i].MID_GAMMA
            MindWave[9] += DataList[i].THETA
            binding.timeStamp.text = binding.timeStamp.text.toString() + "\n"+DataList[i].TIMESTAMP
        }
        for(i in 0 until 10){
            MindWave[i] /= DataList.size
        }

        binding.attention.text = MindWave[0].toString()
        binding.meditation.text = MindWave[1].toString()
        binding.delta.text = MindWave[2].toString()
        binding.highAlpha.text = MindWave[3].toString()
        binding.highBeta.text = MindWave[4].toString()
        binding.lowAlpha.text = MindWave[5].toString()
        binding.lowBeta.text = MindWave[6].toString()
        binding.lowGamma.text = MindWave[7].toString()
        binding.midGamma.text = MindWave[8].toString()
        binding.theta.text = MindWave[9].toString()
    }
}