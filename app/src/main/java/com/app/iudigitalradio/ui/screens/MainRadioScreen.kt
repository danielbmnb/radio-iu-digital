package com.app.iudigitalradio.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.app.iudigitalradio.ui.components.RadioPlayerCard
import com.app.iudigitalradio.ui.components.StationListSection
import com.app.iudigitalradio.ui.components.UserProfileHeader
import com.app.iudigitalradio.ui.theme.BackgroundGradient
import com.app.iudigitalradio.ui.theme.GlassBorder
import com.app.iudigitalradio.ui.theme.NeonCyan
import com.app.iudigitalradio.ui.theme.PrimaryGradient
import com.app.iudigitalradio.ui.viewmodel.RadioViewModel

/**
 * Pantalla principal con diseño Dark Glassmorphism, fondo con gradiente continuo y layout fluido (RF-01).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRadioScreen(
    viewModel: RadioViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Launcher para captura de foto con la Cámara Nativa (RF-02)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            viewModel.updateUserProfileBitmap(bitmap)
            Toast.makeText(context, "¡Foto de perfil actualizada exitosamente!", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher para Permisos en Tiempo de Ejecución (RF-03)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(
                context,
                "Se requiere permiso de cámara para capturar la foto de perfil.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Verificación de permiso de cámara
    val onCameraRequest = {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            cameraLauncher.launch(null)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGradient)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Radio,
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp),
                                    tint = Color.Black
                                )
                            }

                            Text(
                                text = " IU Digital Radio",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.border(
                        width = 0.5.dp,
                        color = GlassBorder
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // 1. Sección Superior: Perfil de Usuario con Anillo Neón y Cámara en Tiempo Real (RF-02, RF-03)
                UserProfileHeader(
                    profileBitmap = uiState.userProfileBitmap,
                    onCameraClick = { onCameraRequest() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Sección Central: Reproductor Glassmorphic con Ecualizador y Controles (RF-04, RF-05, RF-07)
                RadioPlayerCard(
                    station = uiState.selectedStation,
                    isPlaying = uiState.isPlaying,
                    isMuted = uiState.isMuted,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    onPlayPauseClick = { viewModel.togglePlayPause() },
                    onMuteClick = { viewModel.toggleMute() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Sección Inferior: Catálogo de Emisoras con Chips de Filtro (RF-06)
                StationListSection(
                    stations = uiState.stations,
                    selectedStation = uiState.selectedStation,
                    isPlaying = uiState.isPlaying,
                    onStationSelect = { station ->
                        viewModel.selectStation(station)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
