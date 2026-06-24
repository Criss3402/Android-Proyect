package com.example.tpi_mobile_001.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

// object = un singleton en Kotlin: solo existe UNA instancia de RetrofitClient
// en toda la app, compartida por todos lados donde se la use.
object RetrofitClient {

    // La URL base de la API. 10.0.2.2 es la IP especial que representa,
    // desde DENTRO del emulador de Android, "la PC que está corriendo el emulador".
    // Si en algún momento prueban en un celular físico, esto cambia a la IP de red real.
    private const val BASE_URL = "http://10.0.2.2:5089/"

    // Configuración de cómo se convierte JSON <-> objetos Kotlin.
    // ignoreUnknownKeys = true: si la API devuelve un campo que el modelo Kotlin
    // no tiene definido, no falla — simplemente lo ignora. Da más margen de error
    // sin romper la app si la API cambia ligeramente.
    private val json = Json { ignoreUnknownKeys = true }

    // "by lazy": esta variable se crea recién la PRIMERA VEZ que alguien la usa,
    // no apenas arranca la app. Es eficiente porque si nunca se llega a usar,
    // nunca se gasta el trabajo de construirla.
    private val okHttpClient by lazy {
        okhttp3.OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    // Servicio para los endpoints de Partido. Retrofit "implementa" automáticamente
    // la interfaz PartidoApiService a partir de las anotaciones (@GET, @POST, etc.)
    val partidoApi: PartidoApiService by lazy {
        retrofit.create(PartidoApiService::class.java)
    }

    // Servicio para los endpoints de Usuario (registro/login).
    val usuarioApi: UsuarioApiService by lazy {
        retrofit.create(UsuarioApiService::class.java)
    }

    val entradaApi: EntradaApiService by lazy {
        retrofit.create(EntradaApiService::class.java)
    }

}