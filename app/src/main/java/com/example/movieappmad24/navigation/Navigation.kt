package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.dependencyInjector.Injector
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.ViewModelDetail
import com.example.movieappmad24.viewmodels.ViewModelHomeMovies
import com.example.movieappmad24.viewmodels.ViewModelWatchlistMovies

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModelFactory = Injector.provideMoviesViewModelFactory(context)

    val viewModelDetail: ViewModelDetail = viewModel(factory = viewModelFactory)
    val viewModelHome: ViewModelHomeMovies = viewModel(factory = viewModelFactory)
    val viewModelWatchlist: ViewModelWatchlistMovies = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, viewModelHome = viewModelHome)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                viewModelDetail = viewModelDetail
            )
        }

        composable(route = Screen.WatchlistScreen.route) {
            WatchlistScreen(navController = navController, viewModelWatchlist = viewModelWatchlist)
        }
    }
}