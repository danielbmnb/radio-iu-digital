package com.app.iudigitalradio.ui.state

import android.graphics.Bitmap
import com.app.iudigitalradio.data.model.RadioStation

/**
 * Estado inmutable de la interfaz de usuario de IU Digital Radio (RF-04).
 */
data class RadioState(
    val stations: List<RadioStation> = emptyList(),
    val selectedStation: RadioStation? = null,
    val isPlaying: Boolean = false,
    val isMuted: Boolean = false,
    val volume: Float = 1.0f,
    val userProfileBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val isUsingFallback: Boolean = false,
    val errorMessage: String? = null
)
