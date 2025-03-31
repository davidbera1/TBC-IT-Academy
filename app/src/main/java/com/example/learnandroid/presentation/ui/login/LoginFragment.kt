package com.example.learnandroid.presentation.ui.login

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.UiUtils
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var uiUtils: UiUtils

    override fun start() {
        // receive email and password after successful registration and fill ETs
        setFragmentResultListener("credentials") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }

        observeUiFields()
        observeState()
        observeEffects()
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener { viewModel.onEvent(LoginEvent.LoginButtonClicked) }
    }

    private fun navigateToHomeFragment() {
        val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(direction)
    }

    private fun observeUiFields() {
        binding.etEmail.addTextChangedListener { email ->
            viewModel.onEvent(LoginEvent.SendUpdatedEmail(email.toString()))
        }

        binding.etPassword.addTextChangedListener { password ->
            viewModel.onEvent(LoginEvent.SendUpdatedPassword(password.toString()))
        }

        binding.cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(LoginEvent.RememberMeChecked(isChecked))
        }
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                uiUtils.handleLoader(
                    binding.progressBar,
                    binding.loadingView,
                    binding.btnLogin,
                    state.isLoading
                )
            }
        }
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
}
