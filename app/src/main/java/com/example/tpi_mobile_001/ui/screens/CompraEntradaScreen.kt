package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.models.SectorDto
import com.example.tpi_mobile_001.network.RetrofitClient
import com.example.tpi_mobile_001.viewmodel.CompraUiState
import com.example.tpi_mobile_001.viewmodel.CompraViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraEntradaScreen(
    partido: Partido,
    onVolver: () -> Unit
) {
    val compraViewModel: CompraViewModel = viewModel()
    val scope = rememberCoroutineScope()

    var sectores by remember { mutableStateOf<List<SectorDto>>(emptyList()) }
    var cargandoSectores by remember { mutableStateOf(true) }
    var sectorSeleccionado by remember { mutableStateOf<SectorDto?>(null) }
    var expandido by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            sectores = RetrofitClient.sectorApi.getSectores()
        } finally {
            cargandoSectores = false
        }
    }

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

            if (cargandoSectores) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = { expandido = !expandido }
                ) {
                    OutlinedTextField(
                        value = sectorSeleccionado?.let { "${it.nombre} — $${it.precio}" } ?: "Elegí un sector",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sector") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandido,
                        onDismissRequest = { expandido = false }
                    ) {
                        sectores.forEach { sector ->
                            DropdownMenuItem(
                                text = { Text("${sector.nombre} — $${sector.precio}") },
                                onClick = {
                                    sectorSeleccionado = sector
                                    expandido = false
                                }
                            )
                        }
                    }
                }

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
                        val sector = sectorSeleccionado
                        if (sector == null) {
                            compraViewModel.uiState = CompraUiState.Error("Elegí un sector antes de continuar.")
                        } else {
                            compraViewModel.comprar(partido.partidoId, sector.sectorId)
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
}