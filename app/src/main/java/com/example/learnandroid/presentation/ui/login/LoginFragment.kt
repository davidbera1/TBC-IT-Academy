package com.example.learnandroid.presentation.ui.login

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.domain.model.UserSession
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

        handleLoginResult()
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener { login() }
    }

    // only allowed email: eve.holt@reqres.in
    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(
            email = email, password = password
        )
    }

    private fun handleLoginResult() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.loginState.collect { loginState ->

                uiUtils.handleLoader(binding.progressBar, binding.loadingView, loginState.loader)
                uiUtils.handleErrorMessage(requireContext(), loginState.errorMessage)
                uiUtils.handleAction(binding.btnLogin, loginState.action)

                // save session only when login was successful
                if (loginState.loginResult == true) {
                    val token = loginState.data?.token ?: ""
                    if (binding.cbRememberMe.isChecked) {
                        viewModel.saveUserSession(
                            UserSession(
                                isLoggedIn = true,
                                email = binding.etEmail.text.toString(),
                                token = token
                            )
                        )
                    } else {
                        // save only email if remember me is not checked
                        viewModel.saveUserSession(
                            UserSession(
                                email = binding.etEmail.text.toString(),
                                token = ""
                            )
                        )
                    }

                    requireContext().showToast(getString(R.string.login_was_successful))

                    val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(direction)

                }
            }
        }
    }
}