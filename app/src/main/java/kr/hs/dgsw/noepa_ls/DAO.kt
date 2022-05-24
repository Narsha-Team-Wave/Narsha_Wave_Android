package kr.hs.dgsw.noepa_ls

import androidx.room.*

@Dao
interface DAO {
    @Query("SELECT * FROM MindWave")
    fun getAll(): List<MindWaveEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mindWaveEntity: MindWaveEntity)

    @Delete
    fun delete(mindWaveEntity: MindWaveEntity)
}