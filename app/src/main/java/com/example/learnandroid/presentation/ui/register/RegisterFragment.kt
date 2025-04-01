package com.example.learnandroid.presentation.ui.register

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.presentation.base.BaseComposeFragment
import com.example.learnandroid.presentation.compose.screens.RegisterScreen
import com.example.learnandroid.presentation.extensions.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseComposeFragment() {

    private val viewModel: RegisterViewModel by viewModels()

    @Composable
    override fun SetupContent() {
        val state = viewModel.state.collectAsStateWithLifecycle()
        RegisterScreen(
            email = state.value.email,
            password = state.value.password,
            repeatPassword = state.value.repeatPassword,
            registerButtonClicked = { viewModel.onEvent(RegisterEvent.RegisterButtonClicked) },
            backButtonClicked = { viewModel.onEvent(RegisterEvent.BackButtonClicked) },
            updateEmailValue = { viewModel.onEvent(RegisterEvent.SendUpdatedEmail(email = it)) },
            updatePasswordValue = { viewModel.onEvent(RegisterEvent.SendUpdatedPassword(password = it)) },
            updateRepeatPasswordValue = {
                viewModel.onEvent(RegisterEvent.SendUpdatedRepeatPassword(repeatPassword = it))
            },
            isLoading = state.value.isLoading
        )
    }

    override fun start() {
        observeEffects()
    }

    private fun navigateToLoginFragment() {
        val direction = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effects ->
                when (effects) {
                    is RegisterEffect.ShowToast -> requireContext().showToast(effects.message)

                    is RegisterEffect.NavigateToLogin -> {
                        setUpFragmentResult()
                        navigateToLoginFragment()
                    }

                    is RegisterEffect.NavigateToHome -> findNavController().navigateUp()
                }
            }
        }
    }

    private fun setUpFragmentResult() {
        val bundle = bundleOf(
            EMAIL_KEY to viewModel.state.value.email,
            PASSWORD_KEY to viewModel.state.value.password
        )
        setFragmentResult(REQUEST_KEY, bundle)
    }

    companion object {
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val REQUEST_KEY = "credentials"
    }
}