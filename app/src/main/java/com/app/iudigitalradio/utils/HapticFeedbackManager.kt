package com.app.iudigitalradio.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Gestor del servicio de vibración nativo para retroalimentación háptica (RF-05).
 * Soporta Android 12+ (VibratorManager) con retrocompatibilidad para versiones anteriores (Vibrator).
 */
class HapticFeedbackManager(context: Context) {

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    /**
     * Dispara una pulsación háptica corta (50ms) al presionar un botón de control (Play, Pause, Mute).
     */
    fun triggerClickFeedback() {
        vibrator?.let { v ->
            if (v.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(
                        VibrationEffect.createOneShot(
                            50L,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(50L)
                }
            }
        }
    }

    /**
     * Dispara un patrón de vibración doble para confirmaciones especiales (ej. cambio de emisora).
     */
    fun triggerStationChangeFeedback() {
        vibrator?.let { v ->
            if (v.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val timings = longArrayOf(0, 30, 50, 30)
                    val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
                    v.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(longArrayOf(0, 30, 50, 30), -1)
                }
            }
        }
    }
}
