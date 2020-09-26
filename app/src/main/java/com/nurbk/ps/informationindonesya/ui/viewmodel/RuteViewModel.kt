package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.Hotel
import com.nurbk.ps.informationindonesya.model.RuteAngkot
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class RuteViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

     val dataRuteLiveData = MediatorLiveData<Resource<RuteAngkot>>()


    private suspend fun getRuteangkot(
    ) {
        dataRuteLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getRuteangkot(
                )
            dataRuteLiveData.postValue(getRuteangkot(response))

            Timber.d("$TAG getRuteangkot-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataRuteLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getRuteangkot-> ${t.message.toString()}")
                }
                else -> {
                    dataRuteLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getRuteangkot-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getRuteangkot(response: Response<RuteAngkot>):
            Resource<RuteAngkot> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getRuteangkot-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getRuteangkot->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    private fun getRuteangkots() =
        viewModelScope.launch {
            getRuteangkot()
        }

    init {
        getRuteangkots()
    }
}