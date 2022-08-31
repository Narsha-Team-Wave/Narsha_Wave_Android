package kr.hs.dgsw.noepa_ls

import android.content.Context
import androidx.room.*

@Database(entities = [MindWaveEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): DAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase? {
            if(INSTANCE == null){
                kotlin.synchronized(AppDatabase::class) {
                    if (context != null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "MindWave.db")
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}