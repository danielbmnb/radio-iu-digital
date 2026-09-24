package com.app.iudigitalradio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color.Black,
    primaryContainer = GlassSurfaceVariant,
    onPrimaryContainer = Color.White,
    secondary = GoldAccent,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF2C2208),
    onSecondaryContainer = GoldAccent,
    tertiary = NeonBlue,
    background = DarkBackgroundStart,
    onBackground = Color.White,
    surface = GlassSurface,
    onSurface = Color.White,
    surfaceVariant = GlassSurfaceVariant,
    onSurfaceVariant = Color(0xFFB0C4DE),
    error = ErrorRed
)

@Composable
fun IUDigitalRadioTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
