package com.example.tpi_mobile_001.models

import kotlinx.serialization.Serializable

// Lo que se manda para REGISTRARSE. Coincide con RegistroUsuarioDto de la API en C#.
// El password viaja en texto plano por HTTPS hacia la API — la API es quien
// lo hashea antes de guardarlo, Kotlin nunca toca esa lógica.
@Serializable
data class RegistroRequest(
    val username: String,
    val email: String,
    val password: String
)

// Lo que se manda para LOGUEARSE. Coincide con LoginDto de la API.
// Solo necesita username y password (no email) porque así definimos el login del lado del server.
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

// Lo que la API DEVUELVE después de un login exitoso. Coincide con LoginResponseDto.
// El "token" es el JWT que hay que guardar y reusar en los pedidos protegidos
// (mandándolo en el header Authorization: Bearer {token}).
@Serializable
data class LoginResponse(
    val token: String,
    val usuarioId: Int,
    val username: String
)

// Lo que la API devuelve al consultar o registrar un usuario. Coincide con UsuarioDto.
// Notar que NO tiene password ni nada sensible — la API nunca expone esos datos,
// así que del lado de Kotlin tampoco hay forma de recibirlos ni de necesitarlos.
@Serializable
data class Usuario(
    val usuarioId: Int,
    val username: String,
    val email: String
)

@Serializable
data class CompraEntradaRequest(
    val partidoId: Int,
    val sectorId: Int   // antes tenía "sector: String" y "precio: Double"

)

@Serializable
data class EntradaDto(
    val entradaId: Int,
    val usuarioId: Int,
    val usuarioUsername: String,
    val partidoId: Int,
    val sectorId: Int,
    val sectorNombre: String,
    val precio: Double,
    val fechaCompra: String,
    // Datos del partido
    val equipoLocalNombre: String,
    val equipoVisitanteNombre: String,
    val estadio: String,
    val fechaPartido: String,
    val horaPartido: String,
    val fase: String
)

@Serializable
data class SectorDto(
    val sectorId: Int,
    val nombre: String,
    val precio: Double
)