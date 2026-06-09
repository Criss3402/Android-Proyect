package com.example.tpi_mobile_001.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpi_mobile_001.ui.screens.ListaPartidosScreen
import com.example.tpi_mobile_001.viewmodel.PartidoViewModel

@Composable
fun AppNavigation(viewModel: PartidoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista",
        modifier = modifier
    ) {
        composable("lista") {
            ListaPartidosScreen(viewModel = viewModel)
        }
    }
}