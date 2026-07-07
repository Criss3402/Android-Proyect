package com.example.tpi_mobile_001.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tpi_mobile_001.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(4000)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(TribunaAzulClaro, TribunaAzulOscuro),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 800f)
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
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo barras
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.height(50.dp)
            ) {
                Box(modifier = Modifier.width(10.dp).height(22.dp).background(Color.White, RoundedCornerShape(2.dp)))
                Box(modifier = Modifier.width(10.dp).height(36.dp).background(Color.White, RoundedCornerShape(2.dp)))
                Box(modifier = Modifier.width(10.dp).height(50.dp).background(Color.White, RoundedCornerShape(2.dp)))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "TRIBUNA",
                fontSize = 64.sp,
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                letterSpacing = 3.sp
            )

            Text(
                text = "MUNDIAL FIFA",
                fontSize = 14.sp,
                fontFamily = Archivo,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.9f),
                letterSpacing = 5.sp
            )

            Text(
                text = "2026",
                fontSize = 14.sp,
                fontFamily = Archivo,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.9f),
                letterSpacing = 5.sp
            )
        }
    }
}