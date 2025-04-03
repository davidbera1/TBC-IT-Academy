package com.example.learnandroid.presentation.navigation.nav_graphs

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.learnandroid.presentation.navigation.Routes
import com.example.learnandroid.presentation.ui.login.LoginEffect
import com.example.learnandroid.presentation.ui.login.LoginScreen
import com.example.learnandroid.presentation.ui.login.LoginViewModel
import com.example.learnandroid.presentation.util.CollectSideEffect

fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable(Routes.LOGIN) {
        val viewModel: LoginViewModel = hiltViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val snackbarHostState = remember { SnackbarHostState() }

        LoginScreen(
            state = state,
            onEvent = { viewModel.onEvent(it) },
            snackbarHostState = snackbarHostState
        )

        CollectSideEffect(flow = viewModel.effects) { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> {
                    navController.navigate(Routes.HOME) {
                        popUpTo(navController.graph.id)
                    }
                }

                is LoginEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }
}