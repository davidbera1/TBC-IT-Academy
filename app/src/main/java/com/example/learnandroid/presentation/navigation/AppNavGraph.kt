package com.example.learnandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.learnandroid.presentation.navigation.nav_graphs.homeNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.loginNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.profileNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.registerNavigation
import com.example.learnandroid.presentation.navigation.nav_graphs.welcomeNavigation

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Routes.WELCOME
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

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "registration"
    const val HOME = "home"
    const val PROFILE = "profile"
}