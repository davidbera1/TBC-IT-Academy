package com.example.learnandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.learnandroid.presentation.navigation.nav_graphs.homeNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.loginNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.profileNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.registerNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.welcomeNavigation
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Route = Route.Welcome
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        welcomeNavigation(navController)
        loginNavigation(navController)
        registerNavigation(navController)
        homeNavigation(navController)
        profileNavigation(navController)
    }
}

sealed class Route {
    @Serializable
    data object Welcome : Route()

    @Serializable
    data object Login : Route()

    @Serializable
    data object Register : Route()

    @Serializable
    data object Home : Route()

    @Serializable
    data object Profile : Route()
}