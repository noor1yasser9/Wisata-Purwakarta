package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.Hotel
import com.nurbk.ps.informationindonesya.model.kuliner
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class CookingViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

    val dataCookingLiveData = MediatorLiveData<Resource<kuliner>>()


    private suspend fun getCooking(
    ) {
        dataCookingLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getCooking(
                )
            dataCookingLiveData.postValue(getHotel(response))

            Timber.d("$TAG getCooking-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataCookingLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getCooking-> ${t.message.toString()}")
                }
                else -> {
                    dataCookingLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getCooking-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getHotel(response: Response<kuliner>):
            Resource<kuliner> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getCooking-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getCooking->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    private fun getCooks() =
        viewModelScope.launch {
            getCooking()
        }

    init {
        getCooks()
    }
}