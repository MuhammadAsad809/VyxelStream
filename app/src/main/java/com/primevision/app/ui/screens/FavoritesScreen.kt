package com.primevision.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.data.repository.MovieRepository
import com.primevision.app.ui.components.GlassChip
import com.primevision.app.ui.components.GlassNavigationBar
import com.primevision.app.ui.components.MovieCard
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary

@Composable
fun FavoritesScreen(
    onMovieClick: (String) -> Unit,
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val movies by MovieRepository.movies.collectAsState()
    var selectedPlatform by remember { mutableStateOf("HBO Max") }

    val favorites = remember(movies, selectedPlatform) {
        movies.filter { it.isFavorite && (selectedPlatform.isEmpty() || it.platform == selectedPlatform) }
            .ifEmpty { movies.filter { it.isFavorite } }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            // 1. Centered Header "Favourites"
            Text(
                text = "Favourites",
                color = PrimeTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.2).sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Horizontal Streaming Platform Chips (HBO Max, Disney+, Apple TV, Netflix)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(MovieRepository.platforms) { platform ->
                    GlassChip(
                        text = platform,
                        isSelected = platform == selectedPlatform,
                        onClick = { selectedPlatform = platform }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Featured Movie Card (Large cinematic card matching reference)
            if (favorites.isNotEmpty()) {
                val featured = favorites.first()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCard(
                        movie = featured,
                        onClick = { onMovieClick(featured.id) },
                        cardWidth = 310.dp,
                        cardHeight = 440.dp,
                        showRating = true
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favourites in this category yet.",
                        color = PrimeTextMuted,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 4. Floating Bottom Glass Navigation Bar
        GlassNavigationBar(
            selectedItem = NavItem.FAVORITES,
            onItemSelected = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
