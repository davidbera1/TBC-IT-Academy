package com.example.learnandroid.presentation.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.presentation.base.BaseComposeFragment
import com.example.learnandroid.presentation.compose.LoginScreen
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        setUpFragmentResultListener()
        observeEffects()
    }

    private fun setUpFragmentResultListener() {
        // receive email and password after successful registration and fill ETs
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")

            viewModel.onEvent(LoginEvent.SendUpdatedEmail(email ?: ""))
            viewModel.onEvent(LoginEvent.SendUpdatedPassword(password ?: ""))
        }
    }

    @Composable
    override fun SetupContent() {
        val state = viewModel.state.collectAsState()
        LoginScreen(
            loginButtonClicked = { viewModel.onEvent(LoginEvent.LoginButtonClicked) },
            updateEmailValue = { viewModel.onEvent(LoginEvent.SendUpdatedEmail(it)) },
            updatePasswordValue = { viewModel.onEvent(LoginEvent.SendUpdatedPassword(it)) },
            updateRememberMeValue = { viewModel.onEvent(LoginEvent.SendUpdatedRememberMe(it)) },
            isLoading = state.value.isLoading,
            emailTextFromRegistration = state.value.email,
            passwordTextFromRegistration = state.value.password
        )
    }

    private fun navigateToHomeFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(direction)
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is LoginEffect.ShowToast -> requireContext().showToast(effect.message)

                    is LoginEffect.NavigateToHome -> navigateToHomeFragment()
                }
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "credentials"
    }
}
