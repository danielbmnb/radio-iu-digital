package com.app.iudigitalradio.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Paleta Futurista & Glassmorphic para IU Digital Radio
val NeonCyan = Color(0xFF00F2FE)
val NeonBlue = Color(0xFF4FACFE)
val GoldAccent = Color(0xFFFFB703)
val OrangeAccent = Color(0xFFFB8500)
val LiveGreen = Color(0xFF00E676)

// Fondos y Superficies Cristal Oscuras
val DarkBackgroundStart = Color(0xFF0B0F19)
val DarkBackgroundEnd = Color(0xFF182238)
val GlassSurface = Color(0xFF131B2E)
val GlassSurfaceVariant = Color(0xFF1E2A47)
val GlassBorder = Color(0x26FFFFFF)
val GlassBorderActive = Color(0xFF00F2FE)
val ErrorRed = Color(0xFFFF5252)

// Gradientes Listos para Componentes
val PrimaryGradient = Brush.horizontalGradient(
    colors = listOf(NeonCyan, NeonBlue)
)

val AccentGradient = Brush.horizontalGradient(
    colors = listOf(GoldAccent, OrangeAccent)
)

val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(DarkBackgroundStart, DarkBackgroundEnd)
)

val GlassCardGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0x2B2A3B5C),
        Color(0x1A131B2E)
    )
)
