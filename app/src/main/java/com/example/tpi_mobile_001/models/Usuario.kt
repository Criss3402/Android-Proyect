package com.example.tpi_mobile_001.models

data class Usuario(
    val email: String,
    val password: String
)

// Modelo simple de usuario para el login/registro.
// Por ahora solo tiene email y password porque no hay backend todavía.
// Cuando se conecte la API real (TP6 etapa 2), probablemente se agreguen
// más campos como id, nombre, etc.