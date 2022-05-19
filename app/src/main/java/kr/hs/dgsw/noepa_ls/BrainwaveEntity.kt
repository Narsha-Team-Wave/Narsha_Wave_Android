package kr.hs.dgsw.noepa_ls

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pwittchen.neurosky.library.message.enums.BrainWave
import com.github.pwittchen.neurosky.library.message.enums.Signal
import io.reactivex.Single

@Entity(tableName = "MindWave")
class BrainwaveEntity(
//    @PrimaryKey(autoGenerate = true) var id: Long?,
//    @ColumnInfo(name = "signal") var singnal: Signal,
//    @ColumnInfo(name = "brainwave") var brainwave: Set<BrainWave>
)
