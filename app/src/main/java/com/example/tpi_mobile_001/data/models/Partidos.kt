package com.example.tpi_mobile_001.data.models


data class Partidos(
    val id: String,
    val EquipoLocal: String,
    val EquipoVisitante: String,
    val Fecha: Long,
    val Estadio: String,
    val Fase: String,
    val GolesLocal: Int,
    val GolesVisitante: Int
)
