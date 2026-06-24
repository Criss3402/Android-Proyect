package com.example.tpi_mobile_001.network

import com.example.tpi_mobile_001.models.LoginRequest
import com.example.tpi_mobile_001.models.LoginResponse
import com.example.tpi_mobile_001.models.RegistroRequest
import com.example.tpi_mobile_001.models.Usuario
import retrofit2.http.Body
import retrofit2.http.POST

// Define los endpoints de la API relacionados a Usuario (registro y login).
// Retrofit genera automáticamente el código real de red a partir de esta interfaz:
// nosotros solo declaramos "la forma" del pedido, Retrofit hace el trabajo de armar
// la conexión HTTP, mandar el JSON, y convertir la respuesta de vuelta a objetos Kotlin.
interface UsuarioApiService {

    // Le dice a Retrofit: "esto es un POST hacia api/usuarios/registro".
    // suspend = esta función es asíncrona (no bloquea el hilo principal mientras espera
    // la respuesta de la red); solo se puede llamar desde una coroutine.
    @POST("api/usuarios/registro")
    suspend fun registrar(
        @Body datos: RegistroRequest  // @Body: convertí "datos" a JSON y mandalo en el cuerpo del pedido
    ): Usuario  // el tipo de retorno le dice a Retrofit cómo convertir el JSON de respuesta

    // Mismo patrón para el login.
    @POST("api/usuarios/login")
    suspend fun login(
        @Body datos: LoginRequest
    ): LoginResponse
}