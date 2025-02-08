package com.example.learnandroid.presentation.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.data.local.datastore.UserSessionManager
import com.example.learnandroid.data.model.dataclass.UserSession
import com.example.learnandroid.presentation.base.BaseFragment
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener { login() }

        binding.etEmail.doAfterTextChanged { checkLoginButtonState() }

        binding.etPassword.doAfterTextChanged { checkLoginButtonState() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // receive email and password after successful registration and fill ETs
        setFragmentResultListener("credentials") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }

        handleLoginResult()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginResult.collect { result ->

                handleProgressBarAndButtonState(result.loader)

                // save session only when login was successful
                if (result.loginResult == true) {
                    if (binding.cbRememberMe.isChecked) {
                        UserSessionManager.saveUserSession(
                            context = requireContext(), UserSession(
                                isLoggedIn = true,
                                email = binding.etEmail.text.toString(),
                                token = result.token
                            )
                        )
                    } else {
                        // save only email if remember me is not checked
                        UserSessionManager.saveUserSession(
                            context = requireContext(), UserSession(
                                email = binding.etEmail.text.toString(),
                            )
                        )
                    }

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.login_was_successful),
                        Toast.LENGTH_SHORT
                    ).show()

                    val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(direction)

                } else if (result.loginResult == false) {
                    Toast.makeText(
                        requireContext(), result.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Function to show/hide progress bar, disable/enable login button and change it's background
     * color according to loader state
     */
    private fun handleProgressBarAndButtonState(loader: Boolean?) {
        if (loader == true) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            binding.btnLogin.setBackgroundResource(R.drawable.gray_button_background)
        } else if (loader == false) {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            binding.btnLogin.setBackgroundResource(R.drawable.purple_button_background)
        }
    }

    /**
     * Function to check if email and password fields contain valid values and enable/disable login button accordingly
     */
    private fun checkLoginButtonState() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && email.length >= 10 && email.contains("@")) {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.setBackgroundResource(R.drawable.purple_button_background)
        } else {
            binding.btnLogin.isEnabled = false
            binding.btnLogin.setBackgroundResource(R.drawable.gray_button_background)
        }
    }
}