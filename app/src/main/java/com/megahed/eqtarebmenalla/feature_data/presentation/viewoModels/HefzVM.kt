package com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels

import androidx.lifecycle.LiveData
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.ResultHefz
import com.megahed.eqtarebmenalla.feature_data.data.remote.hez.entity.Reway
import com.megahed.eqtarebmenalla.feature_data.data.repository.HefzRepository

class HefzVM {

    val hefzRepository : HefzRepository

    constructor(){
        hefzRepository = HefzRepository()
    }

    fun getAllRewat(): LiveData<ResultHefz>{

      return  hefzRepository.getAllRewat()

    }
}