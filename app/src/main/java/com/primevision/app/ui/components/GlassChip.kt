package com.primevision.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeGlassPillActive
import com.primevision.app.ui.theme.PrimePrimary
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary

@Composable
fun GlassChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) PrimeGlassPillActive else PrimeGlassPill,
        label = "chip_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) PrimePrimary else PrimeGlassBorder,
        label = "chip_border"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) PrimeTextPrimary else PrimeTextMuted,
        label = "chip_text"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}
