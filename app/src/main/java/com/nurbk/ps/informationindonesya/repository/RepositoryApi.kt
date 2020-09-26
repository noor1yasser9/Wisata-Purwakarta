package com.nurbk.ps.informationindonesya.repository

import com.nurbk.ps.informationindonesya.network.RetrofitInstance

class RepositoryApi {

    suspend fun getHotel() = RetrofitInstance.api!!.getHotel()

    suspend fun getCooking() = RetrofitInstance.api!!.getCooking()

    suspend fun getWorship() = RetrofitInstance.api!!.getWorship()
    suspend fun getWisata() = RetrofitInstance.api!!.getWisata()

    suspend fun getWisata(id: String) = RetrofitInstance.api!!.getWisata(id)

    suspend fun getKomunitas() = RetrofitInstance.api!!.getKomunitas()

    suspend fun getKomunitas(id: String) = RetrofitInstance.api!!.getKomunitas(id)

    suspend fun getRuteangkot() = RetrofitInstance.api!!.getRuteangkot()


}