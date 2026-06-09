package com.example.tpi_mobile_001.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://6a28488a4e1e783349a56173.mockapi.io/"

    val api: PartidoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PartidoApiService::class.java)
    }
}