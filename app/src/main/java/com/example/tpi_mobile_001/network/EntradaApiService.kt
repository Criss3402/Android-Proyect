package com.example.tpi_mobile_001.network

import com.example.tpi_mobile_001.models.CompraEntradaRequest
import com.example.tpi_mobile_001.models.EntradaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EntradaApiService {

    @POST("api/entradas")
    suspend fun comprar(@Body datos: CompraEntradaRequest): EntradaDto

    // Trae las entradas del usuario autenticado (el token lo agrega AuthInterceptor automáticamente)
    @GET("api/entradas/mis-entradas")
    suspend fun misEntradas(): List<EntradaDto>
}