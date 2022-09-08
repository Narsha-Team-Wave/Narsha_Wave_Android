package kr.hs.dgsw.noepa_ls

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm = "mPref"
    private val prefs = context.getSharedPreferences(prefNm, MODE_PRIVATE)

    var id: String?
        get() = prefs.getString("id", null)
        set(value) {
            prefs.edit().putString("id", value).apply()
        }
}

class App : Application() {
    companion object {
        lateinit var prefs: Prefs
    }

//    override fun onCreate() {
//        prefs = Prefs(applicationContext)
//        super.onCreate()
//    }
}