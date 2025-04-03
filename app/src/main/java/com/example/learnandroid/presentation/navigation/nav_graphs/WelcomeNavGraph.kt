package com.example.learnandroid.presentation.navigation.nav_graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.learnandroid.presentation.navigation.Route
import com.example.learnandroid.presentation.ui.welcome.WelcomeEffect
import com.example.learnandroid.presentation.ui.welcome.WelcomeScreen
import com.example.learnandroid.presentation.ui.welcome.WelcomeViewModel
import com.example.learnandroid.presentation.util.CollectSideEffect

fun NavGraphBuilder.welcomeNavigation(navController: NavController) {
    composable<Route.Welcome> {
        val viewModel: WelcomeViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val effectFlow = viewModel.effects

        WelcomeScreen(
            state = state,
            onEvent = { viewModel.onEvent(it) }
        )

        CollectSideEffect(flow = effectFlow) { effect ->
            when (effect) {
                WelcomeEffect.NavigateToHome -> {
                    navController.navigate(Route.Home) {
                        popUpTo(navController.graph.id)
                    }
                }

                WelcomeEffect.NavigateToLogin -> navController.navigate(Route.Login)

                WelcomeEffect.NavigateToRegister -> navController.navigate(Route.Register)
            }
        }
    }
}