package com.projectdevs.donacionesapp.ui.navigation

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectdevs.donacionesapp.domain.donaciones
import com.projectdevs.donacionesapp.ui.components.BottomAppBar
import com.projectdevs.donacionesapp.ui.components.BottomNavItem
import com.projectdevs.donacionesapp.ui.screens.ChatScreen
import com.projectdevs.donacionesapp.ui.screens.DonationCreateScreen
import com.projectdevs.donacionesapp.ui.screens.DonationDetailScreen
import com.projectdevs.donacionesapp.ui.screens.DonationHistoryScreen
import com.projectdevs.donacionesapp.ui.screens.DonationRequestsScreen
import com.projectdevs.donacionesapp.ui.screens.DonatorProfileScreen
import com.projectdevs.donacionesapp.ui.screens.EditProfileScreen
import com.projectdevs.donacionesapp.ui.screens.ForumChatScreen
import com.projectdevs.donacionesapp.ui.screens.HomeScreen
import com.projectdevs.donacionesapp.ui.screens.InitialScreen
import com.projectdevs.donacionesapp.ui.screens.LoginScreen
import com.projectdevs.donacionesapp.ui.screens.PostScreen
import com.projectdevs.donacionesapp.ui.screens.ProfileScreen
import com.projectdevs.donacionesapp.ui.screens.RegisterScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val routesToExclude = listOf(
        Screen.Login.route,
        Screen.Register.route,
        "InitialScreen",
        "edit_profile",
        "chatScreen",
        "forumChatScreen",
        "postScreen",
        "donation_history/{category}",
        "create_donation",
        "donator_profile/{donorId}",
        "detail/{donationId}"
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != null && currentRoute !in routesToExclude) {
                BottomAppBar(navController = navController)
            }
        },
        floatingActionButton = {
            when (currentRoute) {

                BottomNavItem.Home.route -> {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("postScreen")},
                        text = { Text("Nueva publicación") },
                        icon = { Icon(Icons.Filled.Add, null)},
                        modifier = Modifier.offset(y = 8.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                }

                BottomNavItem.Forum.route -> {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("create_donation")},
                        text = { Text("Nuevo pedido") },
                        icon = { Icon(Icons.Filled.Add, null)},
                        modifier = Modifier.offset(y = 8.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                }

                BottomNavItem.Profile.route -> {
//                    FloatingActionButton(
//                        onClick = { navController.navigate("postScreen")},
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = Color.Black,
//                        modifier = Modifier.offset(y = 8.dp),
//                    ) {
//                        Icon(
//                            Icons.Filled.Add,
//                            "Agregar donacion"
//                        )
//                    }
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("postScreen")},
                        text = { Text("Nueva publicación") },
                        icon = { Icon(Icons.Filled.Add, null)},
                        modifier = Modifier.offset(y = 8.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                }

                else -> {}
            }
        }
        ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "InitialScreen",
            modifier = Modifier
                .padding(
                    start = padding.calculateStartPadding(LocalLayoutDirection.current),
                    end = padding.calculateEndPadding(LocalLayoutDirection.current)
                )
                .padding(
                    bottom = if (currentRoute != null && currentRoute !in routesToExclude) padding.calculateBottomPadding() else 0.dp
                )
        ) {

            composable("InitialScreen"){
                InitialScreen(
                    navigateToLogin = { navController.navigate(Screen.Login.route)},
                    navigateToRegister = { navController.navigate(Screen.Register.route)}
                )
            }

            composable(Screen.Register.route){
                RegisterScreen(
                    navigateToHome = { navController.navigate(BottomNavItem.Home.route)},
                    navigateBack = { navController.popBackStack()}
                )
            }


            composable("chatScreen"){
                ChatScreen(navController = navController)
            }

            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToRegister = { navController.navigate(Screen.Register.route)},
                    navigateToHome = { navController.navigate(Screen.Home.route)}

                )
            }

            composable("forumChatScreen"){
                ForumChatScreen(navigateBack = { navController.popBackStack()})
            }

            composable("postScreen") {
                PostScreen(
                    onBackClick = {navController.popBackStack()},
                    navController = navController
                )
            }
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onItemClick = { donation ->
                        navController.navigate("detail/${donation.id}")
                    },
                    donaciones = donaciones,
                )
            }

            composable(BottomNavItem.Forum.route) {
                DonationRequestsScreen(
                    onNavigateToChat = { navController.navigate("forumChatScreen")}
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
                    onEditClick = { navController.navigate("edit_profile") },
                    onBackClick = { navController.navigate("home") },
//                    onAddClick = { navController.navigate("postScreen") },
                    onDonationCardClick = { category ->
                        navController.navigate("donation_history/$category")
                    }
                )
            }

            composable("edit_profile") {
                EditProfileScreen(navController = navController)
            }

            composable(
                route = "donator_profile/{donorId}",

                arguments = listOf(
                    navArgument("donorId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val donorId = backStackEntry.arguments?.getInt("donorId") ?: 0

                DonatorProfileScreen(
                    navigateBack = { navController.popBackStack() },
                    navController = navController,
                    donorId = donorId,
                    donaciones = donaciones,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(
                route = "donation_history/{category}",
                arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "Error"
                DonationHistoryScreen(
                    navController = navController,
                    category = category,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = "detail/{donationId}",
                arguments = listOf(navArgument("donationId") {type = NavType.IntType})
            ) { backStackEntry ->
                val donationId = backStackEntry.arguments?.getInt("donationId") ?: return@composable
                val donation = donaciones.find { it.id == donationId }!!
                DonationDetailScreen(
                    donation = donation,
                    onBackClick = {navController.popBackStack()},
                    navController = navController
                )
            }
        }
    }
}
