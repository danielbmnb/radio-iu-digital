package com.app.iudigitalradio.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.iudigitalradio.data.model.RadioStation
import com.app.iudigitalradio.ui.theme.GlassBorder
import com.app.iudigitalradio.ui.theme.GlassCardGradient
import com.app.iudigitalradio.ui.theme.LiveGreen
import com.app.iudigitalradio.ui.theme.NeonCyan
import com.app.iudigitalradio.ui.theme.PrimaryGradient

/**
 * Reproductor interactivo de audio con estilo Glassmorphic, ecualizador neón y controles flotantes (RF-04, RF-05, RF-07).
 */
@Composable
fun RadioPlayerCard(
    station: RadioStation?,
    isPlaying: Boolean,
    isMuted: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onPlayPauseClick: () -> Unit,
    onMuteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animación de pulso para la luz de "EN VIVO"
    val infiniteTransition = rememberInfiniteTransition(label = "pulseLive")
    val livePulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardGradient)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (station != null) {
                    // Encabezado: Frecuencia & Insignia "EN VIVO"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorder)
                        ) {
                            Text(
                                text = station.frequency,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isPlaying) LiveGreen.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.08f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isPlaying) LiveGreen.copy(alpha = 0.5f) else Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .alpha(if (isPlaying) livePulseAlpha else 0.4f)
                                        .background(if (isPlaying) LiveGreen else Color.Gray)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isPlaying) "SEÑAL EN VIVO" else "EN ESPERA",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (isPlaying) LiveGreen else Color.LightGray,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo de la Emisora con Borde Brillante
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF0D1424))
                            .border(1.dp, GlassBorder, RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = station.logoUrl,
                            contentDescription = "Logo de ${station.name}",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(14.dp)),
                            contentScale = ContentScale.Fit,
                            error = null,
                            fallback = null
                        )

                        Icon(
                            imageVector = Icons.Default.Radio,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = NeonCyan.copy(alpha = 0.25f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Título de la Emisora y Género
                    Text(
                        text = station.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = station.genre,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Ecualizador Dinámico de 7 Barras Neón
                    SoundWaveEqualizer(isPlaying = isPlaying)

                    Spacer(modifier = Modifier.height(14.dp))

                    // Mensaje de Error si ocurre falla de streaming
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Controles Interactivos de Audio (Play / Pause / Mute)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón de Silenciador (Mute)
                        IconButton(
                            onClick = onMuteClick,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isMuted) MaterialTheme.colorScheme.error.copy(alpha = 0.25f)
                                    else Color.White.copy(alpha = 0.08f)
                                )
                                .border(
                                    1.dp,
                                    if (isMuted) MaterialTheme.colorScheme.error else GlassBorder,
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = if (isMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = if (isMuted) "Activar sonido" else "Silenciar",
                                tint = if (isMuted) MaterialTheme.colorScheme.error else NeonCyan
                            )
                        }

                        Spacer(modifier = Modifier.width(28.dp))

                        // Botón Flotante Principal Play / Pause (72dp)
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(PrimaryGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = onPlayPauseClick,
                                modifier = Modifier.size(72.dp)
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp),
                                        color = Color.Black,
                                        strokeWidth = 3.5.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                                        modifier = Modifier.size(40.dp),
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Selecciona una emisora del catálogo",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * Componente de ecualizador de 7 barras con animación neón y movimiento asíncrono.
 */
@Composable
fun SoundWaveEqualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "equalizer")

    val h1 by infiniteTransition.animateFloat(
        initialValue = 6f, targetValue = 26f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 22f, targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(450, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 10f, targetValue = 30f,
        animationSpec = infiniteRepeatable(tween(300, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "h3"
    )
    val h4 by infiniteTransition.animateFloat(
        initialValue = 28f, targetValue = 12f,
        animationSpec = infiniteRepeatable(tween(400, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "h4"
    )

    Row(
        modifier = Modifier
            .height(34.dp)
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val heights = if (isPlaying) listOf(h1, h2, h3, h4, h2, h3, h1) else listOf(5f, 5f, 5f, 5f, 5f, 5f, 5f)

        heights.forEach { h ->
            Box(
                modifier = Modifier
                    .width(4.5.dp)
                    .height(h.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        if (isPlaying) PrimaryGradient
                        else androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.2f), Color.White.copy(alpha = 0.2f))
                        )
                    )
            )
        }
    }
}
