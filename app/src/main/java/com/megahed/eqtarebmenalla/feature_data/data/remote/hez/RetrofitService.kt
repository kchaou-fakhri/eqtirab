package com.megahed.eqtarebmenalla.feature_data.data.remote.hez


import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.ResultHefz
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

        @GET("v1/edition/format/audio")
        fun getAllRewat() : Call<ResultHefz>


}