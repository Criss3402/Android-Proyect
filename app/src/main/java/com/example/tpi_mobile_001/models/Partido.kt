package com.example.tpi_mobile_001.models

import kotlinx.serialization.Serializable

@Serializable
data class Partido(
    val id: String,
    val EquipoLocal: String,
    val EquipoVisitante: String,
    val Fecha: String,
    val Hora: String,
    val Estadio: String,
    val Fase: String,
    val GolesLocal: Int,
    val GolesVisitante: Int
)