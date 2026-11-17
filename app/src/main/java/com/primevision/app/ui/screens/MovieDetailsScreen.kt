package com.primevision.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.primevision.app.data.repository.MovieRepository
import com.primevision.app.ui.components.HeroSection
import com.primevision.app.ui.components.RatingBadge
import com.primevision.app.ui.theme.PrimeBackground
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimeTextPrimary
import com.primevision.app.ui.theme.PrimeTextSecondary

@Composable
fun MovieDetailsScreen(
    movieId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val movie = MovieRepository.getMovieById(movieId) ?: MovieRepository.getMovieById("mavka")!!

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            // 1. Edge-to-Edge Hero Section
            item {
                HeroSection(
                    movie = movie,
                    onPlayClick = {
                        Toast.makeText(context, "Streaming: ${movie.title}", Toast.LENGTH_SHORT).show()
                    },
                    onDownloadClick = {
                        Toast.makeText(context, "Downloading: ${movie.title} (HD)", Toast.LENGTH_SHORT).show()
                    },
                    onMoreClick = {
                        MovieRepository.toggleFavorite(movie.id)
                        val msg = if (movie.isFavorite) "Removed from Favourites" else "Added to Favourites"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // 2. Info Metadata Row (2022 • 1h 45m • CC • HD)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${movie.year}  •  ${movie.duration}  •  ${movie.contentRating}  •  ${movie.quality}",
                        color = PrimeTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // 3. Movie Title & Rating Badge
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        color = PrimeTextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.3).sp
                    )

                    RatingBadge(rating = movie.rating)
                }
            }

            // 4. Synopsis / Description
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = movie.synopsis,
                    color = PrimeTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            // 5. Recommended Stills / Collections
            if (movie.recommendedStills.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(movie.recommendedStills) { stillUrl ->
                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(95.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .border(1.dp, PrimeGlassBorder, RoundedCornerShape(18.dp))
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(stillUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Scene",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Floating Back Button
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 20.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(PrimeGlassPill)
                .border(1.dp, PrimeGlassBorder, CircleShape)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = PrimeTextPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
