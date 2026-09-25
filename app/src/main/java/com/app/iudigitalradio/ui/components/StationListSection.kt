package com.app.iudigitalradio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.iudigitalradio.data.model.RadioStation
import com.app.iudigitalradio.ui.theme.GlassBorder
import com.app.iudigitalradio.ui.theme.GlassCardGradient
import com.app.iudigitalradio.ui.theme.NeonCyan
import com.app.iudigitalradio.ui.theme.PrimaryGradient

/**
 * Catálogo interactivo de emisoras con Chips de filtrado por categoría y tarjetas Glassmorphic (RF-06).
 */
@Composable
fun StationListSection(
    stations: List<RadioStation>,
    selectedStation: RadioStation?,
    isPlaying: Boolean,
    onStationSelect: (RadioStation) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("Todas") }
    val categories = listOf("Todas", "Institucional", "Noticias", "Música")

    // Filtrado de emisoras por categoría
    val filteredStations = remember(selectedCategory, stations) {
        if (selectedCategory == "Todas") {
            stations
        } else {
            stations.filter { it.genre.contains(selectedCategory, ignoreCase = true) }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Catálogo de Emisoras",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "${filteredStations.size} disponibles",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Chips de Filtrado Horizontal
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
        ) {
            items(categories) { category ->
                val isCategorySelected = category == selectedCategory
                FilterChip(
                    selected = isCategorySelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            fontSize = 12.sp,
                            fontWeight = if (isCategorySelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan,
                        selectedLabelColor = Color.Black,
                        containerColor = Color.White.copy(alpha = 0.08f),
                        labelColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isCategorySelected,
                        selectedBorderColor = NeonCyan,
                        borderColor = GlassBorder
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Emisoras
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredStations.forEach { station ->
                val isSelected = selectedStation?.id == station.id
                StationItemCard(
                    station = station,
                    isSelected = isSelected,
                    isPlaying = isSelected && isPlaying,
                    onClick = { onStationSelect(station) }
                )
            }
        }
    }
}

/**
 * Tarjeta individual de la emisora con bordes de luz neón al seleccionarse.
 */
@Composable
fun StationItemCard(
    station: RadioStation,
    isSelected: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                brush = if (isSelected) PrimaryGradient else androidx.compose.ui.graphics.Brush.linearGradient(
                    listOf(GlassBorder, GlassBorder)
                ),
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isSelected) GlassCardGradient
                    else androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(Color(0x1F1E2A47), Color(0x0F131B2E))
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo de la Emisora
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0D1424))
                        .border(1.dp, GlassBorder, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = station.logoUrl,
                        contentDescription = station.name,
                        modifier = Modifier.size(42.dp),
                        contentScale = ContentScale.Fit
                    )

                    Icon(
                        imageVector = Icons.Default.Radio,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = NeonCyan.copy(alpha = 0.3f)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Metadata de la Emisora
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = station.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = station.frequency,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = station.description,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Badge de Emisora Activa
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = if (isPlaying) "Reproduciendo" else "Seleccionada",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}
