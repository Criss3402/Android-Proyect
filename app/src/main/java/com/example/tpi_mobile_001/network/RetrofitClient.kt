package com.example.tpi_mobile_001.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://6a28488a4e1e783349a56173.mockapi.io/"

    private val json = Json { ignoreUnknownKeys = true }

    val api: PartidoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(PartidoApiService::class.java)
    }
}