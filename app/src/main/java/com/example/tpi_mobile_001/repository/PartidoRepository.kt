package com.example.tpi_mobile_001.repository

import android.content.Context
import com.example.tpi_mobile_001.data.local.PartidoLocalDataSource
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.network.RetrofitClient

class PartidoRepository(context: Context) {
    private val api = RetrofitClient.api
    private val local = PartidoLocalDataSource(context)

    suspend fun getPartidos(): List<Partido> {
        return try {
            val partidos = api.getPartidos()
            local.guardarPartidos(partidos)
            partidos
        } catch (e: Exception) {
            val cache = local.obtenerPartidos()
            if (cache.isNotEmpty()) cache else throw e
        }
    }
}