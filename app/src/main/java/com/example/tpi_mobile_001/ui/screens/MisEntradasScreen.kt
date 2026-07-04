package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.models.EntradaDto
import com.example.tpi_mobile_001.network.RetrofitClient
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.AuthViewModel
import com.example.tpi_mobile_001.ui.components.getBandera

@Composable
fun MisEntradasScreen(authViewModel: AuthViewModel) {
    var entradas by remember { mutableStateOf<List<EntradaDto>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            entradas = RetrofitClient.entradaApi.misEntradas()
        } catch (e: Exception) {
            error = "Error al cargar las entradas"
        } finally {
            cargando = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoApp)
    ) {
        // ── Header ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FondoCard)
                .padding(horizontal = 20.dp, vertical = 16.dp),
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
                    text = "MIS ENTRADAS",
                    fontSize = 18.sp,
                    fontFamily = Anton,
                    fontWeight = FontWeight.Normal,
                    color = TextoPrimario,
                    letterSpacing = 1.sp
                )
            }
            // Contador de entradas
            if (entradas.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = FondoSeleccion
                ) {
                    Text(
                        text = "${entradas.size}",
                        fontSize = 12.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TribunaAzul,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        HorizontalDivider(color = Borde)

        // ── Contenido ──
        when {
            cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = TribunaAzul)
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error!!, color = MaterialTheme.colorScheme.error, fontFamily = Archivo)
                }
            }
            entradas.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🎫", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No tenés entradas todavía",
                            fontSize = 16.sp,
                            fontFamily = Anton,
                            fontWeight = FontWeight.Normal,
                            color = TextoPrimario
                        )
                        Text(
                            text = "Comprá tu primera entrada desde la lista de partidos",
                            fontSize = 13.sp,
                            fontFamily = Archivo,
                            color = TextoSecundario,
                            modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "TUS ENTRADAS",
                            fontSize = 11.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TextoSecundario,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    items(entradas) { entrada ->
                        EntradaCard(entrada = entrada)
                    }
                }
            }
        }
    }
}

@Composable
fun EntradaCard(entrada: EntradaDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = FondoCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Badge grupo + estado válida
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = FondoSeleccion
                ) {
                    Text(
                        text = entrada.fase,
                        fontSize = 11.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TribunaAzul,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                    )
                    Text(
                        text = "Válida",
                        fontSize = 11.sp,
                        fontFamily = Archivo,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Equipos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = getBandera(entrada.equipoLocalNombre), fontSize = 22.sp)
                    Text(
                        text = entrada.equipoLocalNombre,
                        fontSize = 15.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrimario,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "vs",
                    fontSize = 12.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = entrada.equipoVisitanteNombre,
                        fontSize = 15.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrimario,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(text = getBandera(entrada.equipoVisitanteNombre), fontSize = 22.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estadio + fecha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "📍", fontSize = 11.sp)
                Text(
                    text = "${entrada.estadio} · ${entrada.fechaPartido} · ${entrada.horaPartido.take(5)}",
                    fontSize = 11.sp,
                    fontFamily = Archivo,
                    color = TextoSecundario
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Borde, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Sector + precio + ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = entrada.sectorNombre,
                        fontSize = 14.sp,
                        fontFamily = Archivo,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrimario
                    )
                    Text(
                        text = "Entrada #${entrada.entradaId} · comprada ${entrada.fechaCompra.take(10)}",
                        fontSize = 11.sp,
                        fontFamily = Archivo,
                        color = TextoSecundario,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Text(
                    text = "$${entrada.precio.toInt()}",
                    fontSize = 16.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TribunaAzul
                )
            }
        }
    }
}