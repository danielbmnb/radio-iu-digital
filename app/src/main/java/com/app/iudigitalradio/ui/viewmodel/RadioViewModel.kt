package com.app.iudigitalradio.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.app.iudigitalradio.data.model.RadioStation
import com.app.iudigitalradio.data.repository.RadioRepository
import com.app.iudigitalradio.ui.state.RadioState
import com.app.iudigitalradio.utils.HapticFeedbackManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel central que controla la lógica de negocio y reproducción de audio en tiempo real con Media3 ExoPlayer no bloqueante (RF-04, RF-05, RF-07).
 */
class RadioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RadioRepository()
    private val hapticManager = HapticFeedbackManager(application)

    private val _uiState = MutableStateFlow(RadioState())
    val uiState: StateFlow<RadioState> = _uiState.asStateFlow()

    private var exoPlayer: ExoPlayer? = null

    init {
        val stationsList = repository.getRadioStations()
        val defaultStation = stationsList.firstOrNull()

        _uiState.update {
            it.copy(
                stations = stationsList,
                selectedStation = defaultStation
            )
        }
    }

    @OptIn(UnstableApi::class)
    private fun ensureExoPlayerInitialized(): ExoPlayer {
        if (exoPlayer == null) {
            val dataSourceFactory = androidx.media3.datasource.DefaultHttpDataSource.Factory()
                .setUserAgent("IUDigitalRadio/1.0")
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(15000)
                .setReadTimeoutMs(15000)

            val mediaSourceFactory = androidx.media3.exoplayer.source.DefaultMediaSourceFactory(dataSourceFactory)

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .build()

            exoPlayer = ExoPlayer.Builder(getApplication())
                .setMediaSourceFactory(mediaSourceFactory)
                .setAudioAttributes(audioAttributes, true)
                .setHandleAudioBecomingNoisy(true)
                .build().apply {
                    volume = 1.0f
                    addListener(object : Player.Listener {
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            _uiState.update { it.copy(isPlaying = isPlaying, isLoading = false) }
                        }

                        override fun onPlaybackStateChanged(playbackState: Int) {
                            when (playbackState) {
                                Player.STATE_BUFFERING -> _uiState.update { it.copy(isLoading = true) }
                                Player.STATE_READY -> _uiState.update { it.copy(isLoading = false, errorMessage = null) }
                                Player.STATE_ENDED -> _uiState.update { it.copy(isPlaying = false, isLoading = false) }
                                Player.STATE_IDLE -> {}
                            }
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            val currentStation = _uiState.value.selectedStation
                            if (currentStation?.fallbackStreamUrl != null && !_uiState.value.isUsingFallback) {
                                _uiState.update { it.copy(isUsingFallback = true, isLoading = true) }
                                prepareStationWithUrl(currentStation.fallbackStreamUrl, autoPlay = true)
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isPlaying = false,
                                        isLoading = false,
                                        errorMessage = "Reconectando señal en vivo..."
                                    )
                                }
                            }
                        }
                    })
                }
        }
        return exoPlayer!!
    }

    private fun prepareStation(station: RadioStation, autoPlay: Boolean) {
        _uiState.update { it.copy(isUsingFallback = false) }
        prepareStationWithUrl(station.streamUrl, autoPlay)
    }

    private fun prepareStationWithUrl(url: String, autoPlay: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            val player = ensureExoPlayerInitialized()
            player.stop()
            player.clearMediaItems()

            val uri = Uri.parse(url)
            val mediaItem = MediaItem.fromUri(uri)

            player.setMediaItem(mediaItem)
            player.volume = if (_uiState.value.isMuted) 0.0f else 1.0f
            player.prepare()

            if (autoPlay) {
                player.playWhenReady = true
                player.play()
            }
        }
    }

    /**
     * Cambia la emisora activa y comienza la transmisión.
     */
    fun selectStation(station: RadioStation) {
        hapticManager.triggerStationChangeFeedback()
        _uiState.update {
            it.copy(
                selectedStation = station,
                errorMessage = null,
                isLoading = true,
                isUsingFallback = false
            )
        }
        prepareStation(station, autoPlay = true)
    }

    /**
     * Alterna entre Reproducir y Pausar con vibración háptica.
     */
    fun togglePlayPause() {
        hapticManager.triggerClickFeedback()
        val player = ensureExoPlayerInitialized()
        if (player.isPlaying) {
            player.pause()
        } else {
            if (player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED) {
                _uiState.value.selectedStation?.let { prepareStation(it, autoPlay = true) }
            } else {
                player.play()
            }
        }
    }

    /**
     * Alterna el silenciador de audio (Mute/Unmute) con vibración háptica.
     */
    fun toggleMute() {
        hapticManager.triggerClickFeedback()
        val currentMuted = _uiState.value.isMuted
        val newMuted = !currentMuted
        val newVolume = if (newMuted) 0.0f else 1.0f

        val player = ensureExoPlayerInitialized()
        player.volume = newVolume
        _uiState.update {
            it.copy(
                isMuted = newMuted,
                volume = newVolume
            )
        }
    }

    /**
     * Actualiza la foto de perfil del usuario capturada con la cámara.
     */
    fun updateUserProfileBitmap(bitmap: Bitmap) {
        _uiState.update {
            it.copy(userProfileBitmap = bitmap)
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null
    }
}
