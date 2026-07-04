package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.ui.components.getBandera
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.PartidoUiState
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun formatearFechaCompleta(fecha: String): String {
    return try {
        val date = LocalDate.parse(fecha)
        val dia = date.dayOfMonth
        val mes = date.month.getDisplayName(TextStyle.SHORT, Locale("es")).uppercase()
        "$dia $mes"
    } catch (e: Exception) {
        fecha
    }
}

@Composable
fun ListaPartidosScreen(
    viewModel: PartidoViewModel,
    onPartidoClick: (Partido) -> Unit,
    onCerrarSesion: () -> Unit
) {
    var grupoSeleccionado by remember { mutableStateOf<String?>(null) }
    val chips = listOf("Todos", "Grupo A", "Grupo B", "Grupo C", "Grupo D",
        "Grupo E", "Grupo F", "Grupo G", "Grupo H", "Grupo I",
        "Grupo J", "Grupo K", "Grupo L")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoCard)
    ) {
        // ── Header ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FondoCard)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(Modifier.width(4.dp).height(8.dp).background(TribunaAzul, RoundedCornerShape(1.dp)))
                    Box(Modifier.width(4.dp).height(13.dp).background(TribunaAzul, RoundedCornerShape(1.dp)))
                    Box(Modifier.width(4.dp).height(18.dp).background(TribunaAzul, RoundedCornerShape(1.dp)))
                }
                Text(
                    text = "TRIBUNA",
                    fontSize = 22.sp,
                    fontFamily = Anton,
                    fontWeight = FontWeight.Normal,
                    color = TextoPrimario,
                    letterSpacing = 1.sp
                )
            }
            TextButton(onClick = onCerrarSesion) {
                Text(
                    text = "Cerrar sesión",
                    color = TribunaAzul,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }

        // ── Chips ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FondoCard)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            chips.forEach { chip ->
                val seleccionado = (chip == "Todos" && grupoSeleccionado == null) ||
                        chip == grupoSeleccionado
                FilterChip(
                    selected = seleccionado,
                    onClick = { grupoSeleccionado = if (chip == "Todos") null else chip },
                    label = {
                        Text(
                            text = chip,
                            fontSize = 13.sp,
                            fontFamily = Archivo,
                            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TribunaAzul,
                        selectedLabelColor = Color.White,
                        containerColor = FondoApp,
                        labelColor = TextoSecundario
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = seleccionado,
                        borderColor = Borde,
                        selectedBorderColor = TribunaAzul
                    )
                )
            }
        }

        HorizontalDivider(color = Borde)

        // ── Lista ──
        when (val state = viewModel.uiState) {
            is PartidoUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = TribunaAzul)
                }
            }
            is PartidoUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.mensaje, color = MaterialTheme.colorScheme.error, fontFamily = Archivo)
                }
            }
            is PartidoUiState.Success -> {
                val partidosFiltrados = if (grupoSeleccionado == null) state.partidos
                else state.partidos.filter { it.fase == grupoSeleccionado }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(FondoApp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(partidosFiltrados) { partido ->
                        PartidoCard(partido = partido, onClick = { onPartidoClick(partido) })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PartidoCard(partido: Partido, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = FondoCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {

            // Badge grupo + fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge en una sola línea
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = FondoSeleccion
                ) {
                    Text(
                        text = partido.fase,
                        fontSize = 11.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TribunaAzul,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Fecha "11 JUN · 16:00"
                Text(
                    text = "${formatearFechaCompleta(partido.fecha)} · ${partido.hora.take(5)}",
                    fontSize = 12.sp,
                    fontFamily = Archivo,
                    color = TextoSecundario
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Equipos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Local
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = getBandera(partido.equipoLocalNombre), fontSize = 28.sp)
                    Text(
                        text = partido.equipoLocalNombre,
                        fontSize = 16.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrimario,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }

                // VS centro fijo
                Text(
                    text = "vs",
                    fontSize = 13.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Visitante — alineado a la derecha dentro de su mitad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = partido.equipoVisitanteNombre,
                        fontSize = 16.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrimario,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = getBandera(partido.equipoVisitanteNombre), fontSize = 28.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Borde, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Estadio con pin rojo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "📍", fontSize = 12.sp)
                Text(
                    text = partido.estadio,
                    fontSize = 12.sp,
                    fontFamily = Archivo,
                    color = TextoSecundario
                )
            }
        }
    }
}