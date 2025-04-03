package com.example.learnandroid.presentation.navigation.nav_graphs

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.learnandroid.presentation.navigation.Route
import com.example.learnandroid.presentation.ui.profile.ProfileEffect
import com.example.learnandroid.presentation.ui.profile.ProfileScreen
import com.example.learnandroid.presentation.ui.profile.ProfileViewModel
import com.example.learnandroid.presentation.util.CollectSideEffect

fun NavGraphBuilder.profileNavigation(navController: NavController) {
    composable<Route.Profile> {
        val viewModel: ProfileViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val snackbarHostState = remember { SnackbarHostState() }

        ProfileScreen(
            state = state,
            onEvent = { viewModel.onEvent(it) },
            snackbarHostState = snackbarHostState
        )

        CollectSideEffect(flow = viewModel.effects) { effect ->
            when (effect) {
                is ProfileEffect.NavigateToWelcome -> {
                    navController.navigate(Route.Welcome) {
                        popUpTo(navController.graph.id)
                    }
                }

                is ProfileEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }
}