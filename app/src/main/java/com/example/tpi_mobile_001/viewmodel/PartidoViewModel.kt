package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpi_mobile_001.repository.PartidoRepository
import kotlinx.coroutines.launch

class PartidoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PartidoRepository(application)
    var uiState by mutableStateOf<PartidoUiState>(PartidoUiState.Loading)

    init {
        cargarPartidos()
    }

    fun     cargarPartidos() {
        viewModelScope.launch {
            uiState = PartidoUiState.Loading
            try {
                val partidos = repository.getPartidos()
                uiState = PartidoUiState.Success(partidos)
            } catch (e: Exception) {
                uiState = PartidoUiState.Error("Error al cargar los partidos")
            }
        }
    }
}