package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel

@Composable
fun ListaPartidosScreen(viewModel: PartidoViewModel) {
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

        when {
            viewModel.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            viewModel.error != null -> {
                Text(
                    text = viewModel.error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                LazyColumn {
                    items(viewModel.partidos) { partido ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${partido.EquipoLocal} vs ${partido.EquipoVisitante}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "📍 ${partido.Estadio}")
                                Text(text = "🏆 ${partido.Fase}")
                            }
                        }
                    }
                }
            }
        }
    }
}