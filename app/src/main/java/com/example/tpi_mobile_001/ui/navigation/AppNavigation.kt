package com.example.tpi_mobile_001.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.ui.screens.DetallePartidoScreen
import com.example.tpi_mobile_001.ui.screens.ListaPartidosScreen
import com.example.tpi_mobile_001.ui.screens.LoginScreen
import com.example.tpi_mobile_001.ui.screens.RegisterScreen
import com.example.tpi_mobile_001.viewmodel.AuthViewModel
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel
import com.example.tpi_mobile_001.ui.screens.CompraEntradaScreen


@Composable
fun AppNavigation(viewModel: PartidoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // ViewModel de autenticación, separado del de partidos.
    // Cada uno maneja su propia responsabilidad (principio MVVM).
    val authViewModel: AuthViewModel = viewModel()
    var partidoSeleccionado by remember { mutableStateOf<Partido?>(null) }

    NavHost(
        navController = navController,
        startDestination = "login", //ahora arranca en login, no en la lista
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginExitoso = {
                    navController.navigate("lista") {
                        // Borra "login" del historial de navegación.
                        // Así si tocás "atrás" en la lista de partidos,
                        // no volvés al login, sale de la app directamente.
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate("registro") }
            )
        }
        composable("registro") {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegistroExitoso = {
                    navController.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onVolverALogin = { navController.popBackStack() }
            )
        }
        composable("lista") {
            ListaPartidosScreen(
                viewModel = viewModel,
                onPartidoClick = { partido ->
                    partidoSeleccionado = partido
                    navController.navigate("detalle")
                },
                onCerrarSesion = {
                    // Reseteamos la sesión y volvemos al login,
                    // borrando tod0 el historial de navegación (popUpTo(0))
                    // para que no se pueda volver atrás a la lista sin loguearse.
                    authViewModel.cerrarSesion()
                    navController.navigate("login"){
                        popUpTo(0){ inclusive = true}
                    }
                }
            )
        }
        composable("detalle") {
            partidoSeleccionado?.let { partido ->
                DetallePartidoScreen(
                    partido = partido,
                    onVolver = { navController.popBackStack() },
                    onComprar = { navController.navigate("comprar") }
                )
            }
        }
        composable("comprar") {
            partidoSeleccionado?.let { partido ->
                CompraEntradaScreen(
                    partido = partido,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}