package com.example.learnandroid.presentation.navigation.nav_graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.learnandroid.presentation.navigation.Routes
import com.example.learnandroid.presentation.ui.home.HomeEffect
import com.example.learnandroid.presentation.ui.home.HomeScreen
import com.example.learnandroid.presentation.ui.home.HomeViewModel
import com.example.learnandroid.presentation.util.CollectSideEffect

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(Routes.HOME) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value

        HomeScreen(
            state = state,
            onEvent = { viewModel.onEvent(it) }
        )

        CollectSideEffect(flow = viewModel.effects) { effect ->
            when (effect) {
                HomeEffect.NavigateToProfile -> navController.navigate(Routes.PROFILE)
            }
        }
    }
}