package com.example.learnandroid.presentation.ui.welcome

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.presentation.base.BaseComposeFragment
import com.example.learnandroid.presentation.compose.WelcomeScreen
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseComposeFragment() {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun start() {
        observeEffects()
    }

    @Composable
    override fun SetupContent() {
        val state = viewModel.state.collectAsStateWithLifecycle()
        WelcomeScreen(
            registerButtonClicked = { viewModel.onEvent(WelcomeEvent.RegisterButtonClicked) },
            loginButtonClicked = { viewModel.onEvent(WelcomeEvent.LoginButtonClicked) },
            isLoading = state.value.isLoading
        )
    }

    private fun navigateToRegisterFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToLoginFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToHomeFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
        findNavController().navigate(direction)
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    WelcomeEffect.NavigateToHome -> navigateToHomeFragment()
                    WelcomeEffect.NavigateToLogin -> navigateToLoginFragment()
                    WelcomeEffect.NavigateToRegister -> navigateToRegisterFragment()
                }
            }
        }
    }
}