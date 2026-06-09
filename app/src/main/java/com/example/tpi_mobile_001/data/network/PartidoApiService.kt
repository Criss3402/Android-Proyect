package com.example.tpi_mobile_001.data.network

import com.example.tpi_mobile_001.data.models.Partidos
import retrofit2.http.GET

interface PartidoApiService {
    @GET("ListaPartidos")
    suspend fun getPartidos(): List<Partidos>
}