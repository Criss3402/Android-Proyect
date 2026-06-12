package com.example.tpi_mobile_001.ui.navigation


import com.example.tpi_mobile_001.models.Partido
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.tpi_mobile_001.ui.screens.DetallePartidoScreen
import com.example.tpi_mobile_001.ui.screens.ListaPartidosScreen
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel

@Composable
fun AppNavigation(viewModel: PartidoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var partidoSeleccionado by remember { mutableStateOf<Partido?>(null) }

    NavHost(
        navController = navController,
        startDestination = "lista",
        modifier = modifier
    ) {
        composable("lista") {
            ListaPartidosScreen(
                viewModel = viewModel,
                onPartidoClick = { partido ->
                    partidoSeleccionado = partido
                    navController.navigate("detalle")
                }
            )
        }
        composable("detalle") {
            partidoSeleccionado?.let { partido ->
                DetallePartidoScreen(
                    partido = partido,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}