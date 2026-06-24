package com.example.tpi_mobile_001.network

import com.example.tpi_mobile_001.models.Partido
import retrofit2.http.GET

// Define los endpoints de la API relacionados a Partido.
// Igual que UsuarioApiService: esto es solo una "firma" de cómo se ve el pedido;
// Retrofit construye automáticamente el código real de red a partir de las anotaciones.
interface PartidoApiService {

    // Le dice a Retrofit: "esto es un GET hacia api/partidos" (la ruta real de tu API).
    // Antes decía "listaPartidos", que era la ruta del mock viejo — había que
    // cambiarla para que coincida con Mundial.Apps.WebApiApp.
    //
    // suspend: función asíncrona, solo se puede llamar desde una coroutine
    // (por ejemplo, dentro de viewModelScope.launch { ... } en el ViewModel).
    @GET("api/partidos")
    suspend fun getPartidos(): List<Partido>
    // El tipo de retorno List<Partido> le dice a Retrofit: "la respuesta es un
    // array JSON, convertilo a una lista de objetos Partido usando el modelo
    // que ya definimos en models/Partido.kt".
}