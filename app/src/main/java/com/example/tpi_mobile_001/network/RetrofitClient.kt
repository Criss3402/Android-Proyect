package com.example.tpi_mobile_001.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://6a25d3e65447714a6f83bb6d.mockapi.io/"

    val api: PartidoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PartidoApiService::class.java)
    }
}