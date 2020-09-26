package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.TempatIbadah
import com.nurbk.ps.informationindonesya.model.Wisata
import com.nurbk.ps.informationindonesya.model.WisataContent
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class WistataViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

    val dataWisataLiveData = MediatorLiveData<Resource<Wisata>>()

    val dataWisataContentLiveData = MediatorLiveData<Resource<WisataContent>>()


    private suspend fun getWisata(
        id: String
    ) {
        dataWisataContentLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getWisata(
                    id
                )
            dataWisataContentLiveData.postValue(getWisata(response))

            Timber.d("$TAG getWisata-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataWisataContentLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWisata-> ${t.message.toString()}")
                }
                else -> {
                    dataWisataContentLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWisata-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getWisata(response: Response<WisataContent>):
            Resource<WisataContent> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getWisata-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getWisata->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    fun getWisatas(id: String) =
        viewModelScope.launch {
            getWisata(id)
        }


    private suspend fun getWisata(

    ) {
        dataWisataLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getWisata(

                )
            dataWisataLiveData.postValue(getWisatas(response))

            Timber.d("$TAG getWisata-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataWisataLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWisata-> ${t.message.toString()}")
                }
                else -> {
                    dataWisataLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getWisata-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getWisatas(response: Response<Wisata>):
            Resource<Wisata> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getWisata-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getWisata->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    fun getWisatas() =
        viewModelScope.launch {
            getWisata()
        }

    init {
        getWisatas()
    }

}