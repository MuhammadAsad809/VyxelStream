package com.primevision.app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.primevision.app.ui.components.NavItem
import com.primevision.app.ui.screens.ExploreScreen
import com.primevision.app.ui.screens.FavoritesScreen
import com.primevision.app.ui.screens.HomeScreen
import com.primevision.app.ui.screens.MovieDetailsScreen
import com.primevision.app.ui.screens.ProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val handleNavigation: (NavItem) -> Unit = { item ->
        val targetRoute = when (item) {
            NavItem.HOME -> Screen.Home.route
            NavItem.EXPLORE -> Screen.Explore.route
            NavItem.FAVORITES -> Screen.Favorites.route
            NavItem.PROFILE -> Screen.Profile.route
        }
        if (navController.currentDestination?.route != targetRoute) {
            navController.navigate(targetRoute) {
                popUpTo(Screen.Home.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier.fillMaxSize(),
        enterTransition = { fadeIn(animationSpec = tween(250)) },
        exitTransition = { fadeOut(animationSpec = tween(250)) },
        popEnterTransition = { fadeIn(animationSpec = tween(250)) },
        popExitTransition = { fadeOut(animationSpec = tween(250)) }
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onNavigate = handleNavigation
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: "mavka"
            MovieDetailsScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onNavigate = handleNavigation
            )
        }

        composable(route = Screen.Explore.route) {
            ExploreScreen(
                onNavigate = handleNavigation
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigate = handleNavigation
            )
        }
    }
}
