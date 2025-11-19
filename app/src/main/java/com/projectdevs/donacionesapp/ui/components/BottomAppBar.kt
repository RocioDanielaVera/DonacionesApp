package com.projectdevs.donacionesapp.ui.components

import android.R.attr.label
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomAppBar(
    navController: NavController,
    items: List<BottomNavItem> = listOf(BottomNavItem.Home, BottomNavItem.Forum, BottomNavItem.Profile),
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar (
        containerColor = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier.height(70.dp)
    ) {
        items.forEach { item ->
            val selected = currentDestination == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .height(3.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF5ACF74))
                            )
                            Spacer(Modifier.height(3.dp))
                        } else {
                            Spacer(Modifier.height(4.dp))
                        }

                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp),
                            tint = if (selected) Color(0xFF5ACF74) else Color.Gray
                        )

                        Spacer(Modifier.height(1.dp))

                        Text(
                            item.label,
                            fontSize = 10.sp,
                            color = if (selected) Color(0xFF5ACF74) else Color.Gray
                        )
                    }
                },

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color(0xFF5ACF74),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF5ACF74),
                    unselectedTextColor = Color.Gray
                )
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

    object Forum : BottomNavItem("forum", Icons.Default.Forum, "Foro")
}