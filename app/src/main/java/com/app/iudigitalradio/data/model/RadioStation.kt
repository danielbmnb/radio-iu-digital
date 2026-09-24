package com.app.iudigitalradio.data.model

import androidx.compose.runtime.Immutable

/**
 * Modelo de datos que representa una emisora de radio con sus atributos de streaming principal y respaldo.
 */
@Immutable
data class RadioStation(
    val id: String,
    val name: String,
    val frequency: String,
    val streamUrl: String,
    val fallbackStreamUrl: String? = null,
    val logoUrl: String,
    val genre: String,
    val description: String,
    val isLive: Boolean = true
)
