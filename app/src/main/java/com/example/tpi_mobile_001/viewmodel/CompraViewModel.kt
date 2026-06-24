package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpi_mobile_001.models.EntradaDto
import com.example.tpi_mobile_001.repository.EntradaRepository
import kotlinx.coroutines.launch
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

    fun comprar(partidoId: Int, sector: String, precio: Double) {
        viewModelScope.launch {
            uiState = CompraUiState.Cargando
            try {
                val entrada = repository.comprar(partidoId, sector, precio)
                uiState = CompraUiState.Exito(entrada)
            } catch (e: HttpException) {
                uiState = when (e.code()) {
                    400 -> CompraUiState.Error("El partido indicado no existe.")
                    401 -> CompraUiState.Error("Tu sesión expiró. Volvé a iniciar sesión.")
                    409 -> CompraUiState.Error("Esa entrada ya fue vendida.")
                    else -> CompraUiState.Error("Error al comprar la entrada (código ${e.code()}).")
                }
            } catch (e: Exception) {
                uiState = CompraUiState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}