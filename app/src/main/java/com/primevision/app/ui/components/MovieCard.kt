package com.primevision.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.primevision.app.data.model.Movie
import com.primevision.app.ui.theme.PrimeCardBackground
import com.primevision.app.ui.theme.PrimeCardOverlayGradient
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeTextPrimary
import com.primevision.app.ui.theme.PrimeTextSecondary

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 180.dp,
    cardHeight: Dp = 250.dp,
    showRating: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        label = "card_scale"
    )

    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .scale(scale)
            .clip(shape)
            .background(PrimeCardBackground)
            .border(width = 1.dp, color = PrimeGlassBorder, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        // 1. Movie Poster Image with Coil
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUrl)
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Dark Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimeCardOverlayGradient)
        )

        // 3. Bottom Content (Title, Year, Duration, Rating Badge)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Text(
                text = movie.title,
                color = PrimeTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${movie.year} • ${movie.duration}",
                    color = PrimeTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                if (showRating) {
                    RatingBadge(rating = movie.rating)
                }
            }
        }
    }
}
