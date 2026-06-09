package com.example.tpi_mobile_001.repository

import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.network.RetrofitClient

class PartidoRepository {
    private val api = RetrofitClient.api

    suspend fun getPartidos(): List<Partido> {
        return api.getPartidos()
    }
}