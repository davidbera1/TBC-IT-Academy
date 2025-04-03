package com.example.learnandroid.presentation.navigation.nav_graphs

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.learnandroid.presentation.navigation.Routes
import com.example.learnandroid.presentation.ui.register.RegisterEffect
import com.example.learnandroid.presentation.ui.register.RegisterScreen
import com.example.learnandroid.presentation.ui.register.RegisterViewModel
import com.example.learnandroid.presentation.util.CollectSideEffect

fun NavGraphBuilder.registerNavigation(navController: NavController) {
    composable(Routes.REGISTER) {
        val viewModel: RegisterViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val snackbarHostState = remember { SnackbarHostState() }

        RegisterScreen(
            state = state,
            onEvent = { viewModel.onEvent(it) },
            snackbarHostState = snackbarHostState
        )

        CollectSideEffect(flow = viewModel.effects) { effect ->
            when (effect) {
                is RegisterEffect.NavigateToWelcome -> navController.navigateUp()

                is RegisterEffect.NavigateToLogin -> {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }

                is RegisterEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }
}