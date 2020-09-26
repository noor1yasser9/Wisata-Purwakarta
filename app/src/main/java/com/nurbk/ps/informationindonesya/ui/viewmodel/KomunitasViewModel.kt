package com.nurbk.ps.informationindonesya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.informationindonesya.model.KomunitaContent
import com.nurbk.ps.informationindonesya.model.komunitas
import com.nurbk.ps.informationindonesya.repository.RepositoryApi
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class KomunitasViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HotelViewModel"

    private val repository = RepositoryApi()

    val dataKomunitasLiveData = MediatorLiveData<Resource<komunitas>>()

    val dataKomunitasContentLiveData = MediatorLiveData<Resource<KomunitaContent>>()


    private suspend fun getKomunitas(
    ) {
        dataKomunitasLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getKomunitas(

                )
            dataKomunitasLiveData.postValue(getKomunitas(response))

            Timber.d("$TAG getKomunitas-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataKomunitasLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getKomunitas-> ${t.message.toString()}")
                }
                else -> {
                    dataKomunitasLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getKomunitas-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getKomunitas(response: Response<komunitas>):
            Resource<komunitas> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getKomunitas-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getKomunitas->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    fun getKomunitass() =
        viewModelScope.launch {
            getKomunitas()
        }



    private suspend fun getKomunitas(
        id:String
    ) {
        dataKomunitasContentLiveData.postValue(Resource.Loading())
        try {
            val response = repository
                .getKomunitas(
                    id
                )
            dataKomunitasContentLiveData.postValue(getKomunitasContent(response))

            Timber.d("$TAG getKomunitasContent-> OK")
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    dataKomunitasContentLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getKomunitasContent-> ${t.message.toString()}")
                }
                else -> {
                    dataKomunitasContentLiveData.postValue(Resource.Error(t.message.toString()))
                    Timber.e("$TAG getKomunitasContent-> ${t.message.toString()}")
                }

            }
        }
    }

    private fun getKomunitasContent(response: Response<KomunitaContent>):
            Resource<KomunitaContent> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Timber.d("$TAG getKomunitasContent-> Resource.Success->$resultResponse")
                return Resource.Success(resultResponse)
            }
        }
        Timber.e("$TAG getKomunitasContent->Resource.Error->${response.message()}")
        return Resource.Error(response.message())
    }

    fun getKomunitasContent(id:String) =
        viewModelScope.launch {
            getKomunitas(id)
        }

    init {

        getKomunitass()
    }

}