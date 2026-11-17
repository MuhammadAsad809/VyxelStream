package com.primevision.app.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Details : Screen("details/{movieId}") {
        fun createRoute(movieId: String) = "details/$movieId"
    }
    data object Favorites : Screen("favorites")
    data object Explore : Screen("explore")
    data object Profile : Screen("profile")
}
