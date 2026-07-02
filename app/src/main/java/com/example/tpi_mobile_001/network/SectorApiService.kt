package com.example.tpi_mobile_001.network

import com.example.tpi_mobile_001.models.SectorDto
import retrofit2.http.GET

interface SectorApiService {

    @GET("api/sectores")
    suspend fun getSectores(): List<SectorDto>
}