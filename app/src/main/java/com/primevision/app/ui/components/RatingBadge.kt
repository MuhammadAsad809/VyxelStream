package com.primevision.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.ui.theme.ImdbBlack
import com.primevision.app.ui.theme.ImdbYellow

@Composable
fun RatingBadge(
    rating: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(ImdbYellow)
            .padding(horizontal = 5.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "IMDb ",
            color = ImdbBlack,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = rating,
            color = ImdbBlack,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
