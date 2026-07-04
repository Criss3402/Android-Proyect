package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.models.SectorDto
import com.example.tpi_mobile_001.network.RetrofitClient
import com.example.tpi_mobile_001.ui.components.getBandera
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.CompraUiState
import com.example.tpi_mobile_001.viewmodel.CompraViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private fun formatearFechaCompra(fecha: String): String {
    return try {
        val date = LocalDate.parse(fecha)
        val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("es"))
        date.format(formatter)
    } catch (e: Exception) {
        fecha
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraEntradaScreen(
    partido: Partido,
    onVolver: () -> Unit,
    onCompraExitosa: (entradaId: Int, sectorNombre: String, precio: Int) -> Unit
) {
    val compraViewModel: CompraViewModel = viewModel()
    val estado = compraViewModel.uiState

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

    // Cuando la compra sea exitosa, navegamos a la pantalla de éxito
    LaunchedEffect(estado) {
        val actual = estado
        if (actual is CompraUiState.Exito) {
            val sector = sectorSeleccionado
            onCompraExitosa(
                actual.entrada.entradaId,
                sector?.nombre ?: "",
                sector?.precio?.toInt() ?: 0
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (!cargandoSectores) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FondoApp)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(13.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TribunaAzul)
                    ) {
                        Text(
                            text = "Confirmar compra",
                            fontSize = 15.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(FondoApp)
        ) {
            // ── Header azul ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(TribunaAzulClaro, TribunaAzulOscuro),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(400f, 400f)
                        )
                    )
                    .drawBehind {
                        val stripeWidth = 30.dp.toPx()
                        var x = 0f
                        while (x < size.width) {
                            drawRect(
                                color = Color.White.copy(alpha = 0.06f),
                                topLeft = androidx.compose.ui.geometry.Offset(x, 0f),
                                size = androidx.compose.ui.geometry.Size(stripeWidth / 2, size.height)
                            )
                            x += stripeWidth
                        }
                    }
                    .padding(top = 8.dp, bottom = 48.dp)
            ) {
                Column {
                    // TopBar con flecha y título
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onVolver) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Comprar entrada",
                            fontSize = 16.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    // Equipos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getBandera(partido.equipoLocalNombre), fontSize = 36.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = partido.equipoLocalNombre.uppercase(),
                                fontSize = 13.sp,
                                fontFamily = Anton,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                letterSpacing = 0.5.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = "VS",
                            fontSize = 14.sp,
                            fontFamily = Anton,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getBandera(partido.equipoVisitanteNombre), fontSize = 36.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = partido.equipoVisitanteNombre.uppercase(),
                                fontSize = 13.sp,
                                fontFamily = Anton,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                letterSpacing = 0.5.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Estadio + fecha formateada
                    Text(
                        text = "📍 ${partido.estadio}  ·  📅 ${formatearFechaCompra(partido.fecha)}",
                        fontSize = 12.sp,
                        fontFamily = Archivo,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 24.dp)
                    )
                }
            }

            // ── Formulario de compra ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-32).dp)
                    .background(FondoApp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Elegí tu sector",
                    fontSize = 20.sp,
                    fontFamily = Anton,
                    fontWeight = FontWeight.Normal,
                    color = TextoPrimario
                )

                if (cargandoSectores) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = TribunaAzul)
                    }
                } else {
                    // Dropdown de sectores
                    ExposedDropdownMenuBox(
                        expanded = expandido,
                        onExpandedChange = { expandido = !expandido }
                    ) {
                        OutlinedTextField(
                            value = sectorSeleccionado?.let { "${it.nombre} — $${it.precio}" }
                                ?: "Elegí un sector",
                            onValueChange = {},
                            readOnly = true,
                            textStyle = androidx.compose.ui.text.TextStyle(fontFamily = Archivo, fontSize = 15.sp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Borde,
                                focusedBorderColor = TribunaAzul,
                                unfocusedContainerColor = FondoCard,
                                focusedContainerColor = FondoCard
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandido,
                            onDismissRequest = { expandido = false }
                        ) {
                            sectores.forEach { sector ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = sector.nombre,
                                                fontFamily = Archivo,
                                                fontWeight = FontWeight.Medium,
                                                color = TextoPrimario
                                            )
                                            Text(
                                                text = "$${sector.precio}",
                                                fontFamily = Archivo,
                                                fontWeight = FontWeight.Bold,
                                                color = TribunaAzul
                                            )
                                        }
                                    },
                                    onClick = {
                                        sectorSeleccionado = sector
                                        expandido = false
                                    }
                                )
                            }
                        }
                    }

                    // Resumen si hay sector seleccionado
                    sectorSeleccionado?.let { sector ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = FondoSeleccion
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "RESUMEN",
                                        fontSize = 11.sp,
                                        fontFamily = Archivo,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoSecundario,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = sector.nombre,
                                        fontSize = 15.sp,
                                        fontFamily = Archivo,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoPrimario
                                    )
                                }
                                Text(
                                    text = "$${sector.precio}",
                                    fontSize = 22.sp,
                                    fontFamily = Archivo,
                                    fontWeight = FontWeight.Bold,
                                    color = TribunaAzul
                                )
                            }
                        }
                    }

                    // Estados (Cargando / Error) — el éxito ahora navega, no se muestra acá
                    when (estado) {
                        is CompraUiState.Cargando -> {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = TribunaAzul)
                            }
                        }
                        is CompraUiState.Error -> {
                            Text(
                                text = estado.mensaje,
                                color = MaterialTheme.colorScheme.error,
                                fontFamily = Archivo,
                                fontSize = 13.sp
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}