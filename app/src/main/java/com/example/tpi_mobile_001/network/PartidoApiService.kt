package com.example.tpi_mobile_001.network

import com.example.tpi_mobile_001.models.Partido
import retrofit2.http.GET

interface PartidoApiService {
    @GET("ListaPartidos")
    suspend fun getPartidos(): List<Partido>
}