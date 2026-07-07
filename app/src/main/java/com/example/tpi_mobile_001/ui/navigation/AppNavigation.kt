package com.example.tpi_mobile_001.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tpi_mobile_001.models.Partido
import com.example.tpi_mobile_001.ui.screens.*
import com.example.tpi_mobile_001.ui.theme.*
import com.example.tpi_mobile_001.viewmodel.AuthViewModel
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel

@Composable
fun AppNavigation(viewModel: PartidoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    var partidoSeleccionado by remember { mutableStateOf<Partido?>(null) }

    // Datos de la compra recién hecha, para la pantalla de éxito
    var entradaIdExito by remember { mutableStateOf(0) }
    var sectorNombreExito by remember { mutableStateOf("") }
    var precioExito by remember { mutableStateOf(0) }

    // Ruta actual para saber cuándo mostrar el bottom nav
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Bottom nav solo visible en las 3 pantallas principales
    val mostrarBottomNav = currentRoute in listOf("lista", "entradas", "perfil")

    Scaffold(
        bottomBar = {
            if (mostrarBottomNav) {
                NavigationBar(
                    containerColor = FondoCard,

                    ) {
                    NavigationBarItem(
                        selected = currentRoute == "lista",
                        onClick = {
                            navController.navigate("lista") {
                                popUpTo("lista") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.SportsSoccer, contentDescription = "Partidos")
                        },
                        label = {
                            Text(
                                text = "Partidos",
                                fontSize = 11.sp,
                                fontFamily = Archivo,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TribunaAzul,
                            selectedTextColor = TribunaAzul,
                            unselectedIconColor = TextoSecundario,
                            unselectedTextColor = TextoSecundario,
                            indicatorColor = FondoSeleccion
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "entradas",
                        onClick = {
                            navController.navigate("entradas") {
                                popUpTo("lista") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.ConfirmationNumber, contentDescription = "Entradas")
                        },
                        label = {
                            Text(
                                text = "Entradas",
                                fontSize = 11.sp,
                                fontFamily = Archivo,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TribunaAzul,
                            selectedTextColor = TribunaAzul,
                            unselectedIconColor = TextoSecundario,
                            unselectedTextColor = TextoSecundario,
                            indicatorColor = FondoSeleccion
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "perfil",
                        onClick = {
                            navController.navigate("perfil") {
                                popUpTo("lista") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Person, contentDescription = "Perfil")
                        },
                        label = {
                            Text(
                                text = "Perfil",
                                fontSize = 11.sp,
                                fontFamily = Archivo,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TribunaAzul,
                            selectedTextColor = TribunaAzul,
                            unselectedIconColor = TextoSecundario,
                            unselectedTextColor = TextoSecundario,
                            indicatorColor = FondoSeleccion
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("splash") {
                SplashScreen(
                    onFinish = {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            composable("login") {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginExitoso = {
                        navController.navigate("lista") {
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
                        authViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
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
                        onVolver = { navController.popBackStack() },
                        onCompraExitosa = { entradaId, sectorNombre, precio ->
                            entradaIdExito = entradaId
                            sectorNombreExito = sectorNombre
                            precioExito = precio
                            navController.navigate("exito")
                        }
                    )
                }
            }
            composable("exito") {
                partidoSeleccionado?.let { partido ->
                    CompraExitoScreen(
                        partido = partido,
                        entradaId = entradaIdExito,
                        sectorNombre = sectorNombreExito,
                        precio = precioExito,
                        onVolver = { navController.popBackStack() },
                        onVolverALista = {
                            navController.navigate("lista") {
                                popUpTo("lista") { inclusive = true }
                            }
                        }
                    )
                }
            }
            composable("entradas") {
                MisEntradasScreen(authViewModel = authViewModel)
            }
            composable("perfil") {
                PerfilScreen(
                    authViewModel = authViewModel,
                    onCerrarSesion = {
                        authViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}