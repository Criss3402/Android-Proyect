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
fun ListaPartidosScreen(
    viewModel: PartidoViewModel,
    onPartidoClick: (Partido) -> Unit,
    onCerrarSesion: () -> Unit
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

        TextButton(onClick = onCerrarSesion) {
            Text("Cerrar sesión")
        }

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
                            Column(modifier = Modifier.padding(all = 16.dp)) {
                                Text(
                                    text = "${getBandera(equipo = partido.equipoLocalNombre)} ${partido.equipoLocalNombre} vs ${getBandera(equipo = partido.equipoVisitanteNombre)} ${partido.equipoVisitanteNombre}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "📍 ${partido.estadio}")
                                Text(text = "🏆 ${partido.fase}")
                                Text(text = "📅 ${partido.fecha}  🕐 ${partido.hora}")
                            }
                        }
                    }
                }
            }
        }
    }
}