package kr.hs.dgsw.noepa_ls


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api: API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.80.163.26:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }
}