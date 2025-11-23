package com.projectdevs.donacionesapp.ui.navigation

sealed class Screen(
    val route: String,
) {
    data object Chat : Screen("chat")

    data object Forum : Screen("forum")

    data object Home : Screen("home")
    data object Register : Screen("register")
    data object Login : Screen("login")

    data object Profile : Screen("profile")

}
