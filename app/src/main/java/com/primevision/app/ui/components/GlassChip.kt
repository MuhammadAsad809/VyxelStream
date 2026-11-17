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

@Composable
fun GlassChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipShape = RoundedCornerShape(22.dp)

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0x33253633) else Color(0x1A14201E),
        label = "chip_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0x66FFFFFF) else Color(0x22FFFFFF),
        label = "chip_border"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color(0xFF8E9E9C),
        label = "chip_text"
    )

    Box(
        modifier = modifier
            .clip(chipShape)
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = chipShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            letterSpacing = (-0.1).sp
        )
    }
}
