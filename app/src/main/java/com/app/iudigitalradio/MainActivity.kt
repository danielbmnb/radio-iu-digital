package com.app.iudigitalradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.iudigitalradio.ui.screens.MainRadioScreen
import com.app.iudigitalradio.ui.theme.IUDigitalRadioTheme
import com.app.iudigitalradio.ui.viewmodel.RadioViewModel

/**
 * Actividad Principal de IU Digital Radio.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IUDigitalRadioTheme {
                val radioViewModel: RadioViewModel = viewModel()
                MainRadioScreen(viewModel = radioViewModel)
            }
        }
    }
}
