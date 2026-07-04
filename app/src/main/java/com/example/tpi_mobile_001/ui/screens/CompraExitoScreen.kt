package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.ui.components.getBandera
import com.example.tpi_mobile_001.ui.theme.*

@Composable
fun CompraExitoScreen(
    partido: Partido,
    entradaId: Int,
    sectorNombre: String,
    precio: Int,
    onVolver: () -> Unit,
    onVolverALista: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ── TopBar ──
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
                    tint = TextoPrimario
                )
            }
            Text(
                text = "Comprar entrada",
                fontSize = 16.sp,
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = TextoPrimario,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        HorizontalDivider(color = Borde)

        // ── Contenido centrado ──
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Check circular
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .background(FondoSeleccion, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .background(TribunaAzul, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Entrada comprada\ncon éxito",
                fontSize = 26.sp,
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = TextoPrimario,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tu entrada quedó registrada a tu nombre.",
                fontSize = 14.sp,
                fontFamily = Archivo,
                color = TextoSecundario,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card resumen
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FondoCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, Borde),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ID DE ENTRADA",
                            fontSize = 11.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TextoSecundario,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "#$entradaId",
                            fontSize = 14.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TribunaAzul
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = getBandera(partido.equipoLocalNombre), fontSize = 20.sp)
                        Text(
                            text = "${partido.equipoLocalNombre} vs ${partido.equipoVisitanteNombre}",
                            fontSize = 15.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrimario
                        )
                        Text(text = getBandera(partido.equipoVisitanteNombre), fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(13.dp))
                    HorizontalDivider(color = Borde)
                    Spacer(modifier = Modifier.height(13.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sectorNombre,
                            fontSize = 13.sp,
                            fontFamily = Archivo,
                            color = TextoSecundario
                        )
                        Text(
                            text = "$$precio",
                            fontSize = 16.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrimario
                        )
                    }
                }
            }
        }

        // ── Botón volver a la lista ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            OutlinedButton(
                onClick = onVolverALista,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(13.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Borde),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextoPrimario)
            ) {
                Text(
                    text = "Volver a la lista",
                    fontSize = 14.5.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}