package com.projectdevs.donacionesapp.ui.navigation

import android.R.attr.type
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectdevs.donacionesapp.domain.donaciones
import com.projectdevs.donacionesapp.ui.components.BottomAppBar
import com.projectdevs.donacionesapp.ui.components.BottomNavItem
import com.projectdevs.donacionesapp.ui.screens.DonationCreateScreen
import com.projectdevs.donacionesapp.ui.screens.DonationDetailScreen
import com.projectdevs.donacionesapp.ui.screens.DonationRequestsScreen
import com.projectdevs.donacionesapp.ui.screens.EditProfileScreen
import com.projectdevs.donacionesapp.ui.screens.HomeScreen
import com.projectdevs.donacionesapp.ui.screens.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomAppBar(navController = navController) },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier
                .padding(
                    bottom = padding.calculateBottomPadding(),
                    top = 0.dp
                ),
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onItemClick = { donation ->
                        navController.navigate("detail/${donation.id}")
                    },
                    onAddClick = { /* TODO */ },
                    donaciones = donaciones,
                )
            }

            composable(BottomNavItem.Forum.route) {
                DonationRequestsScreen(
                    onNavigateToCreate = { navController.navigate("create_donation")}
                )
            }

            composable("create_donation") {
                DonationCreateScreen(
                    onClose = { navController.popBackStack()},
                    onCreate = { navController.popBackStack()}
                )
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    onEditClick = { navController.navigate("edit_profile")}
                )
            }

            composable("edit_profile") {
                EditProfileScreen(navController = navController)
            }

            composable(
                route = "detail/{donationId}",
                arguments = listOf(navArgument("donationId") {type = NavType.IntType})
            ) { backStackEntry ->
                val donationId = backStackEntry.arguments?.getInt("donationId") ?: return@composable
                val donation = donaciones.find { it.id == donationId }!!
                DonationDetailScreen(
                    donation = donation,
                    onBackClick = {navController.popBackStack()}
                )
            }
        }
    }
}
