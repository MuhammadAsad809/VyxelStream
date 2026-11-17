package com.primevision.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.primevision.app.ui.navigation.AppNavigation
import com.primevision.app.ui.theme.PrimeBackground
import com.primevision.app.ui.theme.PrimeVisionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeVisionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimeBackground
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
