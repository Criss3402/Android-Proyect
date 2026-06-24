package com.example.tpi_mobile_001.models

import kotlinx.serialization.Serializable

// Representa un Partido tal como lo devuelve la API. Coincide con PartidoDto de C#.
// @Serializable: le dice a kotlinx.serialization que esta clase se puede
// convertir automáticamente desde/hacia JSON.
@Serializable
data class Partido(
    val partidoId: Int,

    // Quedan como String por simplicidad. La API los manda como texto
    // (ej: "2026-06-11" y "16:00:00"). Si más adelante necesitan hacer cálculos
    // de fechas reales (ordenar, comparar), se podrían convertir a un tipo de
    // fecha real de Kotlin, pero no es necesario para solo mostrarlos en pantalla.
    val fecha: String,
    val hora: String,

    val ciudad: String,      // hoy viene vacío desde la API, pero el campo existe
    val estadio: String,
    val fase: String,        // ej: "Grupo A", "Octavos", etc.

    // IDs de los equipos (útiles si en algún momento quieren filtrar por equipo,
    // o navegar a una pantalla de detalle del equipo).
    val equipoIdLocal: Int,
    val equipoLocalNombre: String,   // el nombre ya viene resuelto, no hace falta
    // pedir el equipo aparte a la API

    val equipoIdVisitante: Int,
    val equipoVisitanteNombre: String,

    // Int? (con el ?) porque son NULLABLE: un partido que todavía no se jugó
    // no tiene goles cargados, la API manda "null" en ese caso.
    // Sin el "?", Kotlin esperaría siempre un número y la app crashearía
    // al recibir un partido sin jugar.
    val golesLocal: Int?,
    val golesVisitante: Int?
)