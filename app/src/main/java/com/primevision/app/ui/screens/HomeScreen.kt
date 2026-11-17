package com.primevision.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.data.repository.MovieRepository
import com.primevision.app.ui.components.GlassChip
import com.primevision.app.ui.components.GlassNavigationBar
import com.primevision.app.ui.components.HomeHeader
import com.primevision.app.ui.components.MovieCard
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.components.SectionHeader
import com.primevision.app.ui.theme.PrimeBackground
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimePrimary
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary

@Composable
fun HomeScreen(
    onMovieClick: (String) -> Unit,
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val movies by MovieRepository.movies.collectAsState()
    val series by MovieRepository.series.collectAsState()

    var selectedCategoryId by remember { mutableStateOf("most_watched") }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredMovies = remember(searchQuery, movies) {
        if (searchQuery.isBlank()) movies else MovieRepository.search(searchQuery)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(24.dp))

            // 1. Top Header
            HomeHeader(
                onSearchClick = { isSearchActive = !isSearchActive },
                onFilterClick = { /* Show filter/settings */ }
            )

            // Search bar (animated expandable)
            AnimatedVisibility(visible = isSearchActive) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search movies, genres, series...", color = PrimeTextMuted, fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PrimeGlassPill,
                            unfocusedContainerColor = PrimeGlassPill,
                            focusedBorderColor = PrimePrimary,
                            unfocusedBorderColor = PrimeGlassBorder,
                            focusedTextColor = PrimeTextPrimary,
                            unfocusedTextColor = PrimeTextPrimary
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 2. Horizontally scrollable Category Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                items(MovieRepository.categories) { category ->
                    GlassChip(
                        text = category.name,
                        isSelected = category.id == selectedCategoryId,
                        onClick = { selectedCategoryId = category.id }
                    )
                }
            }

            // 3. Main Feeds (Movies & Series Carousels)
            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Movies Section
                item {
                    SectionHeader(title = if (searchQuery.isBlank()) "Movies" else "Search Results")
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredMovies, key = { it.id }) { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) }
                            )
                        }
                    }
                }

                // Series Section
                if (searchQuery.isBlank()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SectionHeader(title = "Series")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(series, key = { it.id }) { show ->
                                MovieCard(
                                    movie = show,
                                    onClick = { onMovieClick(show.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Floating Bottom Glass Navigation Bar
        GlassNavigationBar(
            selectedItem = NavItem.HOME,
            onItemSelected = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
