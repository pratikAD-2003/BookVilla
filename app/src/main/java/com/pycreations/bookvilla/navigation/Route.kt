package com.pycreations.bookvilla.navigation

sealed class Route(val route: String) {
    object OnboardingRoute : Route(route = "onboarding_screen")
    object HomeScreenRoute : Route(route = "home_screen")
    object SearchBookRoute : Route(route = "search_book_scr")
    object BookDetailRoute : Route(route = "book_detail_scr")
    object FavoriteBooksRoute : Route(route = "favorite_books_scr")
}