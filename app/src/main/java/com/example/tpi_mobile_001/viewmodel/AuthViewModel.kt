package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tpi_mobile_001.models.Usuario

class AuthViewModel : ViewModel() {
    // Usuario hardcodeado mientras no hay backend
    // Lista en memoria que simula una "base de datos" temporal de usuarios.
    // Ya viene con un usuario de prueba para poder loguearse sin registrarse.
    private val usuariosRegistrados = mutableListOf(
        Usuario(email = "admin@test.com", password = "123456")
    )


    // Bandera que indica si hay sesión activa.
    // La navegación (AppNavigation) la usa para decidir si redirigir
    // al login o dejar pasar a la lista de partidos.
    var isLoggedIn by mutableStateOf(false)

    // Mensaje de error para mostrar en la UI (credenciales incorrectas,
    // usuario ya existe, etc.)
    var errorMessage by mutableStateOf<String?>(null)


    // Busca si existe un usuario con ese email y password exactos.
    // Si lo encuentra, marca la sesión como iniciada.
    fun login(email: String, password: String) {
        val usuario = usuariosRegistrados.find { it.email == email && it.password == password }
        if (usuario != null) {
            isLoggedIn = true
            errorMessage = null
        } else {
            errorMessage = "Email o contraseña incorrectos"
        }
    }

    // Verifica que el email no esté ya registrado, y si no lo está,
    // lo agrega a la lista en memoria y loguea automáticamente.
    fun registrar(email: String, password: String) {
        val yaExiste = usuariosRegistrados.any { it.email == email }
        if (yaExiste) {
            errorMessage = "El usuario ya existe"
        } else {
            usuariosRegistrados.add(Usuario(email, password))
            isLoggedIn = true
            errorMessage = null
        }
    }
}