package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.repository.PartidoRepository
import kotlinx.coroutines.launch

class PartidoViewModel : ViewModel() {
    private val repository = PartidoRepository()

    var partidos by mutableStateOf<List<Partido>>(emptyList())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    init {
        cargarPartidos()
    }

    fun cargarPartidos() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                partidos = repository.getPartidos()
            } catch (e: Exception) {
                error = "Error al cargar los partidos"
            } finally {
                isLoading = false
            }
        }
    }
}