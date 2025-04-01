package com.example.learnandroid.presentation.ui.profile

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.presentation.base.BaseComposeFragment
import com.example.learnandroid.presentation.compose.screens.ProfileScreen
import com.example.learnandroid.presentation.extensions.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseComposeFragment() {

    private val viewModel: ProfileViewModel by viewModels()

    @Composable
    override fun SetupContent() {
        val state = viewModel.state.collectAsStateWithLifecycle()
        ProfileScreen(
            logoutButtonClicked = { viewModel.onEvent(ProfileEvent.LogoutButtonClicked) },
            email = state.value.email,
            isLoading = state.value.isLoading
        )
    }

    override fun start() {
        viewModel.onEvent(ProfileEvent.ReadUserSession)
        observeEffects()
    }

    private fun navigateToLoginFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is ProfileEffect.ShowToast -> requireContext().showToast(effect.message)

                    is ProfileEffect.NavigateToLogin -> navigateToLoginFragment()
                }
            }
        }
    }
}