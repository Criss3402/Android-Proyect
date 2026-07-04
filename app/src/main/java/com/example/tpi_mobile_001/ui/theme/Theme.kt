package com.example.tpi_mobile_001.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val TribunaColorScheme = lightColorScheme(
    primary = TribunaAzul,
    onPrimary = FondoCard,
    primaryContainer = FondoSeleccion,
    secondary = TribunaAzulOscuro,
    background = FondoApp,
    surface = FondoCard,
    onBackground = TextoPrimario,
    onSurface = TextoPrimario,
    outline = Borde,
    onSurfaceVariant = TextoSecundario
)

@Composable
fun TribunaTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = TribunaAzul.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = TribunaColorScheme,
        typography = Typography,
        content = content
    )
}