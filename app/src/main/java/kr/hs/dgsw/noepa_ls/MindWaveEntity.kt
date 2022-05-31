package kr.hs.dgsw.noepa_ls

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import io.reactivex.Single

@Entity(tableName = "MindWave")
class MindWaveEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "DELTA") var DELTA: Int,
    @ColumnInfo(name = "THETA") var THETA: Int,
    @ColumnInfo(name = "LOW_ALPHA") var LOW_ALPHA: Int,
    @ColumnInfo(name = "HIGH_ALPHA") var HIGH_ALPHA: Int,
    @ColumnInfo(name = "LOW_BETA") var LOW_BETA: Int,
    @ColumnInfo(name = "HIGH_BETA") var HIGH_BETA: Int,
    @ColumnInfo(name = "LOW_GAMMA") var LOW_GAMMA: Int,
    @ColumnInfo(name = "MID_GAMMA") var MID_GAMMA: Int,
    @ColumnInfo(name = "ATTENTION") var ATTENTION: Int,
    @ColumnInfo(name = "MEDITATION") var MEDITATION: Int,
    @ColumnInfo(name = "TIMESTAMP") var TIMESTAMP: String
){
    constructor(): this(null, 0,0,0,0,0,0,0,0,0,0, "")
}

//  DELTA(1),
//  THETA(2),
//  LOW_ALPHA(3),
//  HIGH_ALPHA(4),
//  LOW_BETA(5),
//  HIGH_BETA(6),
//  LOW_GAMMA(7),
//  MID_GAMMA
//  ATTENTION
//  MEDITATION
