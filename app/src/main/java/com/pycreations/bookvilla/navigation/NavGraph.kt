package com.pycreations.bookvilla.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pycreations.bookvilla.data.repository.BookRepository
import com.pycreations.bookvilla.screens.BookDetailScreen
import com.pycreations.bookvilla.screens.FavoriteBooks
import com.pycreations.bookvilla.screens.NavigationDrawerScr
import com.pycreations.bookvilla.screens.OnboardingScreen
import com.pycreations.bookvilla.screens.SearchScreen
import com.pycreations.bookvilla.viewmodel.BookViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph() {
    val context = LocalContext.current
    val navHostController: NavHostController = rememberNavController()

    val bookRepository = BookRepository()
    val bookViewModel = BookViewModel(bookRepository)

    NavHost(navController = navHostController, Route.HomeScreenRoute.route) {
        composable(Route.HomeScreenRoute.route) {
            NavigationDrawerScr(bookViewModel, navHostController)
        }

        composable(Route.OnboardingRoute.route) {
            OnboardingScreen()
        }
        composable(
            Route.SearchBookRoute.route + "/{query}",
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) { backStackEntry ->
            SearchScreen(
                navHostController,
                bookViewModel,
                backStackEntry.arguments?.getString("query") ?: ""
            )
        }

        composable(
            Route.BookDetailRoute.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            BookDetailScreen(
                bookViewModel,
                backStackEntry.arguments?.getString("id") ?: "",
                navHostController
            )
        }

        composable(Route.FavoriteBooksRoute.route) {
            FavoriteBooks(navHostController,bookViewModel)
        }
    }
}