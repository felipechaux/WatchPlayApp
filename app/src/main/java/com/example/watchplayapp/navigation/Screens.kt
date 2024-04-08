package com.example.watchplayapp.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home_route")
    object Search : Screens("search_route")
}
