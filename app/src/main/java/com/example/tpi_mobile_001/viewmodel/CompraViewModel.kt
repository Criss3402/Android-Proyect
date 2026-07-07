package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpi_mobile_001.models.EntradaDto
import com.example.tpi_mobile_001.repository.EntradaRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

sealed class CompraUiState {
    object Idle : CompraUiState()
    object Cargando : CompraUiState()
    data class Exito(val entrada: EntradaDto) : CompraUiState()
    data class Error(val mensaje: String) : CompraUiState()
}

class CompraViewModel : ViewModel() {
    private val repository = EntradaRepository()

    var uiState by mutableStateOf<CompraUiState>(CompraUiState.Idle)

    fun comprar(partidoId: Int, sectorId: Int) {
        viewModelScope.launch {
            uiState = CompraUiState.Cargando
            try {
                val entrada = repository.comprar(partidoId, sectorId)
                uiState = CompraUiState.Exito(entrada)
            } catch (e: HttpException) {
                // Intentamos leer el mensaje que devuelve la API en el body del error.
                // La API devuelve { "mensaje": "..." } en todos los errores.
                val mensajeApi = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    if (!errorBody.isNullOrEmpty()) {
                        JSONObject(errorBody).getString("mensaje")
                    } else null
                } catch (ex: Exception) {
                    null
                }

                uiState = when (e.code()) {
                    400 -> CompraUiState.Error(
                        mensajeApi ?: "No se pudo completar la compra."
                    )
                    401 -> CompraUiState.Error(
                        "Tu sesión expiró. Volvé a iniciar sesión."
                    )
                    404 -> CompraUiState.Error(
                        mensajeApi ?: "El partido indicado no existe."
                    )
                    409 -> CompraUiState.Error(
                        mensajeApi ?: "Ocurrió un error al procesar la compra. Intentá de nuevo."
                    )
                    else -> CompraUiState.Error(
                        mensajeApi ?: "Error al comprar la entrada (código ${e.code()})."
                    )
                }
            } catch (e: Exception) {
                uiState = CompraUiState.Error("Error de conexión. Verificá tu internet e intentá de nuevo.")
            }
        }
    }
}