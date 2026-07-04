package com.example.tpi_mobile_001.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.R
import androidx.compose.ui.text.font.Font as LocalFont
import androidx.compose.ui.text.googlefonts.Font as GFont

// Proveedor de Google Fonts (solo para Archivo)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Anton — fuente LOCAL, para títulos grandes (TRIBUNA, CREAR CUENTA, etc.)
val Anton = FontFamily(
    LocalFont(R.font.anton_regular)
)

// Archivo — para el resto del texto (sigue vía Google Fonts)
val ArchivoFont = GoogleFont("Archivo")
val Archivo = FontFamily(
    GFont(googleFont = ArchivoFont, fontProvider = provider, weight = FontWeight.Normal),
    GFont(googleFont = ArchivoFont, fontProvider = provider, weight = FontWeight.Medium),
    GFont(googleFont = ArchivoFont, fontProvider = provider, weight = FontWeight.SemiBold),
    GFont(googleFont = ArchivoFont, fontProvider = provider, weight = FontWeight.Bold)
)

// Tipografía global de la app
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Anton,
        fontWeight = FontWeight.Normal,
        fontSize = 52.sp,
        letterSpacing = 2.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Anton,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 1.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)