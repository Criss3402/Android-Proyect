package com.example.tpi_mobile_001.viewmodel

import com.example.tpi_mobile_001.models.Partido

sealed class PartidoUiState {
    object Loading : PartidoUiState()
    data class Success(val partidos: List<Partido>) : PartidoUiState()
    data class Error(val mensaje: String) : PartidoUiState()
}