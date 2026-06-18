package com.example.tpi_mobile_001.ui.screens

import com.example.tpi_mobile_001.models.Partido
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tpi_mobile_001.viewmodel.PartidoUiState
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel
import com.example.tpi_mobile_001.ui.components.getBandera
@Composable
// Firma actualizada con el nuevo parámetro
fun ListaPartidosScreen(
    viewModel: PartidoViewModel,
    onPartidoClick: (Partido) -> Unit,
    onCerrarSesion: () -> Unit  //  nuevo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "⚽ Mundial FIFA 2026",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Botón nuevo para cerrar sesión y volver al login
        TextButton(onClick = onCerrarSesion){
            Text("Cerrar sesión")
        }
        // ... resto del código igual ajajaj XDDDDDDDDDDDDDDD ahre

        when (val state = viewModel.uiState) {
            is PartidoUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is PartidoUiState.Error -> {
                Text(
                    text = state.mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is PartidoUiState.Success -> {
                LazyColumn {
                    items(state.partidos) { partido ->
                        Card(
                            onClick = { onPartidoClick(partido) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${getBandera(partido.EquipoLocal)} ${partido.EquipoLocal} vs ${getBandera(partido.EquipoVisitante)} ${partido.EquipoVisitante}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "📍 ${partido.Estadio}")
                                Text(text = "🏆 ${partido.Fase}")
                                Text(text = "📅 ${partido.Fecha}  🕐 ${partido.Hora}")
                            }
                        }
                    }
                }
            }
        }
    }
}

