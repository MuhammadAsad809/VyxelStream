package com.primevision.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.primevision.app.ui.theme.PrimeObsidianGradient

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
            contentPadding = PaddingValues(bottom = 50.dp)
        ) {
            // 1. Edge-to-Edge Hero Section
            item {
                HeroSection(
                    movie = movie,
                    onBackClick = onBackClick,
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

            // 2. Info Metadata Row (2022 • 1h 45m • [CC] • [HD])
            item {
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.year,
                        color = Color(0xFF90A3A0),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "  •  ",
                        color = Color(0xFF90A3A0),
                        fontSize = 12.sp
                    )
                    Text(
                        text = movie.duration,
                        color = Color(0xFF90A3A0),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "  •  ",
                        color = Color(0xFF90A3A0),
                        fontSize = 12.sp
                    )
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color(0xFF5A6E6A), RoundedCornerShape(3.dp))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "CC",
                            color = Color(0xFF90A3A0),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color(0xFF5A6E6A), RoundedCornerShape(3.dp))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = movie.quality,
                            color = Color(0xFF90A3A0),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 3. Movie Title & IMDb Rating Badge
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
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.3).sp
                    )
                    RatingBadge(rating = movie.rating)
                }
            }

            // 4. Movie Synopsis / Description
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = movie.synopsis,
                    color = Color(0xFF94A5A2),
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            // 5. Visual Stills / Recommended Content Carousel
            item {
                Spacer(modifier = Modifier.height(18.dp))
                val stills = movie.recommendedStills.ifEmpty {
                    listOf(
                        "https://image.tmdb.org/t/p/w500/4HodYYKEIsGOdinkGi2Ucz6X9i0.jpg",
                        "https://image.tmdb.org/t/p/w500/8cmi41E5g3Gk8K8mI7X731k6V9g.jpg"
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(stills) { stillUrl ->
                        val stillShape = RoundedCornerShape(18.dp)
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(115.dp)
                                .clip(stillShape)
                                .background(Color(0xFF0D1716))
                                .border(1.dp, Color(0x22FFFFFF), stillShape)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(stillUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Scene still",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
