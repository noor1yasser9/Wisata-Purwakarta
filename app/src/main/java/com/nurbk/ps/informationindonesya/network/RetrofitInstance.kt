package com.nurbk.ps.informationindonesya.network


import com.nurbk.ps.informationindonesya.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {


    companion object {
        var api: Api? = null

        init {

            val client = OkHttpClient.Builder()
                .build()

            api = getInstantRetrofit(BASE_URL, client).create(Api::class.java)


        }

        private fun getInstantRetrofit(url: String, client: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()


    }

}