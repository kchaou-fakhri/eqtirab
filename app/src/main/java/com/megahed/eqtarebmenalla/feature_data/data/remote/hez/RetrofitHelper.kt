package com.megahed.eqtarebmenalla.feature_data.data.remote.hez

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "https://api.alquran.cloud/"

    private val retrofit by lazy {



           Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // we need to add converter factory to
                // convert JSON object to Java object
                .build()


    }
    val api : RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}