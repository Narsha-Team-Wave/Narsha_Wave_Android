package kr.hs.dgsw.noepa_ls

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/inputData")
    fun MindWavePost(@Body mindwaveData: MindWave_Data) : Call<MindWave_Data>
}