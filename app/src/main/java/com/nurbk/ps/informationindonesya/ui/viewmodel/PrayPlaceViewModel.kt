package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.Hotel
import com.nurbk.ps.informationindonesya.model.TempatIbadah
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class PrayPlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

    val dataTempatbadahlLiveData = MediatorLiveData<Resource<TempatIbadah>>()


    private suspend fun getWorship(
    ) {
        dataTempatbadahlLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getWorship(
                )
            dataTempatbadahlLiveData.postValue(getWorship(response))

            Timber.d("$TAG getWorship-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataTempatbadahlLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWorship-> ${t.message.toString()}")
                }
                else -> {
                    dataTempatbadahlLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWorship-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getWorship(response: Response<TempatIbadah>):
            Resource<TempatIbadah> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getWorship-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getWorship->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    private fun getWorships() =
        viewModelScope.launch {
            getWorship()
        }

    init {
        getWorships()
    }
}