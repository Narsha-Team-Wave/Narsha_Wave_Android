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
            MindWave[0] = DataList[i].ATTENTION
            MindWave[1] = DataList[i].MEDITATION
            MindWave[2] = DataList[i].DELTA
            MindWave[3] = DataList[i].HIGH_ALPHA
            MindWave[4] = DataList[i].HIGH_BETA
            MindWave[5] = DataList[i].LOW_ALPHA
            MindWave[6] = DataList[i].LOW_BETA
            MindWave[7] = DataList[i].LOW_GAMMA
            MindWave[8] = DataList[i].MID_GAMMA
            MindWave[9] = DataList[i].THETA
        }
        for(i in 0 until 10){
            MindWave[i] /= DataList.size
        }


        binding.attention.text = DataList[0].ATTENTION.toString()
        binding.meditation.text = DataList[1].ATTENTION.toString()
        binding.delta.text = DataList[2].ATTENTION.toString()
        binding.highAlpha.text = DataList[3].ATTENTION.toString()
        binding.highBeta.text = DataList[4].ATTENTION.toString()
        binding.lowAlpha.text = DataList[5].ATTENTION.toString()
        binding.lowBeta.text = DataList[6].ATTENTION.toString()
        binding.lowGamma.text = DataList[7].ATTENTION.toString()
        binding.midGamma.text = DataList[8].ATTENTION.toString()
        binding.theta.text = DataList[9].ATTENTION.toString()
    }
}