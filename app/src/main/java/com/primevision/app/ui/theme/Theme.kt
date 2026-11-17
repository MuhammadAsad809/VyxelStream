package com.primevision.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimePrimary,
    onPrimary = PrimeBackground,
    primaryContainer = PrimeSecondary,
    onPrimaryContainer = PrimeTextPrimary,
    secondary = PrimeSecondary,
    onSecondary = PrimeTextPrimary,
    background = PrimeBackground,
    onBackground = PrimeTextPrimary,
    surface = PrimeSurface,
    onSurface = PrimeTextPrimary,
    surfaceVariant = PrimeSurfaceElevated,
    onSurfaceVariant = PrimeTextSecondary
)

@Composable
fun PrimeVisionTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = PrimeBackground.toArgb()
                it.navigationBarColor = PrimeBackground.toArgb()
                val controller = WindowCompat.getInsetsController(it, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
