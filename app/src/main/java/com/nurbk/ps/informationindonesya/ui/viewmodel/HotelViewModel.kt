package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.Hotel
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class HotelViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

     val dataHotelLiveData = MediatorLiveData<Resource<Hotel>>()


    private suspend fun getHotel(
    ) {
        dataHotelLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getHotel(
                )
            dataHotelLiveData.postValue(getHotel(response))

            Timber.d("$TAG getHotel-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataHotelLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getHotel-> ${t.message.toString()}")
                }
                else -> {
                    dataHotelLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getHotel-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getHotel(response: Response<Hotel>):
            Resource<Hotel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getHotel-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getHotel->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    private fun getHotels() =
        viewModelScope.launch {
            getHotel()
        }

    init {
        getHotels()
    }
}