package com.example.watchplayapp.navigation

sealed class Screens(val route: String) {

    data object Splash : Screens("splash_route")
    data object Home : Screens("home_route")
}
