package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.AuthViewModel
import com.example.tpi_mobile_001.ui.theme.Anton


@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoApp)
    ) {
        // ── Header azul con rayas verticales ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.52f) // ocupa ~52% de la pantalla
                .background(
                    Brush.linearGradient(
                        colors = listOf(TribunaAzulClaro, TribunaAzulOscuro),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 800f)
                    )
                )
                // Rayas verticales sutiles
                .drawBehind {
                    val stripeWidth = 30.dp.toPx()
                    var x = 0f
                    while (x < size.width) {
                        drawRect(
                            color = Color.White.copy(alpha = 0.06f),
                            topLeft = Offset(x, 0f),
                            size = androidx.compose.ui.geometry.Size(stripeWidth / 2, size.height)
                        )
                        x += stripeWidth
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 26.dp)
            ) {
                // Logo barras
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.height(34.dp)
                ) {
                    Box(modifier = Modifier.width(7.dp).height(15.dp).background(Color.White, RoundedCornerShape(2.dp)))
                    Box(modifier = Modifier.width(7.dp).height(25.dp).background(Color.White, RoundedCornerShape(2.dp)))
                    Box(modifier = Modifier.width(7.dp).height(34.dp).background(Color.White, RoundedCornerShape(2.dp)))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "TRIBUNA",
                    fontSize = 52.sp,
                    fontFamily = Anton,  // agregá esta línea
                    color = Color.White,
                    letterSpacing = 3.sp
                )
                // "MUNDIAL FIFA" en una línea, "2026" en otra
                Text(
                    text = "MUNDIAL FIFA",
                    fontSize = 12.sp,
                    fontFamily = Anton,
                    color = Color.White.copy(alpha = 0.9f),
                    letterSpacing = 5.sp
                )
                Text(
                    text = "2026",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.9f),
                    letterSpacing = 5.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Iniciá sesión para ver los partidos y comprar tus entradas.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // ── Formulario — sin título, directo a los campos ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.48f)
                .offset(y = (-22).dp)
                .background(
                    FondoApp,
                    RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
                )
                .padding(horizontal = 26.dp)
                .padding(top = 32.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Campo usuario — sin título encima del form, directo al label
            Column {
                Text(
                    text = "USUARIO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Usuario", color = TextoPlaceholder) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Borde,
                        focusedBorderColor = TribunaAzul,
                        unfocusedContainerColor = FondoCard,
                        focusedContainerColor = FondoCard
                    )
                )
            }

            // Campo contraseña
            Column {
                Text(
                    text = "CONTRASEÑA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••", color = TextoPlaceholder) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Borde,
                        focusedBorderColor = TribunaAzul,
                        unfocusedContainerColor = FondoCard,
                        focusedContainerColor = FondoCard
                    )
                )
            }

            // Error
            authViewModel.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            // Botón
            Button(
                onClick = {
                    authViewModel.login(username, password, onExito = onLoginExitoso)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TribunaAzul)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Link registro
            TextButton(
                onClick = onIrARegistro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "¿No tenés cuenta? ", color = TextoSecundario, fontSize = 13.sp)
                Text(text = "Registrate", color = TribunaAzul, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}