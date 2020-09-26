package com.nurbk.ps.informationindonesya.network

import com.nurbk.ps.informationindonesya.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {


    @GET("purwakarta/hotel")
    suspend fun getHotel(
    ): Response<Hotel>

    @GET("purwakarta/kuliner")
    suspend fun getCooking(
    ): Response<kuliner>

    @GET("purwakarta/tempatibadah")
    suspend fun getWorship(
    ): Response<TempatIbadah>


    @GET("purwakarta/wisata/")
    suspend fun getWisata(
    ): Response<Wisata>

    @GET("purwakarta/wisata/{id}")
    suspend fun getWisata(
        @Path("id") id: String
    ): Response<WisataContent>

    @GET("purwakarta/komunitas")
    suspend fun getKomunitas(
    ): Response<komunitas>

    @GET("purwakarta/komunitas/{id}")
    suspend fun getKomunitas(
        @Path("id") id: String
    ): Response<KomunitaContent>

    @GET("purwakarta/ruteangkot")
    suspend fun getRuteangkot(
    ): Response<RuteAngkot>

}