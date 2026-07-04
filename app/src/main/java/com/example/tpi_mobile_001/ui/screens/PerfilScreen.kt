package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.models.EntradaDto
import com.example.tpi_mobile_001.network.RetrofitClient
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.AuthViewModel

@Composable
fun PerfilScreen(
    authViewModel: AuthViewModel,
    onCerrarSesion: () -> Unit
) {
    val username = authViewModel.usuarioUsername ?: "Usuario"
    val iniciales = username.take(2).uppercase()
    val context = LocalContext.current

    var entradas by remember { mutableStateOf<List<EntradaDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            entradas = RetrofitClient.entradaApi.misEntradas()
        } catch (e: Exception) {
            // si falla, se queda en 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoApp)
    ) {
        // ── Header azul con rayas ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(TribunaAzulClaro, TribunaAzulOscuro),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
                .drawBehind {
                    val stripeWidth = 30.dp.toPx()
                    var x = 0f
                    while (x < size.width) {
                        drawRect(
                            color = Color.White.copy(alpha = 0.06f),
                            topLeft = Offset(x, 0f),
                            size = Size(stripeWidth / 2, size.height)
                        )
                        x += stripeWidth
                    }
                }
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Avatar con iniciales
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = iniciales,
                        fontSize = 20.sp,
                        fontFamily = Anton,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }

                Column {
                    Text(
                        text = username.uppercase(),
                        fontSize = 22.sp,
                        fontFamily = Anton,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "@$username",
                        fontSize = 13.sp,
                        fontFamily = Archivo,
                        color = Color.White.copy(alpha = 0.75f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        // ── Contenido ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .background(FondoApp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card de stats — Entradas
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FondoCard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TUS ENTRADAS",
                            fontSize = 11.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = TextoSecundario,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "${entradas.size}",
                            fontSize = 30.sp,
                            fontFamily = Anton,
                            fontWeight = FontWeight.Normal,
                            color = TribunaAzul,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(FondoSeleccion, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎫", fontSize = 22.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Opciones
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = FondoCard
            ) {
                Column {
                    OpcionPerfil(
                        emoji = "👤",
                        texto = "Datos de la cuenta",
                        onClick = {
                            android.widget.Toast.makeText(context, "Próximamente", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider(color = Borde, modifier = Modifier.padding(horizontal = 16.dp))
                    OpcionPerfil(
                        emoji = "🔒",
                        texto = "Cambiar contraseña",
                        onClick = {
                            android.widget.Toast.makeText(context, "Próximamente", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider(color = Borde, modifier = Modifier.padding(horizontal = 16.dp))
                    OpcionPerfil(
                        emoji = "❓",
                        texto = "Ayuda y soporte",
                        onClick = {
                            android.widget.Toast.makeText(context, "Próximamente", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón cerrar sesión
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = FondoCard,
                border = BorderStroke(1.dp, Color(0xFFF0D2D2))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        onClick = onCerrarSesion,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            fontSize = 15.sp,
                            fontFamily = Archivo,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpcionPerfil(emoji: String, texto: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(FondoSeleccion, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 14.sp)
            }
            Text(
                text = texto,
                fontSize = 14.sp,
                fontFamily = Archivo,
                fontWeight = FontWeight.Medium,
                color = TextoPrimario
            )
        }
        Text(text = "›", fontSize = 18.sp, color = Color(0xFFC4C4BD))
    }
}