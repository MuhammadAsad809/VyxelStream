package com.primevision.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Obsidian & Dark Surface Palette
val PrimeBackground = Color(0xFF050B0B)
val PrimeSurface = Color(0xFF0B1918)
val PrimeSurfaceElevated = Color(0xFF102423)
val PrimeCardBackground = Color(0xFF0E1C1B)

// Brand Emerald & Teal
val PrimePrimary = Color(0xFF13B8A6)
val PrimeSecondary = Color(0xFF0C7770)
val PrimeTealLight = Color(0xFF2DD4BF)
val PrimeTealDark = Color(0xFF094D48)

// Gold / Accent (Logo & Rating Badge)
val PrimeAccentGold = Color(0xFFD8B64A)
val ImdbYellow = Color(0xFFF5C518)
val ImdbBlack = Color(0xFF000000)

// Text Colors
val PrimeTextPrimary = Color(0xFFFFFFFF)
val PrimeTextSecondary = Color(0xFFA6B8B5)
val PrimeTextMuted = Color(0xFF6B7F7C)

// Glassmorphism Values
val PrimeGlassBackground = Color(0x1F243A37)
val PrimeGlassHighlight = Color(0x24FFFFFF)
val PrimeGlassBorder = Color(0x33446B66)
val PrimeGlassPill = Color(0x2B1A2E2B)
val PrimeGlassPillActive = Color(0x5213B8A6)

// Ambient Gradients
val PrimeObsidianGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF071212),
        Color(0xFF050B0B),
        Color(0xFF030707)
    )
)

val PrimeCardOverlayGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color(0x33050B0B),
        Color(0xCC050B0B),
        Color(0xFA050B0B)
    )
)

val PrimeHeroOverlayGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color(0x22050B0B),
        Color(0x99050B0B),
        Color(0xFF050B0B)
    )
)

val PrimeGoldFlameGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFD54F),
        Color(0xFFF57C00)
    )
)
