package com.projectdevs.donacionesapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun BottomAppBar(
    navController: NavController,
    items: List<BottomNavItem> = listOf(BottomNavItem.Home, BottomNavItem.Forum, BottomNavItem.Profile),
) {
    NavigationBar {
        val currentDestination = navController.currentDestination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
            )
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Inicio")

    object Profile : BottomNavItem("profile", Icons.Default.Person, "Perfil")

    object Forum : BottomNavItem("forum", Icons.Default.Menu, "Foro")
}