package com.example.tpi_mobile_001.models

data class Partido(
    val id: String,
    val EquipoLocal: String,
    val EquipoVisitante: String,
    val Fecha: Long,
    val Estadio: String,
    val Fase: String,
    val GolesLocal: Int,
    val GolesVisitante: Int
)