package com.example.learnandroid.presentation.ui.login

import androidx.compose.runtime.Composable
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.presentation.base.BaseComposeFragment
import com.example.learnandroid.presentation.compose.screens.LoginScreen
import com.example.learnandroid.presentation.extensions.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {

    private val viewModel: LoginViewModel by viewModels()

    @Composable
    override fun SetupContent() {
        val state = viewModel.state.collectAsStateWithLifecycle()

        LoginScreen(
            email = state.value.email,
            password = state.value.password,
            rememberMe = state.value.isRememberMeChecked,
            loginButtonClicked = { viewModel.onEvent(LoginEvent.LoginButtonClicked) },
            updateEmailValue = { viewModel.onEvent(LoginEvent.SendUpdatedEmail(it)) },
            updatePasswordValue = { viewModel.onEvent(LoginEvent.SendUpdatedPassword(it)) },
            updateRememberMeValue = { viewModel.onEvent(LoginEvent.SendUpdatedRememberMe(it)) },
            isLoading = state.value.isLoading,
        )
    }

    override fun start() {
        setUpFragmentResultListener()
        observeEffects()
    }

    private fun setUpFragmentResultListener() {
        // receive email and password after successful registration and fill ETs
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val email = bundle.getString(EMAIL_KEY)
            val password = bundle.getString(PASSWORD_KEY)

            viewModel.onEvent(LoginEvent.SendUpdatedEmail(email ?: ""))
            viewModel.onEvent(LoginEvent.SendUpdatedPassword(password ?: ""))
        }
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
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val REQUEST_KEY = "credentials"
    }
}
