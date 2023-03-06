package com.megahed.eqtarebmenalla.feature_data.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.megahed.eqtarebmenalla.db.model.Hefz
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.RetrofitHelper
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.ResultHefz

import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Reway
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HefzRepository {

        fun getAllRewat() : LiveData<ResultHefz>{
           val mutableLiveData = MutableLiveData<ResultHefz>()

                RetrofitHelper.api.getAllRewat().clone().enqueue(object :
                    Callback<ResultHefz>{
                    override fun onResponse(
                        call: Call<ResultHefz>,
                        response: Response<ResultHefz>
                    ) {
                        Log.println(Log.ASSERT, "Rewat list : ", response.body().toString())
                        mutableLiveData.value = response.body()
                    }

                    override fun onFailure(call: Call<ResultHefz>, t: Throwable) {
                        Log.println(Log.ASSERT, "Failed : ", t.message.toString())
                    }

                })

           return mutableLiveData
       }
}