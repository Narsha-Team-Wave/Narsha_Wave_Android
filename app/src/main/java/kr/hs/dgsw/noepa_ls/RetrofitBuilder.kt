package kr.hs.dgsw.noepa_ls


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api: API
    private var serverIP:String = "10.80.162.125"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://"+ serverIP + ":8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }
}