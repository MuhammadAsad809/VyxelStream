package com.primevision.app.data.model

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val year: String,
    val duration: String,
    val rating: String = "8.2",
    val contentRating: String = "CC",
    val quality: String = "HD",
    val synopsis: String = "",
    val genres: List<String> = emptyList(),
    val isSeries: Boolean = false,
    val platform: String = "HBO Max",
    val isFavorite: Boolean = false,
    val recommendedStills: List<String> = emptyList()
)

data class Category(
    val id: String,
    val name: String
)
