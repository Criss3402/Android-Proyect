package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.viewmodel.CompraUiState
import com.example.tpi_mobile_001.viewmodel.CompraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraEntradaScreen(
    partido: Partido,
    onVolver: () -> Unit
) {
    val compraViewModel: CompraViewModel = viewModel()
    var sector by remember { mutableStateOf("") }
    var precioTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comprar entrada") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${partido.equipoLocalNombre} vs ${partido.equipoVisitanteNombre}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${partido.estadio} · ${partido.fecha}",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            OutlinedTextField(
                value = sector,
                onValueChange = { sector = it },
                label = { Text("Sector") },
                placeholder = { Text("Ej: Platea, Popular, VIP") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precioTexto,
                onValueChange = { precioTexto = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            val estado = compraViewModel.uiState

            when (estado) {
                is CompraUiState.Cargando -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is CompraUiState.Exito -> {
                    Text(
                        text = "Entrada comprada con éxito (ID: ${estado.entrada.entradaId})",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is CompraUiState.Error -> {
                    Text(
                        text = estado.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }

            Button(
                onClick = {
                    val precio = precioTexto.toDoubleOrNull()
                    if (sector.isBlank()) {
                        compraViewModel.uiState = CompraUiState.Error("Ingresá un sector.")
                    } else if (precio == null) {
                        compraViewModel.uiState = CompraUiState.Error("Ingresá un precio válido.")
                    } else {
                        compraViewModel.comprar(partido.partidoId, sector, precio)
                    }
                },
                enabled = estado !is CompraUiState.Cargando && estado !is CompraUiState.Exito,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar compra")
            }
        }
    }
}