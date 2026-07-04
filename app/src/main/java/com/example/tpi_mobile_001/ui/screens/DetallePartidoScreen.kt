package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.ui.components.getBandera
import com.example.tpi_mobile_001.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.ui.draw.drawBehind

fun formatearFechaDetalle(fecha: String): String {
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
fun DetallePartidoScreen(partido: Partido, onVolver: () -> Unit, onComprar: () -> Unit) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FondoApp)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = onComprar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TribunaAzul)
                ) {
                    Text(
                        text = "Comprar entrada",
                        fontSize = 15.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(FondoApp)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header azul con TopBar + equipos ──
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
                            text = "Detalle del Partido",
                            fontSize = 16.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    // Badge fase — centrado
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = partido.fase,
                                fontSize = 11.sp,
                                fontFamily = Archivo,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                            )
                        }
                    }

                    // Equipos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .padding(bottom = 36.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Local
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getBandera(partido.equipoLocalNombre), fontSize = 58.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = partido.equipoLocalNombre.uppercase(),
                                fontSize = 15.sp,
                                fontFamily = Anton,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )
                        }

                        // VS o resultado
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            if (partido.golesLocal != null && partido.golesVisitante != null) {
                                Text(
                                    text = "${partido.golesLocal} - ${partido.golesVisitante}",
                                    fontSize = 32.sp,
                                    fontFamily = Anton,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = "VS",
                                    fontSize = 24.sp,
                                    fontFamily = Anton,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White
                                )
                                Text(
                                    text = partido.hora.take(5),
                                    fontSize = 13.sp,
                                    fontFamily = Archivo,
                                    color = Color.White.copy(alpha = 0.75f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        // Visitante
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getBandera(partido.equipoVisitanteNombre), fontSize = 58.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = partido.equipoVisitanteNombre.uppercase(),
                                fontSize = 15.sp,
                                fontFamily = Anton,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            // ── Info del partido ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .background(FondoApp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(horizontal = 24.dp, vertical = 28.dp),
            ) {
                InfoRow(emoji = "📍", label = "Estadio", valor = partido.estadio)
                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 12.dp))
                InfoRow(emoji = "🏆", label = "Fase", valor = partido.fase)
                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 12.dp))
                InfoRow(emoji = "📅", label = "Fecha", valor = formatearFechaDetalle(partido.fecha))
                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 12.dp))
                InfoRow(emoji = "🕐", label = "Hora", valor = partido.hora.take(5))
                HorizontalDivider(color = Borde, modifier = Modifier.padding(vertical = 12.dp))
                InfoRow(
                    emoji = "⚽",
                    label = "Goles",
                    valor = if (partido.golesLocal != null && partido.golesVisitante != null)
                        "${partido.golesLocal} - ${partido.golesVisitante}"
                    else
                        "Por jugarse"
                )
            }
        }
    }
}

@Composable
fun InfoRow(emoji: String, label: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 16.sp)
            Text(text = label, fontSize = 14.sp, fontFamily = Archivo, color = TextoSecundario)
        }
        Text(
            text = valor,
            fontSize = 14.sp,
            fontFamily = Archivo,
            fontWeight = FontWeight.SemiBold,
            color = TextoPrimario
        )
    }
}