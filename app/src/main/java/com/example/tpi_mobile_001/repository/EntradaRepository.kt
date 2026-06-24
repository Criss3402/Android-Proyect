package com.example.tpi_mobile_001.repository

import com.example.tpi_mobile_001.models.CompraEntradaRequest
import com.example.tpi_mobile_001.models.EntradaDto
import com.example.tpi_mobile_001.network.RetrofitClient

class EntradaRepository {
    private val api = RetrofitClient.entradaApi

    suspend fun comprar(partidoId: Int, sector: String, precio: Double): EntradaDto {
        return api.comprar(
            CompraEntradaRequest(
                partidoId = partidoId,
                sector = sector,
                precio = precio
            )
        )
    }
}