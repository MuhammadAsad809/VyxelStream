package com.primevision.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primevision.app.data.model.Movie
import com.primevision.app.data.plugin.Cs3PluginLoader
import com.primevision.app.data.repository.MovieRepository
import com.primevision.app.ui.components.GlassChip
import com.primevision.app.ui.components.GlassNavigationBar
import com.primevision.app.ui.components.HomeHeader
import com.primevision.app.ui.components.MovieCard
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.theme.PrimeGlassBorder
import com.primevision.app.ui.theme.PrimeGlassPill
import com.primevision.app.ui.theme.PrimeObsidianGradient
import com.primevision.app.ui.theme.PrimeTextMuted
import com.primevision.app.ui.theme.PrimeTextPrimary
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onMovieClick: (String) -> Unit,
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val movies by MovieRepository.movies.collectAsState()
    val series by MovieRepository.series.collectAsState()

    var selectedCategoryId by remember { mutableStateOf("most_watched") }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Movie>>(emptyList()) }

    // Initialize .cs3 plugins and live TMDB feeds on first launch
    LaunchedEffect(Unit) {
        Cs3PluginLoader.initialize(context)
        MovieRepository.loadLiveTrendingFeeds()
    }

    // Dynamic search across local and loaded extensions
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            searchResults = MovieRepository.search(searchQuery)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimeObsidianGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // 1. Top Header (Logo + Action Buttons)
            HomeHeader(
                onSearchClick = { isSearchActive = !isSearchActive },
                onFilterClick = { onNavigate(NavItem.EXPLORE) }
            )

            // Search Bar (expandable)
            AnimatedVisibility(visible = isSearchActive) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                "Search movies, series, or extension streams...",
                                color = PrimeTextMuted,
                                fontSize = 13.sp
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PrimeGlassPill,
                            unfocusedContainerColor = PrimeGlassPill,
                            focusedBorderColor = Color(0x66FFFFFF),
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

            // 3. Feeds: Movies & Series Carousels
            LazyColumn(
                contentPadding = PaddingValues(bottom = 110.dp),
                modifier = Modifier.weight(1f)
            ) {
                if (searchQuery.isNotBlank()) {
                    item {
                        Text(
                            text = "Results for \"$searchQuery\"",
                            color = Color(0xFF90A3A0),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(searchResults, key = { it.id }) { movie ->
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }
                } else {
                    // Movies Section
                    item {
                        Text(
                            text = "Movies",
                            color = Color(0xFF90A3A0),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(movies, key = { it.id }) { movie ->
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }

                    // Series Section
                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Series",
                            color = Color(0xFF90A3A0),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
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
