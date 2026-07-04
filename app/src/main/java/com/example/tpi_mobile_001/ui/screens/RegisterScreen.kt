package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.AuthViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegistroExitoso: () -> Unit,
    onVolverALogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoApp)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Header azul con rayas ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(TribunaAzulClaro, TribunaAzulOscuro),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 600f)
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.18f), CircleShape)
                            .clickable { onVolverALogin() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "CREAR CUENTA",
                        fontSize = 28.sp,
                        fontFamily = Anton,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // ── Formulario ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-22).dp)
                .background(
                    FondoApp,
                    RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
                )
                .padding(horizontal = 26.dp)
                .padding(top = 32.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Subtítulo
            Column(modifier = Modifier.padding(bottom = 2.dp)) {
                Text(
                    text = "TUS DATOS",
                    fontSize = 22.sp,
                    fontFamily = Anton,
                    fontWeight = FontWeight.Normal,
                    color = TextoPrimario,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Creá tu cuenta gratis en un minuto",
                    fontSize = 13.sp,
                    fontFamily = Archivo,
                    color = TextoSecundario,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Username
            Column {
                Text(
                    text = "NOMBRE DE USUARIO",
                    fontSize = 11.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = {
                        Text(
                            "Usuario",
                            color = TextoPlaceholder,
                            fontFamily = Archivo
                        )
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Archivo,
                        fontSize = 15.sp
                    ),
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

            // Email
            Column {
                Text(
                    text = "EMAIL",
                    fontSize = 11.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            "usuario@email.com",
                            color = TextoPlaceholder,
                            fontFamily = Archivo
                        )
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Archivo,
                        fontSize = 15.sp
                    ),
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

            // Contraseña
            Column {
                Text(
                    text = "CONTRASEÑA",
                    fontSize = 11.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            "••••••••",
                            color = TextoPlaceholder,
                            fontFamily = Archivo
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Archivo,
                        fontSize = 15.sp
                    ),
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

            // Confirmar contraseña
            Column {
                Text(
                    text = "CONFIRMAR CONTRASEÑA",
                    fontSize = 11.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    color = TextoSecundario,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 7.dp)
                )
                OutlinedTextField(
                    value = confirmarPassword,
                    onValueChange = { confirmarPassword = it },
                    placeholder = {
                        Text(
                            "••••••••",
                            color = TextoPlaceholder,
                            fontFamily = Archivo
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = Archivo,
                        fontSize = 15.sp
                    ),
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
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = Archivo,
                    fontSize = 13.sp
                )
            }

            // Botón
            Button(
                onClick = {
                    if (password != confirmarPassword) {
                        authViewModel.errorMessage = "Las contraseñas no coinciden"
                    } else {
                        authViewModel.registrar(
                            username,
                            email,
                            password,
                            onExito = onRegistroExitoso
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TribunaAzul)
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 15.sp,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold
                )
            }

            // Link login
            TextButton(
                onClick = onVolverALogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "¿Ya tenés cuenta? ",
                    color = TextoSecundario,
                    fontFamily = Archivo,
                    fontSize = 13.sp
                )
                Text(
                    text = "Iniciá sesión",
                    color = TribunaAzul,
                    fontFamily = Archivo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}