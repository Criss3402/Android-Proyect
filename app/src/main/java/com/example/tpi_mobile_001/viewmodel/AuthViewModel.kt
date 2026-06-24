package com.example.tpi_mobile_001.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpi_mobile_001.models.LoginRequest
import com.example.tpi_mobile_001.models.RegistroRequest
import com.example.tpi_mobile_001.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthViewModel : ViewModel() {

    // Bandera que indica si hay sesión activa.
    // La navegación (AppNavigation) la usa para decidir si redirigir
    // al login o dejar pasar a la lista de partidos.
    var isLoggedIn by mutableStateOf(false)

    // Mensaje de error para mostrar en la UI (credenciales incorrectas,
    // usuario ya existe, fallo de conexión, etc.)
    var errorMessage by mutableStateOf<String?>(null)

    // El token JWT recibido al loguearse. Se guarda en memoria para
    // usarlo después en pedidos protegidos (ej: comprar una entrada).
    var token: String? = null
        private set

    // El Id del usuario logueado, también sacado de la respuesta del login.
    var usuarioId: Int? = null
        private set

    // Llama a POST /api/usuarios/login. Es asíncrono porque implica una
    // llamada de red real. Recibe un "onExito" que se ejecuta SOLO si el
    // login terminó bien — así la pantalla que lo llama sabe el momento
    // exacto en que puede navegar a la siguiente vista, en vez de adivinar.
    fun login(username: String, password: String, onExito: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.usuarioApi.login(
                    LoginRequest(username = username, password = password)
                )
                token = respuesta.token
                usuarioId = respuesta.usuarioId
                isLoggedIn = true
                errorMessage = null
                onExito()
            } catch (e: HttpException) {
                errorMessage = if (e.code() == 401) {
                    "Usuario o contraseña incorrectos"
                } else {
                    "Error al iniciar sesión (código ${e.code()})"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            }
        }
    }

    // Llama a POST /api/usuarios/registro. Si el registro es exitoso,
    // loguea automáticamente al usuario recién creado, y recién ahí
    // ejecuta el "onExito" (delegado al login, que es quien realmente
    // confirma que la sesión quedó iniciada).
    fun registrar(username: String, email: String, password: String, onExito: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                RetrofitClient.usuarioApi.registrar(
                    RegistroRequest(username = username, email = email, password = password)
                )
                login(username, password, onExito)
            } catch (e: HttpException) {
                errorMessage = if (e.code() == 409) {
                    "Ese username ya está en uso"
                } else {
                    "Error al registrarse (código ${e.code()})"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            }
        }
    }

    // Cierra la sesión: borra el token y vuelve al estado de "no logueado".
    fun cerrarSesion() {
        isLoggedIn = false
        token = null
        usuarioId = null
    }
}